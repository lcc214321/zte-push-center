package com.zte.msg.pushcenter.pccore.core.pusher;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zte.msg.pushcenter.pccore.core.pusher.base.BasePusher;
import com.zte.msg.pushcenter.pccore.core.pusher.base.Config;
import com.zte.msg.pushcenter.pccore.core.pusher.base.Message;
import com.zte.msg.pushcenter.pccore.core.pusher.msg.MailMessage;
import com.zte.msg.pushcenter.pccore.entity.MailInfo;
import com.zte.msg.pushcenter.pccore.entity.Provider;
import com.zte.msg.pushcenter.pccore.enums.ErrorCode;
import com.zte.msg.pushcenter.pccore.enums.PushMethods;
import com.zte.msg.pushcenter.pccore.exception.CommonException;
import com.zte.msg.pushcenter.pccore.service.AppService;
import com.zte.msg.pushcenter.pccore.service.HistoryService;
import com.zte.msg.pushcenter.pcscript.PcScript;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/11 9:34
 */
@SuppressFBWarnings("BC_UNCONFIRMED_CAST")
@Slf4j
@Component
public class MailPusher extends BasePusher {

    @Resource
    private AppService appService;

    @Resource
    private HistoryService historyService;

    @Override
    public void push(Message message) {
        MailMessage mailMessage = (MailMessage) message;
        CompletableFuture.supplyAsync(() -> {
            log.info("==========submit mail push task==========");
            MailConfig config = (MailConfig) configMap.get(PushMethods.MAIL)
                    .get(mailMessage.getProviderId()).get(mailMessage.getProviderId().intValue());
            JavaMailSenderImpl mailSender = buildMailSender(config);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            try {
                MimeMessageHelper simpleMailMessage = new MimeMessageHelper(mimeMessage, true);
                simpleMailMessage.setFrom(config.getUsername());
                simpleMailMessage.setTo(mailMessage.getTo());
                simpleMailMessage.setSubject(mailMessage.getSubject());
                simpleMailMessage.setText(mailMessage.getContent());
                if (!Objects.isNull(mailMessage.getCc()) && mailMessage.getCc().length > 0) {
                    simpleMailMessage.setCc(mailMessage.getCc());
                }
                mailSender.send(mimeMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mailMessage.setTransmitTime(new Timestamp(System.currentTimeMillis()));
            return new PcScript.Res(0, "发送成功");
        }, pushExecutor).exceptionally(e -> {
            log.error("Error while send a mail message: {}", e.getMessage());
            throw new CommonException(ErrorCode.MAIL_PUSH_ERROR);
        }).thenAcceptAsync(o -> persist(mailMessage, o));
    }

    @Override
    protected void persist(Message message, PcScript.Res res) {
        MailMessage mailMessage = (MailMessage) message;
        mailMessage.setAppName(appService.getAppName(mailMessage.getAppId()));
        MailInfo mailInfo = new MailInfo(mailMessage, res);
        historyService.addHistoryMail(mailInfo);
    }

    @Override
    protected void init() {
        configMap.put(PushMethods.MAIL, new HashMap<>(16));
        List<Provider> providers = providerMapper.selectList(new QueryWrapper<Provider>().eq("type", PushMethods.MAIL.value()));
        flushConfig(providers);
        log.info("========== initialize sms config completed : {}  ========== ", providers.size());
    }

    public void flushConfig(List<Provider> providers) {
        flushConfig(providers, false);
    }

    public void flushConfig(List<Provider> providers, boolean remove) {
        providers.forEach(o -> flushConfig(o, remove));
        if (!remove) {
            log.info("========== update sms config completed, update count: {} ==========", providers.size());
        } else {
            log.info("========== delete sms config completed, delete count: {} ==========", providers.size());
        }

    }

    public void flushConfig(Provider provider) {
        flushConfig(provider, false);
    }

    public void flushConfig(Provider provider, boolean remove) {
        if (!remove) {
            TreeMap<Integer, Config> treeMap = configMap.get(PushMethods.MAIL).get(provider.getId());
            if (Objects.isNull(treeMap)) {
                treeMap = new TreeMap<>();
            }
            treeMap.put(provider.getId().intValue(), JSONObject.parseObject(provider.getConfig(), MailConfig.class));
            configMap.get(PushMethods.MAIL).put(provider.getId(), treeMap);
        }else {
            configMap.get(PushMethods.MAIL).remove(provider.getId());
        }
    }

    @Data
    protected static class MailConfig implements Config {

        private String host;

        private Integer port;

        private String protocol;

        private String username;

        private String password;
    }

    public JavaMailSenderImpl buildMailSender(MailConfig mailConfig) {
        JavaMailSenderImpl jms = new JavaMailSenderImpl();
        jms.setProtocol(mailConfig.getProtocol());
        jms.setHost(mailConfig.getHost());
        jms.setPort(mailConfig.getPort());
        jms.setUsername(mailConfig.getUsername());
        jms.setPassword(mailConfig.getPassword());
        jms.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", "true");
        jms.setJavaMailProperties(p);
        return jms;
    }

}
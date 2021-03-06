package com.zte.msg.pushcenter.core.pusher;

import com.zte.msg.pushcenter.core.BasePusher;
import com.zte.msg.pushcenter.core.pusher.msg.Message;
import com.zte.msg.pushcenter.core.pusher.msg.SmsMessage;
import com.zte.msg.pushcenter.enums.PushMethods;
import com.zte.msg.pushcenter.service.SmsConfigService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/11 9:14
 */
@Service
@Slf4j
public class SmsPusher extends BasePusher {

    @Resource
    private SmsConfigService smsConfigService;

    /**
     * 模版id作为key，order作为内部嵌套map的key
     */
    public static Map<Long, TreeMap<Integer, ConfigDetail>> smsConfigMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("==========initialize sms push config...==========");
        List<ConfigDetail> configDetails = smsConfigService.selectConfigDetail(PushMethods.SMS.value());
        configDetails.forEach(this::flushConfigMap);
        log.info("==========initialize sms config completed : {} ==========", smsConfigMap.size());
    }

    public void flushConfigMap(ConfigDetail o) {
        TreeMap<Integer, ConfigDetail> treeMap = smsConfigMap.get(o.getId());
        if (Objects.isNull(treeMap)) {
            treeMap = new TreeMap<>();
        }
        treeMap.put(o.getOrder(), o);
        smsConfigMap.put(o.getId(), treeMap);
    }

    @Override
    public void push(Message message) {
        SmsMessage smsMessage = (SmsMessage) message;
        CompletableFuture.supplyAsync(() -> {
            log.info("==========submit sms push task==========");
            // TODO: 2020/12/31 调用脚本推送

            return null;
        }, pushExecutor).exceptionally(e -> {
            log.error("Error!!! An exception occurred when push a message.");
            // TODO: 2020/12/22 异常的情况返回错误详情
            return null;
        }).thenAcceptAsync(o -> {
            if (message.getIsCallBack()) {
                response(message, null);
            }
        }, resExecutor);
    }

    @Data
    public static class ConfigDetail {

        /**
         * sms_template表id
         */
        private Long id;

        private String name;

        private String example;

        private String sign;

        private Integer order;

        private String sAppId;

        private String secretId;

        private String secretKey;

        private String providerName;
    }

    @Data
    protected static class MessageRecord {

        private String phoneNum;

        private String templateId;

        private String sign;

        private Map<String, String> vars;

    }
}


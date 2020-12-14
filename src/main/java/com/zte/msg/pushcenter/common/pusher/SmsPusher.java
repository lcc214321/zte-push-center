package com.zte.msg.pushcenter.common.pusher;

import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.zte.msg.pushcenter.common.Selector;
import com.zte.msg.pushcenter.msg.Message;
import com.zte.msg.pushcenter.msg.SmsMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/11 9:14
 */
@Service
public class SmsPusher extends BasePusher {

    private HttpProfile httpProfile;

    private ClientProfile clientProfile;

    @Value("${sms-push.url.juhe}")
    private String juheSmsUrl;

    @Value("${sms-push.url.tencent}")
    private String tencentSmsUrl;

    /**
     * 聚合短信推送
     *
     * @param smsMessage
     * @return
     */
    private JSONObject jhSmsPush(SmsMessage smsMessage) {

        String varStr = "";
        String[] vars = smsMessage.getVar();
        for (String key : vars) {
            varStr += key + "&";
        }
        String[] phoneNums = smsMessage.getPhoneNums();
        JSONObject res = null;
        // 聚合不支持批量发送只能一个号码一个号码发
        for (String phoneNum : phoneNums) {
            try {
                varStr = URLEncoder.encode(varStr, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ResponseEntity<JSONObject> exchange = restTemplate.exchange(juheSmsUrl + "?mobile={1}&tpl_id={2}&tpl_value={3}&key={4}", HttpMethod.POST,
                    null, JSONObject.class, phoneNum, smsMessage.getTemplateId(), varStr, smsMessage.getSecretKey());
            res = exchange.getBody();
        }
        return res;
    }

    @Override
    public CompletableFuture<JSONObject> push(Message message) {
        SmsMessage smsMessage = (SmsMessage) message;
        return CompletableFuture.supplyAsync(() -> {
            switch (Selector.Company.valueOf(smsMessage.getProvider())) {
                case TENCENT:
                    return tcSmsPush(smsMessage);
                case JUHE:
                    return jhSmsPush(smsMessage);
                default:
                    return null;
            }
        }, executor);
    }

    /**
     * 腾讯短息推送
     *
     * @param smsMessage
     * @return
     */
    private JSONObject tcSmsPush(SmsMessage smsMessage) {
        try {
            if (Objects.isNull(httpProfile)) {
                httpProfile = new HttpProfile();
                httpProfile.setReqMethod("POST");
                httpProfile.setEndpoint(tencentSmsUrl);
            }
            if (Objects.isNull(clientProfile)) {
                clientProfile = new ClientProfile();
                clientProfile.setHttpProfile(httpProfile);

            }
            Credential cred = new Credential(smsMessage.getSecretId(), smsMessage.getSecretKey());
            SmsClient client = new SmsClient(cred, "", clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            req.setSmsSdkAppid(smsMessage.getAppId());
            req.setSign(smsMessage.getSign());
            req.setSessionContext(smsMessage.getToken());
            req.setTemplateID(smsMessage.getTemplateId());
            String[] phoneNums = smsMessage.getPhoneNums();
            for (int i = 0; i < phoneNums.length; i++) {
                phoneNums[i] = "+86" + phoneNums[i];
            }
            req.setPhoneNumberSet(phoneNums);
            if (smsMessage.getVar().length > 0) {
                String[] vars = smsMessage.getVar();
                String[] strings = new String[vars.length];
                for (int i = 0; i < vars.length; i++) {
                    strings[i] = vars[i].split("=")[1];
                }
                req.setTemplateParamSet(strings);
            }
            SendSmsResponse sendSmsResponse = client.SendSms(req);
            return JSONObject.parseObject(sendSmsResponse.toString());
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.zte.msg.pushcenter.core;

import com.alibaba.fastjson.JSONObject;
import com.zte.msg.pushcenter.core.pusher.msg.Message;
import com.zte.msg.pushcenter.dto.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/11 8:33
 */
@Slf4j
@Service
public abstract class BasePusher {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.consumer.topic}")
    private String kafkaTopic;

    @Resource(name = "asyncPushExecutor")
    protected ThreadPoolTaskExecutor pushExecutor;

    @Resource(name = "asyncResponseExecutor")
    protected ThreadPoolTaskExecutor resExecutor;

    @Resource
    protected RestTemplate restTemplate;

    /**
     * 提交推送
     *
     * @param message
     * @return
     */
    public final void submit(Message message) {
        kafkaTemplate.send(kafkaTopic, JSONObject.toJSONString(message));
    }

    public abstract void push(Message message);

    /**
     * 回调
     *
     * @param message
     */
    protected void response(Message message, JSONObject res) {
        if (!message.getIsCallBack()) {
            return;
        }
        log.info("==========start sms push response==========");
        try {
            restTemplate.exchange(message.getCallBackUrl(), HttpMethod.POST, new HttpEntity<>(DataResponse.of(res)), String.class);
        } catch (Exception e) {
            log.error("Error!!! An exception occurred when response.");
        }
    }
}

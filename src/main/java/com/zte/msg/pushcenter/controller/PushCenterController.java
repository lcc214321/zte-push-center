package com.zte.msg.pushcenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.zte.msg.pushcenter.core.pusher.MailPusher;
import com.zte.msg.pushcenter.core.pusher.SmsPusher;
import com.zte.msg.pushcenter.core.pusher.msg.MailMessage;
import com.zte.msg.pushcenter.core.pusher.msg.SmsMessage;
import com.zte.msg.pushcenter.dto.DataResponse;
import com.zte.msg.pushcenter.dto.req.MailMessageReqDTO;
import com.zte.msg.pushcenter.dto.req.SmsMessageReqDTO;
import com.zte.msg.pushcenter.enums.ErrorCode;
import com.zte.msg.pushcenter.enums.PushMethods;
import com.zte.msg.pushcenter.exception.CommonException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/10 13:57
 */
@RestController
@RequestMapping("/api/openapi/v1/push")
@Api(tags = "消息推送")
@Validated
public class PushCenterController {

    @Resource
    private SmsPusher smsPusher;

    @Resource
    private MailPusher mailPusher;

    @PostMapping(value = "/sms")
    @ApiOperation(value = "短信推送")
    public DataResponse<JSONObject> pushSms(@Valid @RequestBody SmsMessageReqDTO reqDTO) {

        if (reqDTO.getIsCallBack() && Objects.isNull(reqDTO.getCallBackUrl())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        SmsMessage smsMessage = new SmsMessage().build(reqDTO);
        smsMessage.setPushMethods(PushMethods.SMS);
        smsPusher.submit(smsMessage);
        return DataResponse.success();
    }

    @PostMapping(value = "/mail")
    @ApiOperation(value = "邮件推送")
    public DataResponse<JSONObject> pushMail(@Valid MailMessageReqDTO reqDTO) {

        MailMessage mailMessage = new MailMessage().build(reqDTO);
        mailPusher.submit(mailMessage);
        return DataResponse.success();
    }

//    @PostMapping(value = "/wechat")
//    @ApiOperation(value = "公众号推送")
//    public DataResponse<JSONObject> pushWeChat(@Valid WeChatMessageReqDTO reqDTO) {
//        WeChatMessage weChatMessage = new WeChatMessage().build(reqDTO);
//        weChatPusher.submit(weChatMessage);
//        return DataResponse.success();
//
//    }

    @GetMapping(value = "/wechat/auth")
    @ApiOperation(value = "微信公众号获取accessKey")
    public DataResponse<String> weChatAuth(@RequestParam(value = "wechat_app_id") @ApiParam(value = "微信公众号appId") String weChatAppId,
                                           @RequestParam(value = "app_secret") @ApiParam(value = "appSecret") String appSecret) {

        return null;
//        return DataResponse.of(weChatPusher.getAccessKey(weChatAppId, appSecret));
    }


}


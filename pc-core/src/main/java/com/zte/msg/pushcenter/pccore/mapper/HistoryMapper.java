package com.zte.msg.pushcenter.pccore.mapper;

import com.zte.msg.pushcenter.pccore.dto.req.ApplicationHistoryReqDTO;
import com.zte.msg.pushcenter.pccore.dto.req.MailHistoryReqDTO;
import com.zte.msg.pushcenter.pccore.dto.req.SmsHistoryReqDTO;
import com.zte.msg.pushcenter.pccore.dto.req.WechatHistoryReqDTO;
import com.zte.msg.pushcenter.pccore.entity.ApplicationInfo;
import com.zte.msg.pushcenter.pccore.entity.MailInfo;
import com.zte.msg.pushcenter.pccore.entity.SmsInfo;
import com.zte.msg.pushcenter.pccore.entity.WechatInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:
 *
 * @author frp
 * @version 1.0
 * @date 2021/1/6 10:12
 */
@Mapper
@Repository
public interface HistoryMapper {

    List<SmsInfo> listHistorySms(SmsHistoryReqDTO smsHistoryReqDTO);

    List<MailInfo> listHistoryMail(MailHistoryReqDTO mailHistoryReqDTO);

    List<WechatInfo> listHistoryWechat(WechatHistoryReqDTO wechatHistoryReqDTO);

    List<ApplicationInfo> listHistoryApplication(ApplicationHistoryReqDTO applicationHistoryReqDTO);

}

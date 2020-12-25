package com.zte.msg.pushcenter.common.pusher.msg;

import lombok.Data;

import java.sql.Timestamp;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/9 9:58
 */
@Data
public class Message {

    /**
     * messageId
     */
    private Long messageId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息创建时间
     */
    private Timestamp createTime;

}
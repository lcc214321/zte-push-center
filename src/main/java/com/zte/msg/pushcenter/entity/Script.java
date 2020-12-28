package com.zte.msg.pushcenter.entity;

import lombok.Data;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/24 16:29
 */
@Data
public class Script extends BaseEntity{

    private Long configId;

    private String context;

    private String scriptName;

    private String scriptTag;
}
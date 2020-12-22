package com.zte.msg.pushcenter.entity;

import lombok.Data;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/22 14:40
 */
@Data
public class Provider extends BaseEntity{

    private String providerName;

    private Integer type;

    private String secretId;

    private String secretKey;

    private String appId;

}

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
public class Config extends BaseEntity {


    private String configName;

    private String description;

    private String providerName;

}

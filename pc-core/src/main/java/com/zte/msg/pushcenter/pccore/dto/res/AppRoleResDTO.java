package com.zte.msg.pushcenter.pccore.dto.res;

import com.zte.msg.pushcenter.pccore.entity.AppRole;
import lombok.Data;

import java.util.List;

@Data
public class AppRoleResDTO {
    private Integer appId;

    private String appName;

    private List<AppRole> role;
}
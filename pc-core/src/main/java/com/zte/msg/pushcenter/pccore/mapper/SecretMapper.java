package com.zte.msg.pushcenter.pccore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zte.msg.pushcenter.pccore.dto.OpenApiTokenInfo;
import com.zte.msg.pushcenter.pccore.entity.SecretKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecretMapper extends BaseMapper<SecretKey> {

    String selectAppRole(String appKey);
}
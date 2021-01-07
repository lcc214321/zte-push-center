package com.zte.msg.pushcenter.pccore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zte.msg.pushcenter.pccore.dto.res.AppRoleResDTO;
import com.zte.msg.pushcenter.pccore.dto.res.TemplateResDTO;
import com.zte.msg.pushcenter.pccore.entity.AppRole;
import com.zte.msg.pushcenter.pccore.entity.SendMode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AppRoleMapper extends BaseMapper<AppRoleResDTO> {

    List<AppRoleResDTO> listApp();

    List<TemplateResDTO> selectTemplate(Integer modeId);

    List<AppRole> selectAppMode(Integer appId);

    int editAppRole(List<AppRoleResDTO> appRoleResDTOList);

    List<SendMode> listSendMode();

    int deleteSendMode(Integer modeId);

    int updateSendMode(SendMode sendMode);

    int insertSendMode(String modeName);

    Integer selectSendModeId(String modeName);
}

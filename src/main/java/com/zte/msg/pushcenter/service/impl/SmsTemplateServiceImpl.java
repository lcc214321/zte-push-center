package com.zte.msg.pushcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zte.msg.pushcenter.dto.req.SmsTemplateReqDTO;
import com.zte.msg.pushcenter.dto.res.SmsTemplateResDTO;
import com.zte.msg.pushcenter.entity.Provider;
import com.zte.msg.pushcenter.entity.SmsProviderConfig;
import com.zte.msg.pushcenter.entity.SmsTemplate;
import com.zte.msg.pushcenter.enums.ErrorCode;
import com.zte.msg.pushcenter.exception.CommonException;
import com.zte.msg.pushcenter.mapper.ProviderMapper;
import com.zte.msg.pushcenter.mapper.SmsTemplateMapper;
import com.zte.msg.pushcenter.service.SmsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/21 10:48
 */
@Service
@Slf4j
public class SmsTemplateServiceImpl extends ServiceImpl<SmsTemplateMapper, SmsTemplate> implements SmsTemplateService {

    @Resource
    private ProviderMapper providerMapper;

    @Override
    public SmsTemplateResDTO addSmsTemplate(SmsTemplateReqDTO smsTemplateReqDTO) {

        Integer templateCount = getBaseMapper().selectCount(
                new QueryWrapper<SmsTemplate>()
                        .eq("provider_id", smsTemplateReqDTO.getProviderId())
                        .eq("template_id", smsTemplateReqDTO.getTemplateId()));
        if (templateCount >= 1) {
            throw new CommonException(ErrorCode.TEMPLATE_EXIST);
        }
        Provider provider = providerMapper.selectById(smsTemplateReqDTO.getProviderId());
        if (Objects.isNull(provider)) {
            throw new CommonException(ErrorCode.PROVIDER_NOT_EXIST);
        }
        SmsTemplate entity = new SmsTemplate();
        BeanUtils.copyProperties(smsTemplateReqDTO, entity);
        getBaseMapper().insert(entity);
        SmsTemplateResDTO resDTO = new SmsTemplateResDTO();
        BeanUtils.copyProperties(entity, resDTO);
        resDTO.setProviderName(provider.getProviderName());
        resDTO.setExample(smsTemplateReqDTO.getExample());
        return resDTO;
    }

    @Override
    public SmsTemplateResDTO selectTemplate(String templateId) {

        return null;
    }


}

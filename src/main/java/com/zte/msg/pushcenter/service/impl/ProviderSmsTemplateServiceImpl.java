package com.zte.msg.pushcenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zte.msg.pushcenter.entity.ProviderSmsTemplate;
import com.zte.msg.pushcenter.mapper.ProviderSmsTemplateMapper;
import com.zte.msg.pushcenter.service.ProviderSmsTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/31 15:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProviderSmsTemplateServiceImpl extends ServiceImpl<ProviderSmsTemplateMapper, ProviderSmsTemplate> implements ProviderSmsTemplateService {


    @Override
    public void saveBatch(List<ProviderSmsTemplate> templates) {
        super.saveBatch(templates);
    }
}

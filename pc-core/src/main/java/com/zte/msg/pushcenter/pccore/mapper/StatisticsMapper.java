package com.zte.msg.pushcenter.pccore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zte.msg.pushcenter.pccore.dto.req.ConditionStatisticsReqDTO;
import com.zte.msg.pushcenter.pccore.dto.req.StatisticsReqDTO;
import com.zte.msg.pushcenter.pccore.dto.res.AppStatisticsResDTO;
import com.zte.msg.pushcenter.pccore.dto.res.ProviderStatisticsResDTO;
import com.zte.msg.pushcenter.pccore.dto.res.StatisticsResDTO;
import com.zte.msg.pushcenter.pccore.dto.res.TypeStatisticsResDTO;
import com.zte.msg.pushcenter.pccore.entity.App;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/21 10:29
 */
@Mapper
@Repository
public interface StatisticsMapper extends BaseMapper<StatisticsResDTO> {

    List<TypeStatisticsResDTO> statisticsType(String[] typeArray, StatisticsReqDTO statisticsReqDTO);

    List<AppStatisticsResDTO> statisticsAppAndCondition(List<App> appList, ConditionStatisticsReqDTO conditionStatisticsReqDTO);

    List<ProviderStatisticsResDTO> statisticsPlatform(StatisticsReqDTO statisticsReqDTO);
}
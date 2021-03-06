package com.zte.msg.pushcenter.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/21 10:52
 */
@Data
@ApiModel
public class ProviderReqDTO {

    @ApiModelProperty(value = "描述信息")
    private String description;

    @JsonProperty(value = "provider_name")
    @NotNull(message = "32000006")
    @ApiModelProperty(value = "服务商名")
    private String providerName;

}

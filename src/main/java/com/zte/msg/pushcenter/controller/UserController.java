package com.zte.msg.pushcenter.controller;

import com.zte.msg.pushcenter.dto.DataResponse;
import com.zte.msg.pushcenter.dto.req.PasswordReqDTO;
import com.zte.msg.pushcenter.entity.User;
import com.zte.msg.pushcenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * description:
 *
 * @author frp
 * @version 1.0
 * @date 2020/12/28 14:42
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@Api(tags = "用户")
@Validated
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 新增管理平台登录用户
     *
     * @param user 用户信息
     * @return DataResponse
     */
    @PostMapping("/user")
    @ApiOperation(value = "新增管理平台登录用户")
    public <T> DataResponse<T> creatUser(@RequestBody @Valid User user) {
        userService.insertUser(user);
        return DataResponse.success();
    }

    /**
     * 修改密码
     *
     * @param passwordReqDTO 密码信息
     * @return DataResponse
     */
    @PostMapping("/user/change")
    @ApiOperation(value = "修改密码")
    public <T> DataResponse<T> change(@RequestBody @Valid PasswordReqDTO passwordReqDTO) {
        userService.changePwd(passwordReqDTO);
        return DataResponse.success();
    }

    /**
     * 用户删除
     *
     * @param userName 用户名
     * @return DataResponse
     */
    @DeleteMapping("/user")
    @ApiOperation(value = "删除用户")
    public <T> DataResponse<T> deleteUser(@Valid String userName) {
        userService.deleteUser(userName);
        return DataResponse.success();
    }
}
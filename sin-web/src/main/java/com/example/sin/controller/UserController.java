package com.example.sin.controller;

import com.example.sin.entity.UserDO;
import com.example.sin.qo.UserQO;
import com.example.sin.service.UserService;
import com.example.sin.service.utils.Pagination;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
@Api(value = "User Api",tags = {"user 相关操作"})
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "test", method = RequestMethod.POST)
    @ApiOperation(value = "程序简单功能测试")
    public String test() {
        return userService.test();
    }

    /**
     * 获取套餐的分页列表
     */
    @RequestMapping(value = "userList/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "查询user分页列表")
    @ApiImplicitParam(name = "id", value = "套餐列表查询条件对象", required = true, paramType = "path")
    public PageInfo<UserDO> selectByIdRange(@PathVariable(value = "id") Integer id, Pagination pagination) {
        return userService.selectById(id, pagination);
    }
    @PostMapping(value = "primary/{id}")
    @ApiOperation(value = "根据id查询指定user对象")
    public UserDO selectById(@PathVariable(value = "id") Integer id) {
        return userService.selectByID(id);
    }


    @PostMapping(value = "insertUser")
    @ApiOperation(value = "插入user对象")
    @ApiImplicitParam(name = "userQO", value = "userQO对象",required = true, dataType = "UserQO")
    public int insertUser(@RequestBody UserQO userQO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userQO,userDO);
        return userService.insert(userDO);
    }

    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    @ApiOperation(value = "更新user信息")
    @ApiImplicitParam(name = "userDO", value = "user对象",required = true, dataType = "UserDO")
    public int updateUser(@RequestBody UserDO userDO) {
        return userService.updateUser(userDO);
    }
}

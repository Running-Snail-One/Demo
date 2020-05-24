package com.example.sin.service;

import com.example.sin.entity.UserDO;
import com.example.sin.service.utils.Pagination;
import com.github.pagehelper.PageInfo;

public interface UserService {

    String test();

    PageInfo<UserDO> selectById(Integer id, Pagination pagination);

    UserDO selectByID(Integer id);

    int insert(UserDO userQO);

    int updateUser(UserDO userDO);
}

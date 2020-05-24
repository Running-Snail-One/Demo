package com.example.sin.service.imp;

import com.example.sin.entity.UserDO;
import com.example.sin.mapper.UserDOMapper;
import com.example.sin.service.UserService;
import com.example.sin.service.utils.Pagination;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public String test() {
        return "hello world-------------";
    }

    @Override
    public PageInfo<UserDO> selectById(Integer id, Pagination pagination) {
        PageHelper.startPage(pagination.getCurrent(),pagination.getPageSize());
        List<UserDO> userDOS = userDOMapper.selectByPrimaryKeyRange(id);
        return new PageInfo<UserDO>(userDOS);
    }

    @Override
    public UserDO selectByID(Integer id) {
        return userDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(UserDO userDO) {
        return userDOMapper.insert(userDO);
    }

    @Override
    public int updateUser(UserDO userDO) {
        return userDOMapper.updateByPrimaryKey(userDO);
    }


}

package com.example.sin.mapper;

import com.example.sin.entity.UserDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;


@Mapper
@Repository
public interface UserDOMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    UserDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(UserDO record);


    List<UserDO> selectByPrimaryKeyRange(@Param(value = "id") Integer id);
}
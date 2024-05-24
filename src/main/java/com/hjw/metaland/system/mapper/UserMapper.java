package com.hjw.metaland.system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjw.metaland.system.mapper.dto.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

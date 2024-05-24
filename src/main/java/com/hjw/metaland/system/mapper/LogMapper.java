package com.hjw.metaland.system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjw.metaland.system.mapper.dto.Log;
import com.hjw.metaland.system.mapper.dto.Role;

@Mapper
public interface LogMapper extends BaseMapper<Log> {

}

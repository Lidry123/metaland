package com.hjw.metaland.system.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjw.metaland.system.mapper.RoleMapper;
import com.hjw.metaland.system.mapper.dto.Role;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {
}

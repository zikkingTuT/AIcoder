package com.china.powerms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.china.powerms.entity.Users;
import com.china.powerms.service.UsersService;
import com.china.powerms.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【users(用户表)】的数据库操作Service实现
* @createDate 2024-11-22 23:26:44
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}





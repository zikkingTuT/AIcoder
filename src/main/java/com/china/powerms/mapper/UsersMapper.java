package com.china.powerms.mapper;

import com.china.powerms.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【users(用户表)】的数据库操作Mapper
* @createDate 2024-11-22 23:26:44
* @Entity com.china.powerms.entity.Users
*/
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}





package com.china.powerms.mapper;

import com.china.powerms.entity.UserRoles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【user_roles(用户角色关系表)】的数据库操作Mapper
* @createDate 2024-11-22 23:26:50
* @Entity com.china.powerms.entity.UserRoles
*/
@Mapper
public interface UserRolesMapper extends BaseMapper<UserRoles> {

}





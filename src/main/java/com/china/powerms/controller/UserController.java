package com.china.powerms.controller;

import com.china.powerms.common.PasswordEncoder;
import com.china.powerms.common.Result;
import com.china.powerms.dto.LoginDTO;
import com.china.powerms.entity.UserRoles;
import com.china.powerms.entity.Users;
import com.china.powerms.service.ElectricityBillingService;
import com.china.powerms.service.UserRolesService;
import com.china.powerms.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UsersService usersService;


    @Resource
    private UserRolesService userRolesService;

    @Resource
    private ElectricityBillingService electricityBillingService;

    @PostMapping("/register")
    public Result<Users> createUser(@RequestBody @Valid Users users) {
        log.info("users:{}",users);
        // 检查用户名是否存在
        Users existUser = usersService.lambdaQuery()
                .eq(Users::getUsername, users.getUsername())
                .one();
        if (existUser != null) {
            return Result.failed("用户名已存在");
        }
        try {
            // 对密码进行加密
            users.setPassword(PasswordEncoder.encode(users.getPassword()));
            // 设置默认状态
            users.setStatus("active");
            // 保存用户
            if (usersService.save(users)) {
                // 清除密码后返回
                users.setPassword(null);
                electricityBillingService.generate(users.getUsername());
                return Result.ok(users);
            } else {
                return Result.failed("用户创建失败");
            }
        } catch (Exception e) {
            log.error("用户注册失败，用户名：{}，错误信息：{}",
                    users.getUsername(),
                    e.getMessage(),
                    e);
            return Result.failed("创建用户时发生错误");
        }
    }

    @PostMapping("/login")
    public Result<Map<String, Object> > login(@RequestBody  LoginDTO loginDTO) {
        // 根据用户名查询用户
        Users user = usersService.lambdaQuery()
                .eq(Users::getUsername, loginDTO.getUsername())
                .one();
        // 用户不存在
        if (user == null) {
            return Result.failed("用户名或密码错误");
        }
        // 验证密码
        if (!BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
            return Result.failed("用户名或密码错误");
        }
        // 检查用户状态
        if ("inactive".equals(user.getStatus())) {
            return Result.failed("账号已被冻结");
        }
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        UserRoles userRoles = userRolesService.lambdaQuery()
                .eq(UserRoles::getUserId,user.getUserId())
                .one();
        // 清除敏感信息
        user.setPassword(null);
        stringObjectHashMap.put("user",user);
        if(userRoles == null){
            UserRoles userRoles1 = new UserRoles();
            userRoles1.setUserId(user.getUserId());
            userRoles1.setRoleId(2);
            userRolesService.save(userRoles1);
        }else {
            stringObjectHashMap.put("userole",userRoles.getRoleId());
        }
        stringObjectHashMap.put("token","token");
        return Result.ok(stringObjectHashMap);
    }
}

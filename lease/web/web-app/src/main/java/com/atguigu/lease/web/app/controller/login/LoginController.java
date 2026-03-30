package com.atguigu.lease.web.app.controller.login;


import com.atguigu.lease.common.login.LoginUserHolder;
import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.common.utils.JwtUtil;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.app.service.LoginService;
import com.atguigu.lease.web.app.service.UserInfoService;
import com.atguigu.lease.web.app.vo.user.LoginVo;
import com.atguigu.lease.web.app.vo.user.UserInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/app/")
@Tag(name = "登录管理")
public class LoginController {

    @Autowired
    private LoginService service;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("testLogin")
    @Operation(summary = "测试登录（跳过验证码）")
    public Result<String> testLogin(@RequestParam String phone) {
        // 根据手机号查询或创建用户
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, phone);
        UserInfo userInfo = userInfoService.getOne(queryWrapper);

        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setPhone(phone);
            userInfo.setStatus(BaseStatus.ENABLE);
            userInfo.setNickname("用户-" + phone.substring(7));
            userInfoService.save(userInfo);
        }

        if (userInfo.getStatus() == BaseStatus.DISABLE) {
            return Result.fail();
        }

        String token = JwtUtil.createToken(userInfo.getId(), userInfo.getPhone());
        return Result.ok(token);
    }


    @GetMapping("login/getCode")
    @Operation(summary = "获取短信验证码")
    public Result getCode(@RequestParam String phone) {
        service.sendCode(phone);
        return Result.ok();
    }

    @PostMapping("login")
    @Operation(summary = "登录")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String token = service.login(loginVo);
        return Result.ok(token);
    }

    @GetMapping("info")
    @Operation(summary = "获取登录用户信息")
    public Result<UserInfoVo> info() {
        UserInfoVo userInfoVo = service.getLoginUserInfoById(LoginUserHolder.getLoginUser().getUserId());
        return Result.ok(userInfoVo);
    }

}




package com.edu.zzh.controller;

import com.edu.zzh.exception.MyException;
import com.edu.zzh.exception.MyExceptionEnum;
import com.edu.zzh.povo.dto.Admin;
import com.edu.zzh.service.SystemService;
import com.edu.zzh.utils.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zzh
 * @Date: 2021/3/5 14:11
 * @Description:
 */
@Controller
public class LoginController {
    @Autowired
    private SystemService systemService;

    @RequestMapping("/login")
    public void login(@RequestBody Admin admin, HttpServletRequest request) throws Exception {
        Admin adminInfo = systemService.login(admin.getUsername());
        String pwd = AES.decrypt(adminInfo.getSalt(),adminInfo.getPassword());
        if (pwd.equals("")||!pwd.equals(admin.getPassword())){
            throw new MyException(MyExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        request.getSession().setAttribute("admin",admin);

    }
}

package com.edu.zzh.controller;

import com.edu.zzh.povo.dto.UserInfo;
import com.edu.zzh.povo.vo.PageResult;
import com.edu.zzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/12 12:31
 * @Description:
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/queryUserListByPage")
    public ResponseEntity queryUserListByPage(Integer pageNum, Integer pageSize, String userName,String regtime,String sex) throws Exception {

        PageResult<UserInfo> userListByPage= userService.queryUserListByPage(pageNum,pageSize,userName,regtime,sex);

        return ResponseEntity.ok(userListByPage);
    }
    @DeleteMapping("/deleteUserList")
    public ResponseEntity delUserListById(@RequestBody List<UserInfo> userList){
        userService.deleteUserList(userList);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity delUserById(UserInfo userInfo){
        userService.deleteUser(userInfo);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/editUser")
    public String editUser(Integer userId, Model model) throws Exception {
        UserInfo userInfo = userService.queryUserById(userId);
        model.addAttribute("userInfo",userInfo);
        return "editUser";
    }
    @PostMapping("/saveEditUser")
    public ResponseEntity saveEditUser(@RequestBody UserInfo userInfo) throws Exception {
        userService.saveEditUser(userInfo);
        return ResponseEntity.ok().build();
    }


}

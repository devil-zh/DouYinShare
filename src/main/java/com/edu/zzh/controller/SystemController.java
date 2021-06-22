package com.edu.zzh.controller;

import com.edu.zzh.povo.dto.SystemInfo;
import com.edu.zzh.povo.vo.SystemData;
import com.edu.zzh.service.SystemService;
import com.edu.zzh.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: zzh
 * @Date: 2021/3/7 15:07
 * @Description:
 */
@Controller
public class SystemController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private UploadService uploadService;

    @RequestMapping("/system")
    public String system(Model model){
        SystemInfo systemInfo = systemService.selectSystemInfo();
        SystemData systemData = systemService.selectSystemData();
        model.addAttribute("systemInfo",systemInfo);
        model.addAttribute("systemData",systemData);
        return "system";
    }
    @PostMapping("/updateSystemInfo")
    public String updateSystemInfo(@RequestParam("systemName")String systemName,@RequestParam("client_key")String clientKey,@RequestParam("client_secret")String clientSecret,
                                   Model model){
        systemService.update(systemName,clientKey,clientSecret);
        SystemInfo systemInfo = systemService.selectSystemInfo();
        SystemData systemData = systemService.selectSystemData();
        model.addAttribute("systemInfo",systemInfo);
        model.addAttribute("systemData",systemData);
        return "system";
    }
    @PostMapping("/submitlogo")
    public ResponseEntity<Model> submitLogo(@RequestParam("file") MultipartFile file, Model model){
        String logoUrl = uploadService.uploadImage(file);
        systemService.updateLogo(logoUrl);
        SystemInfo systemInfo = systemService.selectSystemInfo();
        systemInfo.setLogoUrl(logoUrl);
        model.addAttribute("systemInfo",systemInfo);
        return ResponseEntity.ok(model);
    }

}

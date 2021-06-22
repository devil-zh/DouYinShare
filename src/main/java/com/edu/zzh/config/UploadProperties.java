package com.edu.zzh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author : zzh
 * @date : 2020/4/20 14:42
 * @describe :
 */
@Data
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {
    private String baseUrl;
    private List<String> allowType;
}

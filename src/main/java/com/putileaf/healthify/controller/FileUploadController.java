package com.putileaf.healthify.controller;

import com.putileaf.healthify.entity.Result;
import com.putileaf.healthify.utils.AliOssUtil;
import com.putileaf.healthify.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.util.Map;
import java.util.UUID;

@RestController
public class FileUploadController {

    //使用类型+上传者id的方式命令
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file,String type) throws Exception {
        //把文件内容存储在本地磁盘
        String originalFilename = file.getOriginalFilename();
        //获取文件名后缀
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //先查询账号的用户名
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        //生成文件名
        String filename = username+"_"+type+substring;
        // file.transferTo(new File("D:\\files\\"+filename)); 本地存储
        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(url);
    }
}

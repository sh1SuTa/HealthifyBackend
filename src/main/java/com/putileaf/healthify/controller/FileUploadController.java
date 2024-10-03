package com.putileaf.healthify.controller;

import com.putileaf.healthify.entity.Result;
import com.putileaf.healthify.utils.AliOssUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import java.util.UUID;

@RestController
public class FileUploadController {

    //使用类型+上传者id的方式命令
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        //保证文件名不重复
        String filename = UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
        // file.transferTo(new File("D:\\files\\"+filename)); 本地存储
        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success("上传成功",url);
    }
}

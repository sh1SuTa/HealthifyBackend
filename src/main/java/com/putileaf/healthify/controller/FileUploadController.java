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
    public Result<String> upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer level = (Integer)map.get("level");
        if (level < 2){
            return Result.error("只有版主才能上传");
        }
        //保证文件名不重复
        String userId =(String) map.get("username");
        String filename =userId+ (System.currentTimeMillis() / 1000)+originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println("打印一下文件名："+filename);
        // file.transferTo(new File("D:\\files\\"+filename)); 本地存储
        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success("上传成功",url);
    }
}

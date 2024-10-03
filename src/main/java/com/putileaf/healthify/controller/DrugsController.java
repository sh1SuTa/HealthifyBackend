package com.putileaf.healthify.controller;

import com.putileaf.healthify.entity.Drugs;
import com.putileaf.healthify.entity.Result;
import com.putileaf.healthify.service.DrugsService;

import com.putileaf.healthify.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/drugs")
public class DrugsController {

    @Autowired
    DrugsService drugsService;


    @PostMapping("/add")
    public Result<String> add(@RequestBody @Validated Drugs drugsModel){
        System.out.println("请求到添加");
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer)map.get("id");
        Integer level = (Integer)map.get("level");
        if (level < 2){
            return Result.error("只有版主才能使用");
        }
        drugsModel.setCreateUser(userId);
        drugsService.add(drugsModel);
        return Result.success("添加成功");
    }
    @GetMapping("/list")
        public Result<List<Drugs>> list(@RequestParam(required = false, defaultValue = "") String searchKeyword){
        System.out.println("keyWord = " + searchKeyword);
        return Result.success("成功",drugsService.list(searchKeyword));
    }

}

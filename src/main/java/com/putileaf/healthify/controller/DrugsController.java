package com.putileaf.healthify.controller;

import com.putileaf.healthify.entity.Drugs;
import com.putileaf.healthify.entity.Result;
import com.putileaf.healthify.service.DrugsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/drugs")
public class DrugsController {

    @Autowired
    DrugsService drugsService;


    @PostMapping("/add")
    public Result<String> add(@RequestBody @Validated Drugs drugs){
            drugsService.add(drugs);
        return Result.success("添加成功");
    }
//    @GetMapping("/list")
//        public Result<List<Drugs>> list(@RequestParam String keyWord){
//        return Result.success(drugsService.list(keyWord));
//    }

}

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
    public Result<String> add(@RequestBody @Validated Drugs drugsModel){
            drugsService.add(drugsModel);
        return Result.success("添加成功");
    }
    @GetMapping("/list")
        public Result<List<Drugs>> list(@RequestParam(required = false, defaultValue = "") String searchKeyword){
        System.out.println("keyWord = " + searchKeyword);
        return Result.success(drugsService.list(searchKeyword));
    }

}

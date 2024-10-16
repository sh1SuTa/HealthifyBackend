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

        return Result.success("成功",drugsService.list(searchKeyword));
    }
    @GetMapping("/list/{id}")
    public Result<Drugs> listId(@PathVariable("id") Integer id){
        return Result.success("成功",drugsService.getById(id));
    }

    //编辑功能，TODO
    @PutMapping("/edit")
    public Result<String> edit(@RequestBody @Validated Drugs drugsModel){

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer)map.get("id");
        Integer level = (Integer)map.get("level");

        if (level < 2){
            return Result.error("只有版主才能使用");
        }
        drugsModel.setCreateUser(userId);
        drugsService.updateById(drugsModel);
        return Result.success("编辑成功");
    }


}

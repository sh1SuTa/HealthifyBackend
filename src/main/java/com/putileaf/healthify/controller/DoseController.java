package com.putileaf.healthify.controller;

import com.putileaf.healthify.entity.Dose;
import com.putileaf.healthify.entity.Result;
import com.putileaf.healthify.service.DoseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/dose")
public class DoseController {

    @Autowired
    DoseService doseService;


    @PostMapping("/add")
    public Result<String> add(@RequestBody @Validated Dose dose){
        try {
            doseService.add(dose);
        }catch (Exception e){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }
    @GetMapping("/list")
        public Result<List<Dose>> list(@RequestParam String keyWord){
        return Result.success(doseService.list(keyWord));
    }

}

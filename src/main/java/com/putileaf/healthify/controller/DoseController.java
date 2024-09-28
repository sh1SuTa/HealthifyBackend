package com.putileaf.healthify.controller;

import com.putileaf.healthify.entity.Dose;
import com.putileaf.healthify.entity.Result;
import com.putileaf.healthify.service.DoseService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dose")
public class DoseController {

    private DoseService doseService;


    @PostMapping("/add")
    public Result<String> add(@RequestBody @Validated Dose dose){
        doseService.add(dose);
        return Result.success("添加成功");
    }


}

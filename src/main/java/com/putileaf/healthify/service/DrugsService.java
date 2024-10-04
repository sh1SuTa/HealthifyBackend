package com.putileaf.healthify.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.putileaf.healthify.entity.Drugs;

import java.util.List;

public interface DrugsService extends IService<Drugs> {


    void add(Drugs drugs);

    List<Drugs> list(String keyWord);
}

package com.putileaf.healthify.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.putileaf.healthify.entity.Drugs;
import com.putileaf.healthify.mapper.DrugsMapper;
import com.putileaf.healthify.service.DrugsAliasService;
import com.putileaf.healthify.service.DrugsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class DrugsServiceImpl extends ServiceImpl<DrugsMapper, Drugs> implements DrugsService {

    @Autowired
    private DrugsMapper drugsMapper;
    @Autowired
    private DrugsAliasService drugsAliasService;
    @Override
    public void add(Drugs drugs) {

        drugsMapper.insert(drugs);
    }

    @Override
    public List<Drugs> list(String keyWord) {
        List<String> nameList = drugsAliasService.list(keyWord);
        System.out.println("查询别名："+nameList.toString());

        // 将 keyWord 本身加入到 nameList 中
        nameList.add(keyWord);

        return drugsMapper.listByNames(nameList);
    }
}

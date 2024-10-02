package com.putileaf.healthify.service.impl;


import com.putileaf.healthify.mapper.DrugsAliasMapper;
import com.putileaf.healthify.service.DrugsAliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DrugsAliasServiceImpl implements DrugsAliasService {
    @Autowired private DrugsAliasMapper drugsAliasMapper;
    @Override
    public List<String> list(String keyWord) {

        return drugsAliasMapper.findNamesByKeyword(keyWord);


    }
}

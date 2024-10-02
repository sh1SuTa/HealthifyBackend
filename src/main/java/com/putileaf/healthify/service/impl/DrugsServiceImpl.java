package com.putileaf.healthify.service.impl;


import com.putileaf.healthify.entity.Drugs;
import com.putileaf.healthify.mapper.DrugsMapper;
import com.putileaf.healthify.service.DrugsAliasService;
import com.putileaf.healthify.service.DrugsService;
import com.putileaf.healthify.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class DrugsServiceImpl implements DrugsService {

    @Autowired
    private DrugsMapper drugsMapper;
    @Autowired
    private DrugsAliasService drugsAliasService;
    @Override
    public void add(Drugs drugs) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer)map.get("id");
        drugs.setCreateUser(userId);
        drugsMapper.insert(drugs);
    }

    @Override
    public List<Drugs> list(String keyWord) {
        List<String> nameList = drugsAliasService.list(keyWord);
        // 将 keyWord 本身加入到 nameList 中
        nameList.add(keyWord);
        return drugsMapper.listByNames(nameList);
    }
}

package com.putileaf.healthify.service.impl;


import com.putileaf.healthify.entity.Dose;
import com.putileaf.healthify.mapper.DoseMapper;
import com.putileaf.healthify.service.DoseAliasService;
import com.putileaf.healthify.service.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class DoseServiceImpl implements DoseService {

    @Autowired
    private DoseMapper doseMapper;
    @Autowired private DoseAliasService doseAliasService;
    @Override
    public void add(Dose dose) {
        doseMapper.insert(dose);

    }

    @Override
    public List<Dose> list(String keyWord) {
        List<String> nameList = doseAliasService.list(keyWord);



        return doseMapper.listByNames(nameList);
    }
}

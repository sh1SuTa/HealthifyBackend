package com.putileaf.healthify.service.impl;


import com.putileaf.healthify.entity.Dose;
import com.putileaf.healthify.mapper.DoseMapper;
import com.putileaf.healthify.service.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoseServiceImpl    implements DoseService {

    @Autowired
    private DoseMapper doseMapper;
    @Override
    public void add(Dose dose) {
        doseMapper.insert(dose);
    }
}

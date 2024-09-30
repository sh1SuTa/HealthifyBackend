package com.putileaf.healthify.service;



import com.putileaf.healthify.entity.Dose;

import java.util.List;

public interface DoseService {


    void add(Dose dose);

    List<Dose> list(String keyWord);
}

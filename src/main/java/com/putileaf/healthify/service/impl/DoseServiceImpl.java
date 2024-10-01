package com.putileaf.healthify.service.impl;


import com.putileaf.healthify.entity.Dose;
import com.putileaf.healthify.mapper.DoseMapper;
import com.putileaf.healthify.service.DoseAliasService;
import com.putileaf.healthify.service.DoseService;
import com.putileaf.healthify.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class DoseServiceImpl implements DoseService {

    @Autowired
    private DoseMapper doseMapper;
    @Autowired private DoseAliasService doseAliasService;
    @Override
    public void add(Dose dose) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer)map.get("id");
        dose.setCreateUser(userId);
        int i = doseMapper.insert(dose);

    }

    @Override
    public List<Dose> list(String keyWord) {
        List<String> nameList = doseAliasService.list(keyWord);
        // 将 keyWord 本身加入到 nameList 中
        nameList.add(keyWord);
        return doseMapper.listByNames(nameList);
    }
}

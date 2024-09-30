package com.putileaf.healthify.service.impl;

import com.putileaf.healthify.mapper.DoseAliasMapper;
import com.putileaf.healthify.service.DoseAliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DoseAliasServiceImpl implements DoseAliasService {

    @Autowired private DoseAliasMapper doseAliasMapper;

    @Override
    public List<String> list(String keyWord) {

        List<String>  nameList= doseAliasMapper.list(keyWord);

        // 使用 Java 9+ 的 Set.of 去重
        return nameList.stream()
                .collect(Collectors.toCollection(() -> new LinkedHashSet<>(nameList.size())))
                .stream()
                .toList();
    }
}

package com.putileaf.healthify.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DoseAliasMapper {

    @Select("select name from dose_alias where dose_alias like concat('%',#{keyWord},'%')")
    List<String> list(String keyWord);
}

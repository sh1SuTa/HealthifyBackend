package com.putileaf.healthify.mapper;

import com.putileaf.healthify.entity.Dose;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DoseMapper {

    @Insert("insert into dose(name, description, cover_img, create_user, create_time, update_time) " +
            "values (#{alias}, #{name}, #{description}, #{cover_img}, #{create_user}, #{create_time}, #{update_time})")
    void add(Dose dose);












}

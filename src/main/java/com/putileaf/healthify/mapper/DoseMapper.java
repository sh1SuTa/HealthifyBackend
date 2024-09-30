package com.putileaf.healthify.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.putileaf.healthify.entity.Dose;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DoseMapper extends BaseMapper<Dose> {


    List<Dose> listByNames(@Param("names") List<String> names);

}

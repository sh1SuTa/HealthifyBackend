package com.putileaf.healthify.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.putileaf.healthify.entity.Drugs;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DoseMapper extends BaseMapper<Drugs> {


    List<Drugs> listByNames(@Param("names") List<String> names);

}

package com.putileaf.healthify.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.putileaf.healthify.entity.DrugsAlias;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface DrugsAliasMapper extends BaseMapper<DrugsAlias> {

    List<String> findNamesByKeyword(@Param("keyword") String keyword);
}

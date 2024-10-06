package com.putileaf.healthify.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.putileaf.healthify.entity.ForumPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ForumMapper extends BaseMapper<ForumPost> {

    List<ForumPost> list(String searchQuery, String category);
}

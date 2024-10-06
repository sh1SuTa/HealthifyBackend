package com.putileaf.healthify.service.impl;

import com.putileaf.healthify.entity.ForumPost;
import com.putileaf.healthify.mapper.ForumMapper;
import com.putileaf.healthify.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {
    @Autowired
    private ForumMapper forumMapper;
    @Override
    public List<ForumPost> list(String searchQuery, String category) {
        return forumMapper.list(searchQuery, category);
    }

    @Override
    public void save(ForumPost forumPost) {
        forumMapper.insert(forumPost);
    }
}

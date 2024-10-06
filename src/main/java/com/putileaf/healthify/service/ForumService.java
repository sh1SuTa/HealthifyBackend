package com.putileaf.healthify.service;

import com.putileaf.healthify.entity.ForumPost;

import java.util.List;

public interface ForumService {

    List<ForumPost> list(String searchQuery, String category);

    void save(ForumPost forumPost);
}

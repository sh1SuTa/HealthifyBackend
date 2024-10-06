package com.putileaf.healthify.controller;

import com.putileaf.healthify.entity.ForumPost;
import com.putileaf.healthify.entity.Result;
import com.putileaf.healthify.service.ForumService;
import com.putileaf.healthify.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumController {
    @Autowired
    private ForumService forumService;

    @GetMapping ("/post")
    //searchQuery可以为空
    public Result<List<ForumPost>> list(@RequestParam(required = false) String searchQuery,
    @RequestParam(defaultValue = "health") String category) {
        List<ForumPost> list = forumService.list(searchQuery, category);

        System.out.println("成功post");
        return Result.success("success",list);
    }
    @PostMapping("/post")
    public Result<String> add(@RequestBody ForumPost forumPost) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer)map.get("id");
        forumPost.setCreateUser(userId);

        forumService.save(forumPost);
        return Result.success("success");
    }

}

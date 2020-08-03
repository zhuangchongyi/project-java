package com.zcy.split_table_db.controller;

import com.zcy.split_table_db.entity.Score;
import com.zcy.split_table_db.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/s/score")
public class ScoreController {
    @Autowired
    ScoreService scoreService;

    @GetMapping
    public List<Map<String, Object>> getUserScore(Integer userId) {
        return scoreService.getUserScore(userId);
    }

    @PostMapping
    public boolean save(Score score) {
        return scoreService.save(score);
    }

}

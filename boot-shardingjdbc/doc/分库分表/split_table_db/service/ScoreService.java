package com.zcy.split_table_db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zcy.split_table_db.entity.Score;

import java.util.List;
import java.util.Map;

public interface ScoreService extends IService<Score> {
    List<Map<String, Object>> getUserScore(Integer userId);
}

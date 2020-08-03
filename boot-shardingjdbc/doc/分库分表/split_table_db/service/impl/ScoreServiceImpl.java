package com.zcy.split_table_db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcy.split_table_db.dao.ScoreDao;
import com.zcy.split_table_db.entity.Score;
import com.zcy.split_table_db.service.ScoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreDao, Score> implements ScoreService {
    @Override
    public List<Map<String, Object>> getUserScore(Integer userId) {
        return baseMapper.getUserScore(userId);
    }
}

package com.zcy.split_table_db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zcy.split_table_db.entity.Score;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ScoreDao extends BaseMapper<Score> {
//    只有在两张表在同一个数据库中存在的时候才可以通过join的方式来获取数据
//    @Select("select s.id scoreId,u.id userId,s.score,u.name " +
//            "from score s,user u " +
//            "where u.id=s.user_id and u.id=#{userId}")
    @Select("select s.id scoreId,u.id userId,s.score,u.name " +
            "from score s,user_0 u " +
            "where u.id=s.user_id and u.id=#{userId}")
    List<Map<String, Object>> getUserScore(Integer userId);
}

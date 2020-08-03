package com.zcy.config.sharding.strategy;


import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Slf4j
public class ItemPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date>, HintShardingAlgorithm, ComplexKeysShardingAlgorithm {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        Date value = preciseShardingValue.getValue();
        // 按4个季度拆分表
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("MM");
        Integer val = Integer.valueOf(sdf.format(value));
        sdf.applyPattern("yyyy");
        // 获取设置的虚拟表名称，这里获取到的logicTableName=item
        String tableName = preciseShardingValue.getLogicTableName();
        log.debug(tableName);
        String table = new StringBuilder().append(tableName).append("_").append(sdf.format(value)).append("_").append(getNum(val)).toString();
        return table;
    }

    /**
     * 季度判断
     * @param val
     * @return
     */
    private int getNum(Integer val) {
        int num = 0;
        switch (val){
            case 3: case 4: case 5: num=1;
                break;
            case 6: case 7: case 8: num=2;
                break;
            case 9: case 10: case 11: num=3;
                break;
            case 12: case 1: case 2: num=4;
                break;
        }
        return num;
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Date> rangeShardingValue) {
        log.debug(rangeShardingValue.getColumnName());
        log.debug(rangeShardingValue.getLogicTableName());
        for (String s : collection) {
            log.debug(s);
        }
        return null;
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, ShardingValue shardingValue) {
        log.debug(shardingValue.getColumnName());
        log.debug(shardingValue.getLogicTableName());
        for (String s : collection) {
            log.debug(s);
        }
        return null;
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, Collection<ShardingValue> collection1) {
        for (String s : collection) {
            log.debug(s);
        }
        return null;
    }
}

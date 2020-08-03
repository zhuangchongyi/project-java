package com.zcy.config.sharding.strategy;


import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;

public class UserPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer>, RangeShardingAlgorithm<Integer> {
    private Integer shardingParam = 2;

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        return getTableName(preciseShardingValue.getValue(), preciseShardingValue.getLogicTableName());
    }

    private String getTableName(Integer value, String table) {
        int suffix = value % shardingParam;
        String tableName = new StringBuilder().append(table).append("_").append(suffix).toString();
        return tableName;
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Integer> rangeShardingValue) {
        return null;
    }
}

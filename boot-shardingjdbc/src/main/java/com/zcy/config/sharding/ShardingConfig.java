package com.zcy.config.sharding;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShardingConfig {
    @Value("${sharding.jdbc.data-source.names}")
    private String[] names;

//    @Bean(name = "shardingDataSource", destroyMethod = "close")
//    public DataSource getShardingDataSource() throws IOException {
//
//        Map<String, DataSource> dataSourceMap = new HashMap<>();
//        Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("application.properties"));
//        for (int i = 0; i < names.length; i++) {
//            String name = names[i];
//            String prefix = "sharding.jdbc.datasource.";
//            HikariDataSource dataSource = new HikariDataSource();
//            dataSource.setDriverClassName(properties.getProperty(prefix + name + ".driver-class-name"));
//            dataSource.setJdbcUrl(properties.getProperty(prefix + name + ".jdbc-url"));
//            dataSource.setUsername(properties.getProperty(prefix + name + ".username"));
//            dataSource.setPassword(properties.getProperty(prefix + name + ".password"));
//            dataSourceMap.put(name, dataSource);
//
//
//        }
//        TableRuleConfiguration itemTableRuleConfig = new TableRuleConfiguration();
//        String logicTable = "item";
//        itemTableRuleConfig.setLogicTable(logicTable);
//        String format = new SimpleDateFormat("yyyy").format(new Date());
//        StringBuilder sb = new StringBuilder();
//        for (int j = 0; j < 4; j++) {
//            sb.append(names[0])
//                    //.append("${0..1}")
//                    .append(".").append(logicTable).append("_").append(format).append("_").append(j + 1);
//            if (j < 4 - 1) {
//                sb.append(",");
//            }
//        }
//        itemTableRuleConfig.setActualDataNodes(sb.toString());
//
//        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
//        shardingRuleConfig.getTableRuleConfigs().add(itemTableRuleConfig);
//
//
//        // 获取数据源对象
//        DataSource dataSource = null;
//        try {
//            dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), new Properties());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return dataSource;
//    }
}

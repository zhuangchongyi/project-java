package com.zcy.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.zcy.mapper2", sqlSessionTemplateRef = "testdb2SqlSessionTemplate")
public class DataSourceConfig2 {

    @Bean(name = "testdb2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.testdb2")
    public DataSource testdb2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "testdb2SqlSessionFactory")
    public SqlSessionFactory testdb2SqlSessionFactory(@Qualifier("testdb2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean(name = "testdb2TransactionManager")
    public DataSourceTransactionManager testdb2TransactionManager(@Qualifier("testdb2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "testdb2SqlSessionTemplate")
    public SqlSessionTemplate testdb2SqlSessionTemplate(@Qualifier("testdb2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}

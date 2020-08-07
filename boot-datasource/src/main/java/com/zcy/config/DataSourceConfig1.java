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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.zcy.mapper1", sqlSessionTemplateRef = "testdb1SqlSessionTemplate")
public class DataSourceConfig1 {

    @Primary
    @Bean(name = "testdb1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.testdb1")
    public DataSource testdb1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "testdb1SqlSessionFactory")
    public SqlSessionFactory testdb1SqlSessionFactory(@Qualifier("testdb1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Primary
    @Bean(name = "testdb1TransactionManager")
    public DataSourceTransactionManager testdb1TransactionManager(@Qualifier("testdb1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "testdb1SqlSessionTemplate")
    public SqlSessionTemplate testdb1SqlSessionTemplate(@Qualifier("testdb1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}

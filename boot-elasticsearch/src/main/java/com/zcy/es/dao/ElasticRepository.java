package com.zcy.es.dao;

import com.zcy.es.bean.DocBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@Mapping
public interface ElasticRepository extends ElasticsearchRepository<DocBean, Long> {
    //@Query 参考 https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.query-methods.at-query

    //默认的注释
//    @Query("{\"match\": {\"content\": {\"query\": \"?0\"}}}")
    Page<DocBean> findByContent(String content, Pageable pageable);

    @Query("{\"bool\" : { \"must\" : [ { \"query_string\" : { \"query\" : \"?0\" ,\"fields\" : [ \"firstCode.keyword\" ] } } ] } }")
    Page<DocBean> findByFirstCode(String firstCode, Pageable pageable);

    @Query("{\"bool\" : { \"must\" : [ { \"query_string\" : { \"query\" : \"?*\" ,\"fields\" : [ \"secondCode\" ] } } ] } }")
    Page<DocBean> findBySecondCode(String secondCode, Pageable pageable);
}

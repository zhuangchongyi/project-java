package com.zcy.es.service;

import com.zcy.es.bean.DocBean;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface IElasticService  {
    void createIndex();

    void deleteIndex(String index);

    void delete(Long id);

    void save(DocBean docBean);

    void saveAll(List<DocBean> list);

    Iterator<DocBean> findAll();

    Optional<DocBean> findById(Long id);

    Page<DocBean> findByContent(String content);

    Page<DocBean> findByFirstCode(String firstCode);

    Page<DocBean> findBySecondCode(String secondCode);

}

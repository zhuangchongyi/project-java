package com.zcy.es.service.impl;

import com.zcy.es.bean.DocBean;
import com.zcy.es.dao.ElasticRepository;
import com.zcy.es.service.IElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ElasticServiceImpl implements IElasticService {
    @Autowired
    private ElasticsearchRestTemplate template;
    @Autowired
    private ElasticRepository repository;
    private Pageable pageable = PageRequest.of(0,10);

    @Override
    public void createIndex() {
        template.indexOps(DocBean.class).create();
    }

    @Override
    public void deleteIndex(String index) {
        template.indexOps(IndexCoordinates.of(index)).delete();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(DocBean docBean) {
        repository.save(docBean);
    }

    @Override
    public void saveAll(List<DocBean> list) {
        repository.saveAll(list);
    }

    @Override
    public Iterator<DocBean> findAll() {
        return repository.findAll().iterator();
    }
    @Override
    public Optional<DocBean> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<DocBean> findByContent(String content) {
        return repository.findByContent(content,pageable);
    }

    @Override
    public Page<DocBean> findByFirstCode(String firstCode) {
        return repository.findByFirstCode(firstCode,pageable);
    }

    @Override
    public Page<DocBean> findBySecondCode(String secondCode) {
        return repository.findBySecondCode(secondCode,pageable);
    }

}

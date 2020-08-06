package com.zcy.es.controller;

import com.zcy.es.bean.DocBean;
import com.zcy.es.service.IElasticService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/elastic")
public class ElasticController {
    @Autowired
    private IElasticService elasticService;

    @GetMapping("/init")
    public String init() {
        elasticService.createIndex();
        List<DocBean> list = new ArrayList<>();
        list.add(new DocBean(1L, "XX0193", "XX8064", "xxxxxx", 1));
        list.add(new DocBean(2L, "XX0210", "XX7475", "xxxxxxxxxx", 1));
        list.add(new DocBean(3L, "XX0257", "XX8097", "xxxxxxxxxxxxxxxxxx", 1));
        elasticService.saveAll(list);
        log.info("init success");
        return "init success";
    }

    @GetMapping("/all")
    public Iterator<DocBean> all() {
        return elasticService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<DocBean> get(@PathVariable("id") Long id) {
        return elasticService.findById(id);
    }

    @GetMapping("/find/{content}")
    public Page<DocBean> find(@PathVariable("content") String content) {
        return elasticService.findByContent(content);
    }

    @GetMapping("/find/code/{content}")
    public Page<DocBean> findByFirstCode(@PathVariable("content") String content) {
        return elasticService.findByFirstCode(content);
    }
    @GetMapping("/find/code2/{content}")
    public Page<DocBean> findByFirstCode2(@PathVariable("content") String content) {
        return elasticService.findBySecondCode(content);
    }

    @DeleteMapping("/index/{index}")
    public String delete(@PathVariable("index") String index) {
        elasticService.deleteIndex(index);
        return "delete index success";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        elasticService.delete(id);
        return "delete success";
    }

}
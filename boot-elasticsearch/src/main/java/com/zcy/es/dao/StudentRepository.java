package com.zcy.es.dao;

import com.zcy.es.bean.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends ElasticsearchRepository<Student, String> {
}

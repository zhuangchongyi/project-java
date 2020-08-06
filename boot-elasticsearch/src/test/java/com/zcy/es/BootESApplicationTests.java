package com.zcy.es;

import com.zcy.es.bean.Student;
import com.zcy.es.dao.StudentRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class BootESApplicationTests {
    @Autowired
    ElasticsearchRestTemplate template;
    @Autowired
    StudentRepository repository;

    @Test
    void createIndex() {
        boolean success = template.indexOps(Student.class).create();
        System.out.println("index create success=" + success);

    }

    @Test
    void saveAll() {
        ArrayList<Student> list = new ArrayList<>();
        String studentId = UUID.randomUUID().toString();
        System.out.println(studentId);
        list.add(new Student(studentId, "张三", new Random().nextInt(20), new Random().nextDouble() * 100));
        list.add(new Student(UUID.randomUUID().toString(), "张四", new Random().nextInt(20), new Random().nextDouble() * 100));
        list.add(new Student(UUID.randomUUID().toString(), "张三三", new Random().nextInt(20), new Random().nextDouble() * 100));
        list.add(new Student(UUID.randomUUID().toString(), "张小三", new Random().nextInt(20), new Random().nextDouble() * 100));
        list.add(new Student(UUID.randomUUID().toString(), "张大三", new Random().nextInt(20), new Random().nextDouble() * 100));
        list.add(new Student(UUID.randomUUID().toString(), "老张", new Random().nextInt(20), new Random().nextDouble() * 100));
        list.add(new Student(UUID.randomUUID().toString(), "老三", new Random().nextInt(20), new Random().nextDouble() * 100));
        list.add(new Student(UUID.randomUUID().toString(), "老三哥", new Random().nextInt(20), new Random().nextDouble() * 100));
        list.add(new Student(UUID.randomUUID().toString(), "阿三", new Random().nextInt(20), new Random().nextDouble() * 100));
        list.add(new Student(UUID.randomUUID().toString(), "阿三哥", new Random().nextInt(20), new Random().nextDouble() * 100));
        list.add(new Student(UUID.randomUUID().toString(), "三三", new Random().nextInt(20), new Random().nextDouble() * 100));
        list.add(new Student(UUID.randomUUID().toString(), "三哥哥", new Random().nextInt(20), new Random().nextDouble() * 100));
        repository.saveAll(list);

        System.err.println("save all success," + studentId);

    }

    @Test
    public void search() {
        String id = "683f690b-eab5-49bb-88d4-b703b1ffe278";
        Optional<Student> student = repository.findById(id);
        System.out.println(student);
        System.err.println("--------------------------------------------");
        Pageable pageable = PageRequest.of(0, 10);
        System.err.println("--------------------------------------------");

        NativeSearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name","张三"))
                .build();
        SearchHits<Student> searchHits = template.search(queryBuilder, Student.class);
        searchHits.get().forEach(hit->{
            Student stu = hit.getContent();
            System.out.println(stu);
        });

    }

}

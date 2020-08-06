package com.zcy.es.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@Document(indexName = "student_index", type = "student")
public class Student {
    public Student(String studentId, String name, Integer age, Double scores) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.scores = scores;
    }

    @Id
    @Field(type = FieldType.Auto)
    private String studentId;

    @Field(type = FieldType.Auto)
    private String name;

    @Field(type = FieldType.Auto)
    private Integer age;

    @Field(type = FieldType.Auto)
    private Double scores;
}

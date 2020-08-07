package com.zcy.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Data
@Accessors(chain = true)
public class SysUser {
    private Long id;
    private String name;
    private String gender;
    private Integer age;
}

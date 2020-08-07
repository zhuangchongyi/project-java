package com.zcy.mapper1;

import com.zcy.entity.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Select("select * from sys_user ")
    List<SysUser> findSysUserAll();

    @Select("select * from sys_user where id=#{id}")
    SysUser findSysUserById(Long id);

    @Insert("insert into sys_user(id,name,gender,age) values(#{id},#{name},#{gender},#{age})")
    int insert(SysUser user);

    @Update("update sys_user set name=#{name},gender=#{gender},age=#{age} where id={id}")
    int update(SysUser user);

    @Delete("delete from sys_ser where id=#{id}")
    int delete(Long id);
}

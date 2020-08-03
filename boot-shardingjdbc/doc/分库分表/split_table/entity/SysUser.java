package com.zcy.split_table.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@TableName("user")
public class SysUser extends Model<SysUser> {
    @TableId
    private int id;
    private String name;
    private int age;
    private int gender;
}

package com.zcy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@TableName("item")
public class Item extends Model<Item> {
    @TableId
    private Long id;
    private String title;
    private BigDecimal price;
    private Integer amount;
    private Date createTime;
}

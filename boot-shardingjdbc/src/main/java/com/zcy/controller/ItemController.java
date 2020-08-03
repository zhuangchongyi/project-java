package com.zcy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zcy.entity.Item;
import com.zcy.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public boolean insert(Item item) {
        return itemService.save(item);
    }

    @GetMapping
    public List<Item> list() {
        return itemService.list();
    }

    @GetMapping("/{current}/{size}")
    public IPage<Item> page(@PathVariable("current") int current, @PathVariable("size") int size) {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("create_time");
        return itemService.page(new Page<>(current, size), queryWrapper);
    }


}

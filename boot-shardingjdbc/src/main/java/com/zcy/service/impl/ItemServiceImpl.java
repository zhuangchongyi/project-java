package com.zcy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcy.service.ItemService;
import com.zcy.dao.ItemDao;
import com.zcy.entity.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemDao, Item> implements ItemService {
}

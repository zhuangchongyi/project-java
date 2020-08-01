package com.zcy.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author zhuangchongyi
 * @Description DAO基础接口，用来被其他DAO继承
 * @Date 2020/7/31 17:08
 */
@NoRepositoryBean
public interface BaseDao<T, I> extends PagingAndSortingRepository<T, I>, JpaSpecificationExecutor<T> {
}

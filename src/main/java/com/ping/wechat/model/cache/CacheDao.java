package com.ping.wechat.model.cache;

import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created  on 2019/2/25.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 * example
 *
 */
public interface  CacheDao {
    List<CacheBean> queryAll();

    CacheBean findUserById(int id);

    int updateUser(@Param("cacheBean") CacheBean cacheBean);

    int deleteUserById(int id);
}

package com.ping.wechat.model.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created  on 2019/2/25.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 * example
 */
public class CacheService {
    @Autowired
    private CacheDao cacheDao;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<CacheBean> queryAll() {
        return cacheDao.queryAll();
    }
    /**
     * 获取用户策略：先从缓存中获取用户，没有则取数据表中 数据，再将数据写入缓存
     */
    public CacheBean findUserById(int id) {
        String key = "cacheBean_" + id;

        ValueOperations<String, CacheBean> operations = redisTemplate.opsForValue();

        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            CacheBean user = operations.get(key);
            System.out.println("==========从缓存中获得数据=========");
            System.out.println(user.getUserName());
            System.out.println("==============================");
            return user;
        } else {
            CacheBean cacheBean = cacheDao.findUserById(id);
            System.out.println("==========从数据表中获得数据=========");
            System.out.println(cacheBean.getUserName());
            System.out.println("==============================");

            // 写入缓存
            operations.set(key, cacheBean, 5, TimeUnit.HOURS);
            return cacheBean;
        }

    }

    /**
     * 更新用户策略：先更新数据表，成功之后，删除原来的缓存，再更新缓存
     */
    public int updateUser(CacheBean user) {
        ValueOperations<String, CacheBean> operations = redisTemplate.opsForValue();
        int result = cacheDao.updateUser(user);
        if (result != 0) {
            String key = "cacheBean_" + user.getUid();
            boolean haskey = redisTemplate.hasKey(key);
            if (haskey) {
                redisTemplate.delete(key);
                System.out.println("删除缓存中的key=========>" + key);
            }
            // 再将更新后的数据加入缓存
            CacheBean cacheBean = cacheDao.findUserById(user.getUid());
            if (cacheBean != null) {
                operations.set(key, cacheBean, 3, TimeUnit.HOURS);
            }
        }
        return result;
    }

    /**
     * 删除用户策略：删除数据表中数据，然后删除缓存
     */
    public int deleteUserById(int id) {
        int result = cacheDao.deleteUserById(id);
        String key = "cacheBean_" + id;
        if (result != 0) {
            boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey) {
                redisTemplate.delete(key);
                System.out.println("删除了缓存中的key:" + key);
            }
        }
        return result;
    }


}

package com.alibaba.cobarclient.route;

/**
 * 分表路由，需要自己在业务里面实现
 * @author kevin
 */
public interface ShardTableRouter<T, R> {
    R route(T t);
}

package com.alibaba.cobarclient;

/**
 * @author kevin
 */
public interface ShardModel<T> {
    String SHARD_TABLE_FIELD="table_id";
    /**
     * 获取分表 id
     *
     * @return
     */
    T getTableId();

    /**
     * 设置分表 id
     *
     * @param t
     */
    void setTableId(T t);
}

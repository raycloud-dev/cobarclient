package com.alibaba.cobarclient;

import java.util.HashMap;

/**
 * @author kevin
 */
public class ShardTableMap extends HashMap<String, Object> implements ShardModel {
    public Object getTableId() {
        return get(SHARD_TABLE_FIELD);
    }

    public void setTableId(Object o) {
        put(SHARD_TABLE_FIELD, o);
    }
}

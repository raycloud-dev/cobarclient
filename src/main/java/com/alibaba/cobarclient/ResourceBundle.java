package com.alibaba.cobarclient;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.concurrent.Future;

/**
 * @author kevin
 */
public class ResourceBundle<T> {
    private Future<T> future;
    private Connection connection;
    private DataSource dataSource;

    ResourceBundle(Connection connection, DataSource dataSource, Future<T> future) {
        this.connection = connection;
        this.dataSource = dataSource;
        this.future = future;
    }

    Future<T> getFuture() {
        return future;
    }

    Connection getConnection() {
        return connection;
    }

    DataSource getDataSource() {
        return dataSource;
    }
}

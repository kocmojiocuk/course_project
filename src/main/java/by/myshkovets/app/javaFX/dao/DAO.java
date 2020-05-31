package by.myshkovets.app.javaFX.dao;

import by.myshkovets.app.javaFX.entity.Entity;

import java.sql.Statement;
import java.util.List;

public interface DAO <T extends Entity> {
    List<T> findAll();
    boolean delete(T entity);
    int create(T entity);
    boolean update(T entity);

    boolean closeStatement(Statement statement);
}

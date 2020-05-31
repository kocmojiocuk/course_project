package by.myshkovets.app.javaFX.dao.goalsDAO;

import by.myshkovets.app.javaFX.entity.tables.Goal;
import by.myshkovets.app.javaFX.dao.DAO;

import java.util.List;

public interface GoalsDAO extends DAO<Goal> {
    List<Goal> findAll();
}

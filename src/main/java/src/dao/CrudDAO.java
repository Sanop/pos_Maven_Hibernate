package src.dao;

import entity.SuperEntity;

import java.io.Serializable;
import java.util.List;

public interface CrudDAO <T extends SuperEntity,ID extends Serializable> extends SuperDAO{
    List<T> findAll()throws Exception;

    T find(ID pk)throws Exception;

    void add(T entity)throws Exception;

    void update(T entity)throws Exception;

    void delete(ID pk)throws Exception;
}


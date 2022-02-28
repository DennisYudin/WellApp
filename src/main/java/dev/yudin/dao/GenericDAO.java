package dev.yudin.dao;

import dev.yudin.exceptions.DataNotFoundException;

public interface GenericDAO<T> {

    T getByName(String name);

    void save(T t);

    default boolean doesExist(String name) {
        boolean valueExist = true;
        try {
            getByName(name);
        } catch (DataNotFoundException ex) {
            valueExist = false;
        }
        return valueExist;
    }
}

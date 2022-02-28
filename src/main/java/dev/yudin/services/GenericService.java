package dev.yudin.services;

public interface GenericService<T> {

    boolean doesExistValue(String name);

    void save(T t);
}

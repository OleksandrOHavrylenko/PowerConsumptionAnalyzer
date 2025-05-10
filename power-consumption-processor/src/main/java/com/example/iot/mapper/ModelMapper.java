package com.example.iot.mapper;

/**
 * @author Oleksandr Havrylenko
 **/
public interface ModelMapper<T> {
    T parseLine(String nextLine);
}

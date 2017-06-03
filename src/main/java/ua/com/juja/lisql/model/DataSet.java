package ua.com.juja.lisql.model;

import java.util.List;
import java.util.Set;

/**
 *
 */
public interface DataSet {
    void put(String name, Object value);

    List<Object> getValues();

    Set<String> getNames();

    Object get(String name);

    String[] getNames(Object o);

    Object getType(String name);

    void update(DataSet source);
}

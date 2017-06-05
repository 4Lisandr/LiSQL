package ua.com.juja.lisql.model;

import java.util.*;
import java.util.stream.Collectors;

public class DataSetImpl implements DataSet {

    private Map<String, Object> data = new LinkedHashMap<String, Object>();

    @Override
    public void put(String name, Object value) {
        data.put(name, value);
    }

    @Override
    public List<Object> getValues() {
        return new ArrayList<Object>(data.values());
    }

    @Override
    public Set<String> getNames() {
        return data.keySet();
    }

    @Override
    public Object get(String name){
        return data.get(name);
    }

    @Override
    public String[] findNames(Object o){
        if (!data.containsValue(o))
            return new String[0];

        return getKeysByValue(data, o)
                .stream()
                .toArray(String[]::new);
    }

    // https://stackoverflow.com/questions/1383797/java-hashmap-how-to-get-key-from-value
    private static  <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public Object getType(String name){
        return get(name).getClass().getTypeName();
    }

    @Override
    public void update(DataSet source) {
        Set<String> columns = source.getNames();
        for (String name : columns) {
            Object data = source.get(name);
            put(name, data);
        }
    }

    private static String getFormatted(Iterable iterable, String format) {
        String string = "";
        for (Object o : iterable) {
            string += String.format(format, o);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    @Override
    public String getNamesFormatted(String format) {
        return getFormatted(getNames(), format);
    }

    @Override
    public String getValuesFormatted(String format) {
        return getFormatted(getValues(), format);
    }


    @Override
    public String toString() {
        return "{" +
                "names:" + getNames().toString() + ", " +
                "values:" + getValues().toString() +
                "}";
    }

}

package ua.com.juja.lisql.model;

import java.util.HashSet;
import java.util.Set;

public class DataSet {

    static class Data {
        private String name;
        private Object value;

        public Data(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }
    // Unique values only
    public Set<Data> data = new HashSet<>();

    public void put(String name, Object value) {
        data.add(new Data(name, value));
    }

    public Set<Object> getValues() {
        Set<Object> result = new HashSet<>();
        for (Data d: data) {
            result.add(d.getValue());
        }
        return result;
    }

    public Set<String> getNames() {
        Set<String> result = new HashSet<>();
        for (Data d: data) {
            result.add(d.getName());
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DataSet{\n");
        for (Data d: data){
            sb.append("name: ").append(d.getName());
            sb.append("value: ").append(d.getValue()).append("\n");
        }
        sb.append("}");

        return sb.toString();
    }
}

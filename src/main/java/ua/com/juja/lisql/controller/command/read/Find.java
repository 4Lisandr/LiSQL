package ua.com.juja.lisql.controller.command.read;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.controller.command.TextBuilder;
import ua.com.juja.lisql.model.DataSet;
import ua.com.juja.lisql.model.DatabaseManager;
import ua.com.juja.lisql.view.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ua.com.juja.lisql.controller.command.TextBundle.FIND;

public class Find extends Command {

    public Find(DatabaseManager manager, View view) {
        super(manager, view);
        setTextBuilder(new TextBuilder("find|sample", FIND.toString()));
    }

    @Override
    public void process(String command) {

        String tableName = validArguments(command)[1];

        Map<String, List<String>> table = new LinkedHashMap<>();
        List<String> tableColumns = manager.getTableColumns(tableName);
        List<DataSet> tableData = manager.getTableData(tableName);
        putHeader(tableColumns, table);
        putData(tableData, table);

        view.write(table);
    }

    private void putHeader(Iterable<String> tableColumns, Map <String, List<String>>map) {
        for(String name: tableColumns){
            map.put(name, new ArrayList<String>());
        }
    }

    private void putHeader(Iterable<String> tableColumns, List<List<String>> table) {
        for(String name: tableColumns){
            List<String> head = new ArrayList<String>();
            head.add(name);
            table.add(head);
        }
    }

    private void putData(List<DataSet> tableData, Map <String, List<String>>map) {
        for (DataSet row : tableData) {
            putRow(row, map);
        }
    }

    private void putRow(DataSet row, Map<String, List<String>> map) {
        if(row.size()!=map.size())
            throw new IllegalArgumentException("Different sizes of data!");

        for (Map.Entry<String, List<String>> entry : map.entrySet()){
            String matchedValue = (String) row.get(entry.getKey());
            entry.getValue().add(matchedValue);
        }
    }
}

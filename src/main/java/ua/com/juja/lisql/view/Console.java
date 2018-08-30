package ua.com.juja.lisql.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Console implements View {

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override // CMD_LIST
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public void write(String... messages) {
        write(Line.concat(messages));
    }

    @Override
    public void write(List<List<?>> table) {
        //todo заменить специальным объектом, который принимает список списков,
        // а возвращает массив строк максимальная ширина консоли - 180
        printHeader(table.get(0));
        for (int i = 1; i < table.size(); i++) {
            write(Line.concat(2, "|", table.get(i)));
        }
    }

    private void printHeader(Iterable<?> tableColumns) {
        String header = Line.concat(2, "|", tableColumns);
        String horizontal = horizontal(header.length());
        if (header.length() > 0)
            header = Line.concat(1, Line.SEPARATOR,
                    horizontal,
                    header,
                    horizontal);
        else
            header = "No content in this table!";

        write(header);
    }

    private String horizontal(int length) {
        List<String> list = new ArrayList();
        for (int i = 0; i < length; i++) {
            list.add(Line.HORIZONTAL);
        }
        return Line.concat(-1, "", list);
    }

    public int userEvent(String msg, String... answers) {
        write(msg);
        String read = read();
        for (int i = 0; i < answers.length; i++) {
            if (read.equalsIgnoreCase(answers[i]))
                return i;
        }

        return -1;
    }

    @Override
    public boolean confirm() {
        write("Are you sure (Y/N)?");
        return read().equals("Y");
    }

    static class Table {
        int[] maxLength;
        String[][] content;
        static final int MAX_WIDTH = 180;

        Table(List<List<?>> data) {
            if (data == null || data.size() == 0)
                return;

            int rows = data.size();
            int col = data.get(0).size();
            maxLength = new int[col];

            content = new String[rows][col];

            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < col; x++) {
                    String s = data.get(y).get(x).toString();
                    content[y][x] = s;

                    if (maxLength[x] >= s.length()) {
                        continue;
                    }
                    maxLength[x] = s.length();
                }
            }
        }

        private String makeRows() {
            StringBuilder result = new StringBuilder();
            for (int y = 0; y < content.length; y++) {
                result.append(makeRow(y)).append(Line.SEPARATOR);
            }
            return result.toString();
        }

        private String makeRow(int row) {
            StringBuilder line = new StringBuilder("| ");
            for (int x = 0; x < maxLength.length; x++) {
                String str = content[row][x];
                line.append(str);
                int diff = maxLength[x] - str.length();
                for (int i = 0; i < diff; i++) {
                    line.append(" ");
                }
                line.append("| ");
            }
            return line.toString();
        }
    }

    public static void main(String[] args) {
        String[][] pocketArray = {{"STATUS CHANGERS", "RUNES", "KEY ITEMS"},
                {"POTION", "SUPER POTION", "FULL HEAL"}, {"ARMOR+", "ATTACK+", "EXP+"},
                {"QUEST ITEMS", "STORY ITEMS", "JOURNAL"}};

        List<List<?>> pocketList = new ArrayList<>(pocketArray.length);
        for (String[] pocket : pocketArray) {
            List<String> currentSubList = new ArrayList<>(pocket.length);
            Collections.addAll(currentSubList, pocket);
            pocketList.add(currentSubList);
        }

        System.out.println(new Table(pocketList).makeRows());
    }
}

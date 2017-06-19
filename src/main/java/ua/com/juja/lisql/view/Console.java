package ua.com.juja.lisql.view;

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
        printHeader(table.get(0));
        for (int i = 1; i < table.size() ; i++) {
            write(Line.concat(2, "|", table.get(i)));
        }
    }

    private void printHeader(Iterable<?> tableColumns) {
        String header = Line.concat(2, "|", tableColumns);
        if (header.length() > 0)
            header = Line.concat(1, Line.SEPARATOR,
                    Line.HORIZONTAL,
                    header,
                    Line.HORIZONTAL);
        else
            header = "No content in this table!";

        write(header);
    }

    public int userEvent(String msg, String... answers){
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
}

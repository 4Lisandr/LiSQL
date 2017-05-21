package ua.com.juja.lisql.view;

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

    //
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

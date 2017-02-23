package ua.com.juja.lisql.view;

import java.util.Scanner;

public class Console implements View {

    public static final String SEPARATOR = System.getProperty("line.separator");

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void write(String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message: messages){
            sb.append(message).append(SEPARATOR);
        }
        write(sb.toString());
    }

}

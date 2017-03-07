package ua.com.juja.lisql.view;

import java.util.Scanner;

public class Console implements View {

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override // HELP
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public void write(String... messages) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messages.length-1; i++) {
            sb.append(messages[i]).append(Line.SEPARATOR);
        }
        sb.append(messages[messages.length-1]);
        write(sb.toString());
    }

}

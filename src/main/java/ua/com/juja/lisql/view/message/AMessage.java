package ua.com.juja.lisql.view.message;

public class AMessage implements Message {
    String message;

    public AMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

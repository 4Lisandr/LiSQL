package ua.com.juja.lisql.controller.command;

import ua.com.juja.lisql.view.View;

public abstract class ViewCommand implements Command {

    private View view;

    public ViewCommand(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}

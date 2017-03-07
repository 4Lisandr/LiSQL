package ua.com.juja.lisql.controller.command.utils;

import ua.com.juja.lisql.controller.command.Command;
import ua.com.juja.lisql.view.EMessage;
import ua.com.juja.lisql.view.View;

public class Unknown extends Command {

    public Unknown(View view){
        super(view);
        setAttributes("unknown", "the truth is out there...", EMessage.UNKNOWN.toString(),
            "This top secret level of this program, how do get this message at all?!!");
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override // A non-existent command was successfully detected and rendered harmless
    public void process(String command) {
        view.write(success() + " " + command, "");
    }
}

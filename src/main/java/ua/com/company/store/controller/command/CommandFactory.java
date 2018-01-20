package ua.com.company.store.controller.command;

/**
 * Created by Владислав on 22.11.2017.
 */
public class CommandFactory {
    private CommandFactory() {
    }
    public static CommandTypical getCommand(String commandKey){
        return CommandEnum.getCommand(commandKey);
    }
}
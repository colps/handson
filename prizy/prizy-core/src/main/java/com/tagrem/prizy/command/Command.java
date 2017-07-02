package com.tagrem.prizy.command;

public interface Command<T> {

    public CommandResult<T> execute();

}

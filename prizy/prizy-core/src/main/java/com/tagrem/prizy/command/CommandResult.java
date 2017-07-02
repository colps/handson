package com.tagrem.prizy.command;

public class CommandResult<T> {

    private final T response;
    private final Status status;
    private final Message message;


    public CommandResult(T response, Status status, Code code, Object... params) {
        this.response = response;
        this.status = status;
        this.message = new Message(code, params);
    }

    public Status getStatus() {
        return status;
    }

    public T getResponse() {
        return response;
    }

    public enum Status {
        OK, ERROR;
    }

    public enum Code {

        SUCCESS("001", "Operation %s  executed Successfully"),
        INTERNAL_ERROR("002", "An error has occurred during execution");

        private String code;
        private String message;

        Code(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class Message {
        private final Code code;
        private final Object[] params;


        public Message(Code code, Object... params) {
            this.code = code;
            this.params = params;
        }

        public String getDescription(){
            return String.format(code.getMessage(), params);
        }

        public Code getCode() {
            return code;
        }

        public Object[] getParams() {
            return params;
        }
    }

}

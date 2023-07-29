package com.viettel.documentdigitization.configuration;

public class Constants {

    public static class ResponseCode {
        public static final int SUCCESS = 200;
        public static final int NOT_FOUND = 404;
        public static final int ERROR = 400;
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int FORBIDDEN = 403;
    }

    public static class ResponseMessage {
        public static final String SUCCESS = "Success!";
        public static final String INTERNAL_SERVER_ERROR = "Internal server error!";
    }

}

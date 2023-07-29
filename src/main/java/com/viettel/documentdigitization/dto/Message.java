package com.viettel.documentdigitization.dto;

import com.viettel.documentdigitization.configuration.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String message;
    private int code;

    public static final Message success() {
        return new Message(
                Constants.ResponseMessage.SUCCESS,
                Constants.ResponseCode.SUCCESS
        );
    }

    public static final Message internal() {
        return new Message(
                Constants.ResponseMessage.INTERNAL_SERVER_ERROR,
                Constants.ResponseCode.INTERNAL_SERVER_ERROR
        );
    }

}

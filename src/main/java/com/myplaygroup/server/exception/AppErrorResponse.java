package com.myplaygroup.server.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

@Getter
public class AppErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final Date timestamp;
    private final int code;
    private final String status;
    private final String message;
    private Object data;

    public AppErrorResponse(
            HttpStatus httpStatus,
            Exception ex
    ){
        this(httpStatus, ex.getMessage(), null);
    }

    public AppErrorResponse(
            HttpStatus httpStatus,
            String message,
            Object data
    ) {
        this.timestamp = new Date();
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;

        if(data != null){
            this.data = data;
        }
    }
}

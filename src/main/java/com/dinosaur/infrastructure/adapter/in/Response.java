package com.dinosaur.infrastructure.adapter.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private String message;
    private int status;
    private Object data;
}

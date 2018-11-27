package com.micro.darren.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JsonResult implements Serializable{

    private Integer resultCode;

    private String resultMessage;

    private Object data;

    @Override
    public String toString() {
        return super.toString();
    }
}

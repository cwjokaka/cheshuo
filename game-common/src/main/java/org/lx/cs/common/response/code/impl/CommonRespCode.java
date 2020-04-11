package org.lx.cs.common.response.code.impl;

import com.fasterxml.jackson.annotation.JsonValue;
import org.lx.cs.common.response.code.RespCode;

public enum CommonRespCode implements RespCode {

    SUCCESS(200),
    FAILURE(300),
    SYSTEM_ERROR(500);
    ;

    @JsonValue
    private int code;
    private String name;

    CommonRespCode(int code) {
        this.code = code;
        this.name = this.name();
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

}

package org.lx.cs.common.response;

import lombok.Data;
import org.lx.cs.common.response.code.RespCode;

@Data
public class CommonResp<T> {

    private RespCode code;
    private String message;
    private T data;

    public CommonResp(RespCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResp(RespCode code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}

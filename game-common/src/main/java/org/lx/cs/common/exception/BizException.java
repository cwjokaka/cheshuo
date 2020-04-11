package org.lx.cs.common.exception;


import org.lx.cs.common.response.code.RespCode;

public class BizException extends RuntimeException {

    private RespCode code;

    public BizException(RespCode code) {
        this.code = code;
    }

    public RespCode getCode() {
        return code;
    }

    public void setCode(RespCode code) {
        this.code = code;
    }
}

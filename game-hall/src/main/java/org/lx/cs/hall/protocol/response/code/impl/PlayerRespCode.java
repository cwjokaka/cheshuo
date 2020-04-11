package org.lx.cs.hall.protocol.response.code.impl;

import com.fasterxml.jackson.annotation.JsonValue;
import org.lx.cs.common.response.code.RespCode;

public enum PlayerRespCode implements RespCode {

    //100表示逻辑服务器  001表示玩家模块 后三位表示错误码
    PLAYER_ALREADY_EXISTS(100001000),
    PLAYER_NOT_FOUND(100001001),
    PLAYER_CREATE_SUCCESS(100001002),
    ;

    @JsonValue
    private int code;
    private String name;

    PlayerRespCode(int code) {
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

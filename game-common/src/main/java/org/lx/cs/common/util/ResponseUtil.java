package org.lx.cs.common.util;

import org.lx.cs.common.response.CommonResp;
import org.lx.cs.common.response.code.RespCode;
import org.lx.cs.common.response.code.impl.CommonRespCode;

public class ResponseUtil {

    private ResponseUtil() {
    }

    public static <T> CommonResp<T> ok() {
        return new CommonResp<>(
                CommonRespCode.SUCCESS,
                MessageSourceUtil.getMessage(CommonRespCode.SUCCESS.getName())
        );
    }

    public static <T> CommonResp<T> ok(RespCode respCode) {
        return new CommonResp<>(
                respCode,
                MessageSourceUtil.getMessage(respCode.getName())
        );
    }

    public static <T> CommonResp<T> ok(T data) {
        CommonResp<T> commonResult = new CommonResp<>(CommonRespCode.SUCCESS, MessageSourceUtil.getMessage(CommonRespCode.SUCCESS.getName()));
        commonResult.setData(data);
        return commonResult;
    }

    public static <T> CommonResp<T> error() {
        return new CommonResp<>(CommonRespCode.SYSTEM_ERROR, MessageSourceUtil.getMessage(CommonRespCode.SYSTEM_ERROR.getName()));
    }

    public static <T> CommonResp<T> error(RespCode respCode) {
        return new CommonResp<>(respCode, MessageSourceUtil.getMessage(respCode.getName()));
    }


}

package org.lx.framework.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lx.framework.annotation.MessageMeta;

/**
 * @author tanjl
 * @date 2022/5/20 11:07
 */
@EqualsAndHashCode(callSuper = true)
@MessageMeta(module = -1, cmd = 0)
@Data
public class ErrorMessage extends Message {

    private int code;
    private String text;

    public ErrorMessage(int code, String text) {
        this.code = code;
        this.text = text;
    }

}

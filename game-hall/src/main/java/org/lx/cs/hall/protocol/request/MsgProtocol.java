package org.lx.cs.hall.protocol.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class MsgProtocol {

    private ProtocolType type;
    private Long userId;
    public JsonNode data;


}

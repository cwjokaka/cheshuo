package org.lx.cs.hall.protocol.request.data;

import lombok.Data;

@Data
public class CreateRoleReq {

    private Long userId;
    private String name;

}

package org.lx.cs.hall.protocol.request;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.lx.cs.hall.service.ISrv;
import org.lx.cs.hall.service.impl.TestSrv;
import org.lx.cs.hall.service.impl.player.CreatePlayerSrv;
import org.lx.cs.hall.service.impl.player.QueryPlayerInfoSrv;
import org.lx.cs.hall.service.impl.pvp.GetPvPServerInfoSrv;

import static org.lx.cs.common.util.SpringUtil.getBean;


/**
 * 协议类型
 */
@Getter
public enum ProtocolType {

    TEST(0, getBean(TestSrv.class)),
    GET_PVP_SERVER_INFO(1, getBean(GetPvPServerInfoSrv.class)),
    CREATE_PLAYER(2, getBean(CreatePlayerSrv.class)),
    QUERY_PLAYER_INFO(3, getBean(QueryPlayerInfoSrv.class))
    ;

    @JsonValue
    private int code;
    private ISrv srv;
    private String name;

    ProtocolType(int code, ISrv srv) {
        this.code = code;
        this.name = this.name();
        this.srv = srv;
    }


}

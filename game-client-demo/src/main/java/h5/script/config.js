const PATH_PREF = "./protobuf/";
const MODULE_CMD_PROTO = [
    // 心跳
    {
        "module": 0,
        "cmds": [
            {"cmd": 65, "proto": "HeartBeatResp"},
        ]
    },
    // GM模块
    {
        "module": 1,
        "cmds": [
            {"cmd": 1, "proto": "GmKickReq"},
            {"cmd": 65, "proto": "GmKickResp"}
        ]
    },
    // 聊天模块
    {
        "module": 101,
        "cmds": [
            {"cmd": 0, "proto": "PrivateChatReq"},
            {"cmd": 64, "proto": "PrivateChatResp"}
        ]
    },
    // 用户模块
    {
        "module": 100,
        "cmds": [
            {"cmd": 0, "proto": "LoginReq"},
            {"cmd": 64, "proto": "LoginResp"},
            {"cmd": 1, "proto": "LogoutReq"},
            {"cmd": 65, "proto": "LogoutResp"},
            {"cmd": 2, "proto": "RegisterReq"},
            {"cmd": 66, "proto": "RegisterResp"},
        ]
    },
];
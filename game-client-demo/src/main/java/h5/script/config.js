const PATH_PREF = "./protobuf/";
const MODULE_CMD_PROTO = [
    // GM模块
    // {"module": 0, "cmd": 0, "proto": "GmMessage"},
    // {"module": 0, "cmd": 1, "proto": "GmMessage"},
    {"module": 0, "cmd": 1, "proto": "GmKickReq"},
    {"module": 0, "cmd": 65, "proto": "GmKickResp"},
    // 聊天模块
    {"module": 101, "cmd": 0, "proto": "PrivateChatReq"},
    {"module": 101, "cmd": 64, "proto": "PrivateChatResp"},
    // 用户模块
    {"module": 100, "cmd": 0, "proto": "LoginReq"},
    {"module": 100, "cmd": 64, "proto": "LoginResp"},

    {"module": 100, "cmd": 1, "proto": "LogoutReq"},
    {"module": 100, "cmd": 65, "proto": "LogoutResp"},

    {"module": 100, "cmd": 2, "proto": "RegisterReq"},
    {"module": 100, "cmd": 66, "proto": "RegisterResp"},
];
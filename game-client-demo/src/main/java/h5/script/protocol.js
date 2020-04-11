console.log("初始化协议表...");
const PROTOCOL_TAB = Object.create(null);
for (let i=0, len=MODULE_CMD_PROTO.length; i<len; i++) {
    let elem = MODULE_CMD_PROTO[i];
    let module = elem["module"];
    let cmd = elem["cmd"];
    let proto = elem["proto"];
    let key = buildKey(module, cmd);
    if (!proto) {
        continue;
    }
    protobuf.load(PATH_PREF + proto + ".proto", function (err, root) {
        if (err) {
            throw err;
        }
        var messageCodec = root.lookupType(proto);
        if (!messageCodec) {
            throw new Error("找不到对应的.proto文件:" + PROTOCOL_TAB[key]);
        }
        console.log(`key:${key}加入协议表成功`);
        PROTOCOL_TAB[key] = messageCodec;
    });
}

function buildKey(module, cmd) {
    return module * 1000 + cmd;
}

function decode(module, cmd, params) {
    let codec = PROTOCOL_TAB[buildKey(module, cmd)];
    if (!codec) {
        console.warn(`没找到对应的解码器, module:${module}, cmd:${cmd}`);
        return;
    }
    return codec.decode(params);
}

function encode(module, cmd, params) {
    let codec = PROTOCOL_TAB[buildKey(module, cmd)];
    if (!codec) {
        console.warn(`没找到对应的编码器, module:${module}, cmd:${cmd}`);
        return;
    }
    return codec.encode(params).finish();
}

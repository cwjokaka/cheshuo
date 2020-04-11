package org.lx.cs.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T strToObj(String jsonStr, Class<T> clazz) throws IOException {
        return bytesToObj(jsonStr.getBytes(), clazz);
    }

    public static <T> T bytesToObj(byte[] jsonBytes, Class<T> clazz) throws IOException {
        return mapper.readValue(jsonBytes, clazz);
    }

    public static Map<String, Object> bytesToMap(byte[] jsonBytes) throws IOException {
        return mapper.readValue(jsonBytes, new TypeReference<Map<String, Object>>(){});
    }

    public static Map<String, Object> strToMap(String jsonStr) throws IOException {
        return bytesToMap(jsonStr.getBytes());
    }

    public static JsonNode bytesToJsonNode(byte[] jsonBytes) throws IOException {
        return mapper.readValue(jsonBytes, JsonNode.class);
    }

    public static JsonNode strToJsonNode(String jsonStr) throws IOException {
        return bytesToJsonNode(jsonStr.getBytes());
    }

    public static String toJsonStr(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static <T> T nodeToObj(JsonNode jsonNode, Class<T> clazz) throws JsonProcessingException {
        return mapper.treeToValue(jsonNode, clazz);
    }

}

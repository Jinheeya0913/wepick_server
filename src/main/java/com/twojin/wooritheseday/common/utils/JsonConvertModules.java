package com.twojin.wooritheseday.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Map;

public class JsonConvertModules {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static String dtoJsonString;
    private static JSONObject resultObj = new JSONObject();

    private static JSONParser jsonParser = new JSONParser();

    static {
        // null 값을 json 에 추가하지 않도록 설정
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }


    public static JSONObject dtoToJsonObj(Object obj)  {
        try {
            dtoJsonString = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            resultObj = (JSONObject) jsonParser.parse(dtoJsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return resultObj;
    }
    public static JSONArray listToJsonArray(List<?> list) {
        JSONArray jsonArray = new JSONArray();
        for (Object obj : list) {
            jsonArray.add(dtoToJsonObj(obj));
        }
        return jsonArray;
    }

    public static <T> T jsonStrToDto(String jsonString, Class<T> dtoClass) {
        try {
            JsonNode originalNode = objectMapper.readTree(jsonString);
            ObjectNode filteredNode = objectMapper.createObjectNode();

            // DTO의 필드를 동적으로 가져와서 JSON 데이터에서 해당 필드만 추출
            for (var field : dtoClass.getDeclaredFields()) {
                String fieldName = field.getName();
                if (originalNode.has(fieldName)) {
                    filteredNode .put(fieldName, originalNode.path(fieldName));
                }
            }

            return objectMapper.treeToValue(filteredNode, dtoClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

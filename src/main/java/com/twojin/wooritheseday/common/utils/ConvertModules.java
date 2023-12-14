package com.twojin.wooritheseday.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

public class ConvertModules {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static String dtoJsonString;
    private static JSONObject resultObj = new JSONObject();

    private static JSONParser jsonParser = new JSONParser();



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

}

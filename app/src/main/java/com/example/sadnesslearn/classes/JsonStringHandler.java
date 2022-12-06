package com.example.sadnesslearn.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class JsonStringHandler {
    public static String serializeFromMap(Map<String, String> input){
        return new Gson().toJson(input, new TypeToken<HashMap<String, String>>() {}.getType());
    }
    public static Map<String, String> deserializeToMap(String output){
        return new Gson().fromJson(output, new TypeToken<HashMap<String, String>>() {}.getType());
    }
}

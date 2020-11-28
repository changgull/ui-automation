package com.stonecress.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.reporters.Files;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

public class Base {
    private static Properties globalProperties; // this should exist as a singleton

    protected static Properties getProperties() {
        return globalProperties;
    }

    protected void setProperties(Properties prop) {
        globalProperties = prop;
    }

    protected static String getPropStr(String key) {
        return (String) getProperties().get(key);
    }

    protected Integer getPropInt(String key) {
        return new Integer(getPropStr(key));
    }

    protected Boolean getPropBoolean(String key) {
        return new Boolean(getPropStr(key));
    }

    protected void loadGlobalProperties(String envFile) {
        setProperties(loadProperties(getProperties(), envFile));
    }

    protected Properties loadProperties(Properties properties, String propFile) {
        try {
            getLogger().info("loading properties: " + propFile);
            InputStream stream = getClass().getClassLoader().getResourceAsStream(propFile);
            if (properties == null) properties = new Properties();
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }

    public String randomString() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    public JSONObject getJsonById(JSONArray jsonArray, String id) {
        return getJsonByField(jsonArray, "id", id);
    }

    public JSONObject getJsonById(JSONArray jsonArray, Integer id) {
        JSONObject jsonObject;
        Iterator<Object> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            jsonObject = (JSONObject) iterator.next();
            if (jsonObject.getInt("id") == id) return jsonObject;
        }
        return null;
    }

    public JSONObject getJsonByField(JSONArray jsonArray, String key, String value) {
        JSONObject jsonObject;
        Iterator<Object> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            jsonObject = (JSONObject) iterator.next();
            if (jsonObject.getString(key).equals(value)) return jsonObject;
        }
        return null;
    }

    public JSONObject loadJSONObject(String resourceFilePath) {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(resourceFilePath);
            String jsonString = Files.streamToString(stream);
            return new JSONObject(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray loadJSONArray(String resourceFilePath) {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(resourceFilePath);
            String jsonString = Files.streamToString(stream);
            return new JSONArray(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

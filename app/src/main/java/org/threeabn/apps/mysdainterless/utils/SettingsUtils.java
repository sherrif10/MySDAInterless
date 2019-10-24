package org.threeabn.apps.mysdainterless.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.threeabn.apps.mysdainterless.settings.Settings;
import org.threeabn.apps.mysdainterless.settings.Status;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class SettingsUtils {
    private static ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return mapper;
    }
    public static String toJSONString(Settings settings) throws JsonProcessingException {
        return mapper().writerWithDefaultPrettyPrinter().writeValueAsString(settings);
    }

    public static String toStatusJSONString(Status status) throws JsonProcessingException {
        return mapper().writerWithDefaultPrettyPrinter().writeValueAsString(status);
    }

    public static Settings fromJSONString(String jsonString) throws JsonParseException, JsonMappingException, IOException {
        return mapper().readValue(jsonString, Settings.class);
    }

    public static Status fromStatusJSONString(String jsonString) throws JsonParseException, JsonMappingException, IOException {
        return mapper().readValue(jsonString, Status.class);
    }
}

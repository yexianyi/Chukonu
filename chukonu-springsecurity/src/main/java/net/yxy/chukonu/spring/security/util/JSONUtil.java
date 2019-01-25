package net.yxy.chukonu.spring.security.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JSONUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    private JSONUtil() {

    }

    public static String toJSONString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    public static String toPrettyJSONString(Object object) {
        try {
            Object jsonObj = mapper.writeValueAsString(object);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);
        } catch (JsonParseException e) {
            log.error(e.getMessage());
        } catch (JsonMappingException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    public static String toPrettyJSONString(String json) {
        try {
            Object jsonObj = mapper.readValue(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);
        } catch (JsonParseException e) {
            log.error(e.getMessage());
        } catch (JsonMappingException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    public static <T> T parseObject(String json, Class<T> cls) {
        try {
            return mapper.readValue(json, cls);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    public static <T> List<T> parseObjects(String json, Class<T> cls) {
        try {
            TypeFactory typeFactory = mapper.getTypeFactory();
            return mapper.readValue(json, typeFactory.constructCollectionType(List.class, cls));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

}

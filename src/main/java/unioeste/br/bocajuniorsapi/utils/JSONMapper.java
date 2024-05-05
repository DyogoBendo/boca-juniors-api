package unioeste.br.bocajuniorsapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONMapper {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static String map(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}

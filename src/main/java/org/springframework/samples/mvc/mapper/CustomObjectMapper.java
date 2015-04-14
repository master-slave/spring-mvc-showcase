package org.springframework.samples.mvc.mapper;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.samples.mvc.mapping.YourDateDeserializer;
import org.springframework.samples.mvc.mapping.YourDateSerializer;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        SimpleModule module = new SimpleModule("JsonDateModule", new Version(2, 0, 0, null, null, null));
        module.addSerializer(Date.class, new YourDateSerializer());
        module.addDeserializer(Date.class, new YourDateDeserializer());
        registerModule(module);
  }
}
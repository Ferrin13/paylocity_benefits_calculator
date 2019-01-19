package com.paylocity.benefitscalculator.utility;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class RepositorySerializer extends JsonSerializer<Repository> {

    @Override
    public void serialize(Repository repository, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        jsonGenerator.writeStartArray();
        //The Json generator operates on type Object, so the loss of genericism and common super type here is fine.
        repository.getAll().forEach(e -> {
            try {
                mapper.writeValue(jsonGenerator, e);
            } catch (Exception le) {
                throw new RuntimeException(le.getCause());
            }}
        );
        jsonGenerator.writeEndArray();
    }
}

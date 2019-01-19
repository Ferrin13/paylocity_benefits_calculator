package com.paylocity.benefitscalculator.utility;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class RepositoryDeserializer extends JsonDeserializer<Repository> {
    /*
    We do not support sending a repository from the front end, however the front end by default would like to send one
    when it exists on the types, so this allows it to do that, but just ignores whatever it sends.
     */
    @Override
    public Repository deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return new Repository();
    }
}

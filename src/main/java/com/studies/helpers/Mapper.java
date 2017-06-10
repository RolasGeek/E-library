package com.studies.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studies.entity.Book;

public class Mapper {
    public static Mapper singleton;
    private ObjectMapper mapper;

    public static Mapper getInstance(){
        if (singleton == null){
            singleton = new Mapper();
            singleton.setMapper(new ObjectMapper());
        }
        return singleton;
    }

    public String entityToJSON(Object obj){
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}

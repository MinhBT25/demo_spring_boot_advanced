package com.example.demo.util;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.UUID;

public class StringToUuidConverter implements Converter<String,UUID> {

    @Override
    public UUID convert(MappingContext<String, UUID> context) {
        String source = context.getSource();
        if (source == null) {
            return null;
        }
        return UUID.fromString(source);
    }
}

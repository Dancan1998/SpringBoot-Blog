package com.springboot.blogbuiltonspringboot.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class EnumRoleConverter implements AttributeConverter<EnumRole, String> {
    @Override
    public String convertToDatabaseColumn(EnumRole enumRole) {
        if(enumRole == null){
            return null;
        }
        return enumRole.getAppRoles();
    }

    @Override
    public EnumRole convertToEntityAttribute(String eRole) {
        if (eRole == null) {
            return null;
        }
        return Stream.of(EnumRole.values())
                .filter(role -> role.getAppRoles().equals(eRole))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

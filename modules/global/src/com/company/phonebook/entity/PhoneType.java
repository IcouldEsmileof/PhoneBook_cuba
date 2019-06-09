package com.company.phonebook.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum PhoneType implements EnumClass<String> {

    MOBILE("10"),
    HOME("20"),
    WORK("30"),
    OTHER("40");
    private String id;

    PhoneType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PhoneType fromId(String id) {
        for (PhoneType at : PhoneType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
package com.company.phonebook.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "PHONEBOOK_PERSON")
@Entity(name = "phonebook_Person")
public class Person extends StandardEntity {
    private static final long serialVersionUID = 8351506993050245057L;

    @NotNull
    @Lob
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull(message = "Person must have phone number")
    @Composition
    @OneToMany(mappedBy = "owner")
    protected List<PhoneNumber> phoneNumbers;

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
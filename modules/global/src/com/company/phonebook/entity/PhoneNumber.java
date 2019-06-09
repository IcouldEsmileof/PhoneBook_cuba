package com.company.phonebook.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s|owner,type")
@Table(name = "PHONEBOOK_PHONE_NUMBER")
@Entity(name = "phonebook_PhoneNumber")
public class PhoneNumber extends StandardEntity {
    private static final long serialVersionUID = 8567949229245925835L;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID")
    protected Person owner;

    @Length(message = "Incorrect number", min = 10, max = 14)
    @NotNull
    @Column(name = "NUMBER_", nullable = false, unique = true, length = 13)
    protected String number;

    @NotNull
    @Column(name = "TYPE_", nullable = false)
    protected String type;

    public PhoneType getType() {
        return type == null ? null : PhoneType.fromId(type);
    }

    public void setType(PhoneType type) {
        this.type = type == null ? null : type.getId();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
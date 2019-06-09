package com.company.phonebook.web.screens.person;

import com.haulmont.cuba.gui.screen.*;
import com.company.phonebook.entity.Person;


/**
 * Created by Aykut Ismailov on 8.6.2019 г.
 */
@UiController("phonebook_Person.edit")
@UiDescriptor("person-edit.xml")
@EditedEntityContainer("personDc")
@LoadDataBeforeShow
public class PersonEdit extends StandardEditor<Person> {
}
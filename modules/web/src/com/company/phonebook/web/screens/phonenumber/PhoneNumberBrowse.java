package com.company.phonebook.web.screens.phonenumber;

import com.haulmont.cuba.gui.screen.*;
import com.company.phonebook.entity.PhoneNumber;


/**
 * Created by Aykut Ismailov on 8.6.2019 г.
 */
@UiController("phonebook_PhoneNumber.browse")
@UiDescriptor("phone-number-browse.xml")
@LookupComponent("phoneNumbersTable")
@LoadDataBeforeShow
public class PhoneNumberBrowse extends StandardLookup<PhoneNumber> {
}
package com.company.phonebook.web.screens.person;

import com.company.phonebook.entity.Calls;
import com.company.phonebook.entity.Person;
import com.company.phonebook.entity.PhoneNumber;
import com.company.phonebook.entity.PhoneType;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Created by Aykut Ismailov on 8.6.2019 г.
 */
@UiController("phonebook_Person.browse")
@UiDescriptor("person-browse.xml")
@LookupComponent("personsTable")
@LoadDataBeforeShow
public class PersonBrowse extends StandardLookup<Person> {

    @Inject
    private GroupTable<PhoneNumber> numberTable;
    @Inject
    private CollectionContainer<PhoneNumber> numberDc;
    @Inject
    private Button callBtn;

    private PhoneNumber phoneNumber = null;
    @Inject
    private Metadata metadata;
    @Inject
    private DataManager dataManager;
    @Inject
    private FileUploadField uploadField;
    @Inject
    private Notifications notifications;
    @Inject
    private GroupTable<Person> personsTable;


    @Subscribe("personsTable")
    private void onPersonsTableSelection(Table.SelectionEvent<Person> event) {
        List<Person> people = new ArrayList<>(event.getSelected());
        if (people.size() == 1) {
            numberTable.setVisible(true);
            numberDc.setItems(people.get(0).getPhoneNumbers());
        } else {
            numberTable.setVisible(false);
        }
    }

    @Subscribe("uploadField")
    private void onUploadFieldFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        try {
            if (uploadField.getFileContent() == null) {
                throw new NullPointerException();
            }
            Scanner in = new Scanner(uploadField.getFileContent());
            for (; in.hasNextLine(); ) {
                String[] s = in.nextLine().split(",");
                if (s.length == 3) {
                    PhoneNumber number = metadata.create(PhoneNumber.class);
                    Person owner = metadata.create(Person.class);
                    owner.setName(s[0]);
                    number.setOwner(owner);
                    if (s[1].length() == 13) {
                        if (s[1].matches("\\+359(87|88|89|98)[2-9][0-9]{6}")) {
                            number.setNumber(s[1]);
                        } else {
                            notifications.create(Notifications.NotificationType.WARNING).withCaption(s[1] + " is not valid phone number.").show();
                        }
                    } else if (s[1].length() == 10) {
                        if (s[1].matches("0(87|88|89|98)[2-9][0-9]{6}")) {
                            number.setNumber("+359" + s[1].substring(1));
                        } else {
                            notifications.create(Notifications.NotificationType.WARNING).withCaption(s[1] + " is not valid phone number.").show();
                        }
                    } else if (s[1].length() == 14) {
                        if (s[1].matches("00359(87|88|89|98)[2-9][0-9]{6}")) {
                            number.setNumber("+" + s[1].substring(2));
                        } else {
                            notifications.create(Notifications.NotificationType.WARNING).withCaption(s[1] + " is not valid phone number.").show();
                        }
                    } else {
                        notifications.create(Notifications.NotificationType.WARNING).withCaption(s[1] + " is not valid phone number.").show();
                    }
                    switch (s[2].toLowerCase()) {
                        case "mobile":
                            number.setType(PhoneType.MOBILE);
                            break;
                        case "work":
                            number.setType(PhoneType.WORK);
                            break;
                        case "home":
                            number.setType(PhoneType.HOME);
                            break;
                        default:
                            number.setType(PhoneType.OTHER);
                    }
                    dataManager.commit(owner);
                    dataManager.commit(number);
                    personsTable.refresh();
                } else {
                    notifications.create(Notifications.NotificationType.WARNING).withCaption(String.join(",", s) + " is not valid account.").show();
                }
            }
        } catch (Exception e) {
            notifications.create(Notifications.NotificationType.ERROR).withCaption(e.getMessage()).show();
        }
    }

    @Subscribe("numberTable")
    private void onNumberTableSelection(Table.SelectionEvent<PhoneNumber> event) {
        List<PhoneNumber> numbers = new ArrayList<>(event.getSelected());
        if (numbers.size() == 1) {
            callBtn.setEnabled(true);
            phoneNumber = numbers.get(0);
        } else {
            callBtn.setEnabled(false);
            phoneNumber = null;
        }
    }

    @Subscribe("callBtn")
    private void onCallBtnClick(Button.ClickEvent event) {
        if (phoneNumber != null) {

            Calls c = metadata.create(Calls.class);
            c.setMadeOn(LocalDateTime.now());
            c.setPhoneNumber(phoneNumber);
            dataManager.commit(c);

        } else {
            callBtn.setVisible(false);
        }
    }


}
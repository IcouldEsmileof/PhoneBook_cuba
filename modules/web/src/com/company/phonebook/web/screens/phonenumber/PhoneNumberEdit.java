package com.company.phonebook.web.screens.phonenumber;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.screen.*;
import com.company.phonebook.entity.PhoneNumber;
import com.haulmont.cuba.gui.util.OperationResult;

import javax.inject.Inject;


/**
 * Created by Aykut Ismailov on 8.6.2019 г.
 */
@UiController("phonebook_PhoneNumber.edit")
@UiDescriptor("phone-number-edit.xml")
@EditedEntityContainer("phoneNumberDc")
@LoadDataBeforeShow
public class PhoneNumberEdit extends StandardEditor<PhoneNumber> {
    @Inject
    private Notifications notifications;

    @Subscribe
    private void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if(getEditedEntity().getNumber().length()==13) {
            if (getEditedEntity().getNumber().matches("\\+359(87|88|89|98)[2-9][0-9]{6}")) {
                event.resume();
            }else{
                notifications.create(Notifications.NotificationType.ERROR).withCaption("Incorrect phone number").show();
                event.preventCommit(OperationResult.fail());
            }
        }else if(getEditedEntity().getNumber().length()==10){
            if (getEditedEntity().getNumber().matches("0(87|88|89|98)[2-9][0-9]{6}")) {
                getEditedEntity().setNumber("+359"+getEditedEntity().getNumber().substring(1));
                event.resume();
            }else{
                notifications.create(Notifications.NotificationType.ERROR).withCaption("Incorrect phone number").show();
                event.preventCommit(OperationResult.fail());
            }
        }else if(getEditedEntity().getNumber().length()==14){
            if (getEditedEntity().getNumber().matches("00359(87|88|89|98)[2-9][0-9]{6}")) {
                getEditedEntity().setNumber("+"+getEditedEntity().getNumber().substring(2));
                event.resume();
            }else{
                notifications.create(Notifications.NotificationType.ERROR).withCaption("Incorrect phone number").show();
                event.preventCommit(OperationResult.fail());
            }
        }else{
            notifications.create(Notifications.NotificationType.ERROR).withCaption("Incorrect phone number").show();
            event.preventCommit(OperationResult.fail());
        }
    }

}
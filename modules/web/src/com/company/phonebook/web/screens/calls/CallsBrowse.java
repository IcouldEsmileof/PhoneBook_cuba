package com.company.phonebook.web.screens.calls;

import com.company.phonebook.entity.Calls;
import com.company.phonebook.entity.PhoneNumber;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import jdk.nashorn.internal.codegen.CompilerConstants;

import javax.inject.Inject;
import java.util.*;


/**
 * Created by Aykut Ismailov on 8.6.2019 г.
 */
@UiController("phonebook_Calls.browse")
@UiDescriptor("calls-browse.xml")
@LookupComponent("callsesTable")
@LoadDataBeforeShow
public class CallsBrowse extends StandardLookup<Calls> {
    @Inject
    private CollectionLoader<Calls> callsesDl;
    @Inject
    private GroupTable<Calls> callsesTable;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private CollectionContainer<Calls> callsesDc;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        callsesTable.addGeneratedColumn("Owner",entity->{
            Label l=uiComponents.create(Label.NAME);
            l.setValue(entity.getPhoneNumber().getOwner().getName());
            HBoxLayout hBoxLayout=uiComponents.create(HBoxLayout.NAME);
            hBoxLayout.add(l);
            return hBoxLayout;
        });
        callsesTable.addGeneratedColumn("Phone number",entity->{
            Label l=uiComponents.create(Label.NAME);
            l.setValue(entity.getPhoneNumber().getNumber());
            HBoxLayout hBoxLayout=uiComponents.create(HBoxLayout.NAME);
            hBoxLayout.add(l);
            return hBoxLayout;
        });
        callsesTable.addGeneratedColumn("Type",entity->{
            Label l=uiComponents.create(Label.NAME);
            l.setValue(entity.getPhoneNumber().getType());
            HBoxLayout hBoxLayout=uiComponents.create(HBoxLayout.NAME);
            hBoxLayout.add(l);
            return hBoxLayout;
        });

    }


    @Subscribe("topBtn")
    private void onTopBtnClick(Button.ClickEvent event) {
        List<Calls> callsList=new ArrayList<>(callsesDc.getItems());
        Map<PhoneNumber,Integer> m=new HashMap<>();
        for(Calls c:callsList){
            if(m.containsKey(c.getPhoneNumber())){
                int i=m.get(c.getPhoneNumber());
                m.put(c.getPhoneNumber(),i+1);
            }else{
                m.put(c.getPhoneNumber(),1);
            }
        }
        callsList.sort((o1, o2) -> m.get(o2.getPhoneNumber()).compareTo(m.get(o1.getPhoneNumber())));
        Set<PhoneNumber> phoneList=new HashSet<>();
        for(Calls c:callsList){
                phoneList.add(c.getPhoneNumber());
                if(phoneList.size()==5){
                    break;
                }
        }
        Iterator<Calls> iterator=callsList.iterator();
        for(;iterator.hasNext();){
            Calls c=iterator.next();
            if(phoneList.contains(c.getPhoneNumber())){
                phoneList.remove(c.getPhoneNumber());
            }else{
                iterator.remove();
            }
        }
        callsesDc.setItems(callsList.subList(0,callsList.size()<5?callsList.size():5));
    }


}
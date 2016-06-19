package com.example;

/**
 * Created by furkan on 5/23/2016.
 */

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.validation.constraints.Null;
import java.util.Date;

@SpringUI
@PreserveOnRefresh
//@Theme("mytheme")
public class Denek extends UI {
    int n = 1;

    public void init (VaadinRequest request){

        //setTheme("mytheme");
        setTheme("valo");

        VerticalLayout container = new VerticalLayout(); // layout to make positioning adjustments
        container.setMargin(true);
        VerticalLayout alayout = new VerticalLayout(); // main layout
        alayout.setMargin(true);
        setContent(container);

        Panel pane = new Panel();
        pane.setSizeUndefined();
        pane.addStyleName(ValoTheme.PANEL_WELL);


        HorizontalLayout h1 = new HorizontalLayout(); // layout to contain first two formed layouts
        h1.setWidth("115%");

        Label lbl = new Label("<b><center>Staj Gunlugu</center></b>", ContentMode.HTML); // title

        // first formed layout which contains baslik and ilgili
        FormLayout form1 = new FormLayout();

        TextField text = new TextField("Baslik");
        //text.setRequired(true);
        //text.addValidator(new NullValidator("Must be given", false));
        form1.addComponent(text);

        ComboBox combobox = new ComboBox("Ilgili");
        combobox.addItems("Baris", "Samet", "Kutay");
        form1.addComponent(combobox);
        // first formed layout which contains baslik and ilgili


        // second form layout which contains tarih and konu
        FormLayout form2 = new FormLayout();
        DateField date = new DateField("Tarih");
        form2.addComponent(date);

        TextField textkonu = new TextField("Konu");
        form2.addComponent(textkonu);
        // second form layout whicj contains tarih and konu


        // third formed layout contains aciklama
        FormLayout form3 = new FormLayout();
        TextArea aciklama = new TextArea("Aciklama");
        aciklama.setWidth("100%");
        form3.addComponent(aciklama);
        // third formed layout contains aciklama

        // create table
        Table table = new Table();
        table.setSelectable(true);
        table.setImmediate(true);

        table.addContainerProperty("Baslik", String.class, null);
        table.addContainerProperty("Ilgili", String.class, null);
        table.addContainerProperty("Gun", Date.class, null);
        table.addContainerProperty("Konu", String.class, null);
        table.addContainerProperty("Aciklama", String.class, null);
        table.setWidth("100%");
        //table.setEditable(true);

        /*table.addListener(new ItemClickEvent.ItemClickListener() {
            public void itemClick(ItemClickEvent event) {
                if (event.getButton() == MouseEvents.ClickEvent.BUTTON_RIGHT) {
                    for(int i = 0; i<=n; i++ ) {
                        if (table.isSelected(i))
                            table.removeItem(i);
                    }
                }
            }
        }); */

        Action actionDelete = new Action("Delete");

        table.addActionHandler(new Action.Handler() {
            @Override
            public void handleAction(final Action action, final Object sender,
                                     final Object target) {
                if (action == actionDelete) {
                    table.removeItem(target);
                }
            }

            @Override
            public Action[] getActions(final Object target, final Object sender) {

                if (target == null) {
                    return new Action[] { actionDelete };
                }
                return new Action[] { actionDelete };
            }
        });
        // create table



        // horizontal layout which contains kaydet and temizle buttons
        HorizontalLayout h2 = new HorizontalLayout();

        h2.setWidth("100%");
        Button button = new Button("clear");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {

                for(int i = 0; i<=n; i++ ) {
                    if (table.isSelected(i))
                        table.removeItem(i);
                }

                //table.removeItem(1);
                text.setValue("");
                aciklama.setValue("");
                textkonu.setValue("");
                combobox.setValue(null);
                date.setValue(null);
            }
        });

        h2.addComponent(button);

        Button button2 = new Button("kaydet");
        h2.addComponent(button2);
        h2.setComponentAlignment(button2, Alignment.MIDDLE_RIGHT);

        button2.addClickListener(new Button.ClickListener() {
            //int n = 1;
            public void buttonClick(Button.ClickEvent event) {
                if(text.getValue() == "" && combobox.getValue() == null && date.getValue() == null && textkonu.getValue() == "" && aciklama.getValue() == "" ) {
                    // don't add
                }
                else {


                    table.addItem(new Object[]{text.getValue(), combobox.getValue(), date.getValue(), textkonu.getValue(), aciklama.getValue()}, n);
                }

                n++;
                text.setValue("");
                aciklama.setValue("");
                textkonu.setValue("");
                combobox.setValue(null);
                date.setValue(null);
            }
        });
        // horizontal layout which contains kaydet and temizle buttons


        // placing layouts
        h1.addComponent(form1);
        h1.addComponent(form2);
        h1.setComponentAlignment(form2,Alignment.TOP_RIGHT);

        alayout.addComponent(lbl);
        alayout.addComponent(h1);
        alayout.addComponent(form3);
        alayout.addComponent(h2);
        alayout.addComponent(table);
        alayout.setWidth("600px");

        container.addComponent(alayout);
        container.addComponent(pane);
        container.setComponentAlignment(pane, Alignment.TOP_CENTER);

        pane.setContent(alayout);

    }
}

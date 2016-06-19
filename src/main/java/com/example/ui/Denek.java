package com.example.ui;

/**
 * Created by furkan on 5/23/2016.
 */

import com.example.Registry;
import com.example.model.TableModel;
import com.example.service.TableService;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
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
    static int n = 1;
    int id =1;
    Table table;
    private TableService tableService = Registry.getTableService();

    public void init (VaadinRequest request){

        //setTheme("mytheme");
        setTheme("valo");
        System.out.println();
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
        table = new Table();
        table.setSelectable(true);
        table.setImmediate(true);

        table.addContainerProperty("objId", String.class, null);
        table.addContainerProperty("Baslik", String.class, null);
        table.addContainerProperty("Ilgili", String.class, null);
        table.addContainerProperty("Gun", Date.class, null);
        table.addContainerProperty("Konu", String.class, null);
        table.addContainerProperty("Aciklama", String.class, null);
        table.setWidth("100%");

        table.setVisibleColumns(new Object[]{"Baslik","Ilgili","Gun","Konu","Aciklama"});


        Action actionDelete = new Action("Delete");
        Action actionDeleteByVariables = new Action("Delete By Variables");
        table.addActionHandler(new Action.Handler() {
            @Override
            public void handleAction(final Action action, final Object sender,
                                     final Object target) {

                if (action == actionDelete) {
                    if(table.getValue()!=null){
                        Item item = table.getItem(table.getValue());
                        tableService.deleteTableModel(item.getItemProperty("objId").getValue().toString());
                        fillTable();
                    }
                }
                else if (action == actionDeleteByVariables) {
                    if(table.getValue()!=null){
                        Item item = table.getItem(table.getValue());
                        tableService.deleteTableModelFromVariables(item.getItemProperty("Baslik").getValue()+"",item.getItemProperty("Ilgili").getValue()+"");
                        fillTable();
                    }
                }
            }

            @Override
            public Action[] getActions(final Object target, final Object sender) {

                if (target == null) {
                    return new Action[] { actionDelete, actionDeleteByVariables };
                }
                return new Action[] { actionDelete , actionDeleteByVariables};
            }
        });
        // create table


        // horizontal layout which contains kaydet and temizle buttons
        HorizontalLayout h2 = new HorizontalLayout();

        //Registry.getTableService().removeAll("b");

        h2.setWidth("100%");
        Button button = new Button("clear");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                text.setValue("");
                aciklama.setValue("");
                textkonu.setValue("");
                combobox.setValue(null);
                date.setValue(null);
            }
        });

        h2.addComponent(button);

        fillTable();

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
                    TableModel tableModel = Registry.getTableService().saveTable(text.getValue(), String.valueOf(combobox.getValue()), date.getValue(), textkonu.getValue(), aciklama.getValue());

                    Object id = table.addItem();
                    //table.getItem(id).getItemProperty("Baslik").setValue(text.getValue()); does same thing with below code
                    table.getContainerProperty(id,"Baslik").setValue(text.getValue());
                    table.getContainerProperty(id,"Ilgili").setValue(combobox.getValue());
                    table.getContainerProperty(id,"Gun").setValue(date.getValue());
                    table.getContainerProperty(id,"Konu").setValue(textkonu.getValue());
                    table.getContainerProperty(id,"Aciklama").setValue(aciklama.getValue());
                    table.getContainerProperty(id,"objId").setValue(tableModel.getId());
                    try {
                        fillTable();
                    }
                    catch (Exception exception){
                        System.out.println(exception.toString());
                    }
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

    private void fillTable(){
        table.removeAllItems();
        for(TableModel tableModel : tableService.findAllTables()) {
            Object id = table.addItem();
            table.getContainerProperty(id,"Baslik").setValue(tableModel.getBaslik());
            table.getContainerProperty(id,"Ilgili").setValue(tableModel.getIlgili());
            table.getContainerProperty(id,"Gun").setValue(tableModel.getGun());
            table.getContainerProperty(id,"Konu").setValue(tableModel.getKonu());
            table.getContainerProperty(id,"Aciklama").setValue(tableModel.getAciklama());
            table.getContainerProperty(id,"objId").setValue(tableModel.getId());

        }
    }
}

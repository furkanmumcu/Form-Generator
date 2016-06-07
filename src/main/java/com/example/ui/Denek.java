package com.example.ui;

/**
 * Created by furkan on 5/23/2016.
 */

import com.example.Registry;
import com.example.model.TableModel;
import com.example.service.TableService;
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
    static int n = 1;
    int id =1;
    private TableService tableService = Registry.getTableService();

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
                    //fillTable(table);
                    table.removeItem(target);
                    //System.out.println(target);
                    //int nmr = (int) target;
                    //tableService.removeTable(tableService.findAllTables().get(nmr-1));
                    //fillTable(table);
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

        //Registry.getTableService().removeAll("b");

        h2.setWidth("100%");
        Button button = new Button("clear");
        button.addClickListener(new Button.ClickListener() {
            //@Override
            public void buttonClick(Button.ClickEvent event) {
                //tableService.removeTable(tableService.findAllTables().get(1));
/*                for(int i = 0; i<=n; i++ ) {

                    if (table.isSelected(i)) {
                        System.out.println(table.getValue());
                        //nt nmr = (Integer) table.getValue();
                        //tableService.removeTable(tableService.findAllTables().get(nmr-2));
                        table.removeItem(i);

                    }
                }*/
                //fillTable(table);

                text.setValue("");
                aciklama.setValue("");
                textkonu.setValue("");
                combobox.setValue(null);
                date.setValue(null);
            }
        });

        h2.addComponent(button);

        fillTable(table);

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

                    table.addItem(new Object[]{text.getValue(), combobox.getValue(), date.getValue(), textkonu.getValue(), aciklama.getValue()}, n+id);
                    Registry.getTableService().saveTable(text.getValue(), String.valueOf(combobox.getValue()), date.getValue(), textkonu.getValue(), aciklama.getValue());
                    try {
                        fillTable(table);
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

        //Registry.getTableService().findAllTables().toString();

    }

    private void fillTable(Table table){
        table.removeAllItems();
        for(TableModel tableModel : tableService.findAllTables()) {
            table.addItem(new Object[]{tableModel.getBaslik(),tableModel.getIlgili(),tableModel.getGun(),tableModel.getKonu(),tableModel.getAciklama()},id);
            id++;
        }
    }
}

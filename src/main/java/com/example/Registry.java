package com.example;

import com.example.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by furka_000 on 6/3/2016.
 */
@Component
public class Registry {
    private static Registry instance;

    private Registry(){}
    @Autowired
    private TableService tableService;

    @PostConstruct
    private void init() {instance = this;}

    public static Registry get() {return instance;}

    public static TableService getTableService (){return instance.tableService;}


}

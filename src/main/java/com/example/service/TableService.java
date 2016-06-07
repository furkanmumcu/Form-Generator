package com.example.service;


import com.example.model.TableModel;
import com.example.repository.TableModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by furka_000 on 6/3/2016.
 */

@Service
public class TableService {

    @Autowired
    private TableModelRepository tableModelRepository;

    public List<TableModel> findAllTables(){
        return (List <TableModel>) (tableModelRepository.findAll());
    }




    public TableModel saveTable ( String Baslik, String Ilgili, Date Gun, String Konu, String Aciklama){
        TableModel tableModel = new TableModel();
        //tableModel.setId(id);
        tableModel.setBaslik(Baslik);
        tableModel.setIlgili(Ilgili);
        tableModel.setGun(Gun);
        tableModel.setKonu(Konu);
        tableModel.setAciklama(Aciklama);
        return tableModelRepository.save(tableModel);
    }

    public void removeTable(TableModel t){
        tableModelRepository.delete(t);
    }

    public  void removeAll(){
        tableModelRepository.delete(findAllTables());
    }

}

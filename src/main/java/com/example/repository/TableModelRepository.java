package com.example.repository;

import com.example.model.TableModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

/**
 * Created by furkan on 6/3/2016.
 */
public interface TableModelRepository extends CrudRepository<TableModel,String> {

}

package com.example.model;

import com.example.Registry;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by furka_000 on 6/3/2016.
 */

@Document
public class TableModel {

    @Id
    private String id;
    private String baslik;
    private String ilgili;
    private Date gun;
    private String konu;
    private String aciklama;
    //private  int id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getIlgili() {
        return ilgili;
    }

    public void setIlgili(String ilgili) {
        this.ilgili = ilgili;
    }

    public Date getGun() {
        return gun;
    }

    public void setGun(Date gun) {
        this.gun = gun;
    }

    public String getKonu() {
        return konu;
    }

    public void setKonu(String konu) {
        this.konu = konu;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableModel that = (TableModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (baslik != null ? !baslik.equals(that.baslik) : that.baslik != null) return false;
        if (ilgili != null ? !ilgili.equals(that.ilgili) : that.ilgili != null) return false;
        if (gun != null ? !gun.equals(that.gun) : that.gun != null) return false;
        if (konu != null ? !konu.equals(that.konu) : that.konu != null) return false;
        return aciklama != null ? aciklama.equals(that.aciklama) : that.aciklama == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (baslik != null ? baslik.hashCode() : 0);
        result = 31 * result + (ilgili != null ? ilgili.hashCode() : 0);
        result = 31 * result + (gun != null ? gun.hashCode() : 0);
        result = 31 * result + (konu != null ? konu.hashCode() : 0);
        result = 31 * result + (aciklama != null ? aciklama.hashCode() : 0);
        return result;
    }
}

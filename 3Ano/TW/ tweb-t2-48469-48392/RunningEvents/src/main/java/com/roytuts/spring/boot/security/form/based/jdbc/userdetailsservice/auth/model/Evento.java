/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model;

import java.sql.Date;

/**
 *
 * @author barbara
 */

public class Evento {
    
    private int eid;
    private String eventoName;
    private String descricao;
    private Date data;
    private Integer preco;
    
    public Evento(){
    }
    
    public Evento(String eventoName, String descricao, Date data,  Integer preco){
        this.eid=eid;
        this.eventoName = eventoName;
        this.descricao=descricao;
        this.data=data;
        this.preco=preco;
    }
    
    public Integer getEid(){
        return eid;
    }

    public void setEid(Integer eid){
        this.eid=eid;
    }
    public String getEventoName(){
        return eventoName;
    }

    public void setEventoName(String eventoName){
        this.eventoName=eventoName;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao=descricao;
    }

    public Date getData(){
        return data;
    }

     public void setData(Date date){
        this.data=date;
    }

    public Integer getPreco(){
        return preco;
    }

     public void setPreco(Integer preco){
        this.preco=preco;
    }



   
}

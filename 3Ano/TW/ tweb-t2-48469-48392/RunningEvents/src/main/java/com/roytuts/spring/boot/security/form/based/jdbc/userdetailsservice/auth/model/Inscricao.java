/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model;

/**
 *
 * @author barbara
 */
public class Inscricao {
    
    private String user_name;
    private String evento_name;
    private Integer dorsal;
    
    public Inscricao() {
    }

    public Inscricao(String user_name, String evento_name, Integer dorsal) {
        this.user_name = user_name;
        this.evento_name = evento_name;
        this.dorsal = dorsal;
    }
    
    public void setParticipantname(String user_name) {
        this.user_name = user_name;
    }

    public String getParticipantname(){
        return this.user_name;
    } 
    
    public void setEventoname(String evento_name) {
        this.evento_name = evento_name;
    }

    public String getEventoname(){
        return this.evento_name;
    } 
    
    public void setDorsal(Integer dorsal) {
        this.dorsal = dorsal;
    }

    public Integer getDorsal(){
        return this.dorsal;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Evento;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.rowmapper.EventoRowMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author barbara
 */
@Repository
public class EventoDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Evento getEventoByName(final String evento_name) {
        String sql = "SELECT evento_name, descricao, data, preco FROM evento WHERE evento_name = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{evento_name}, new EventoRowMapper());
    }

    public void saveEvento(final Evento e) {
        String sql = "INSERT INTO evento (evento_name, descricao, data, preco) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, e.getEventoName(), e.getDescricao(), e.getData(), e.getPreco());
        System.out.println("EventoDao - saved\n" + sql + "\n");
    }
    
     public List<Evento> getAllEventos() {
        String sql = "SELECT evento_name, descricao, data, preco FROM evento";
        return jdbcTemplate.query(sql, new EventoRowMapper());
    }

    public List<Evento> getEventosAtivos()  
    {  
        String sql = "SELECT evento_name, descricao, data, preco FROM evento WHERE data = CURRENT_DATE";
        return jdbcTemplate.query(sql, new EventoRowMapper());
    }

    public List<Evento> getEventosPassados()  
    {  
        String sql = "SELECT evento_name, descricao, data, preco FROM evento WHERE data < CURRENT_DATE";
        return jdbcTemplate.query(sql, new EventoRowMapper());
    }
    
    public List<Evento> getEventosFuturos()  
    {  
        String sql = "SELECT evento_name, descricao, data, preco FROM evento WHERE data > CURRENT_DATE";
        return jdbcTemplate.query(sql, new EventoRowMapper());
    }

    
}

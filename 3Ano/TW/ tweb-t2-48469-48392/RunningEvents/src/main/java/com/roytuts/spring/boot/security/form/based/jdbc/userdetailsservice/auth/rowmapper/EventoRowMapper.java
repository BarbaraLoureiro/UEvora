/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Evento;


/**
 *
 * @author barbara
 */
public class EventoRowMapper implements RowMapper<Evento> {
    @Override
    public Evento mapRow(ResultSet rs, int rowNum) throws SQLException {
        Evento evento = new Evento();
        evento.setEventoName(rs.getString("evento_name"));
        evento.setDescricao(rs.getString("descricao"));
        evento.setData(rs.getDate("data"));
        evento.setPreco(rs.getInt("preco"));
        return evento;
    }
}


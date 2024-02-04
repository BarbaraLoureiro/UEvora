/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Inscricao;

/**
 *
 * @author barbara
 */
public class InscricaoRowMapper implements RowMapper<Inscricao> {
    
    @Override
    public Inscricao mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Inscricao(rs.getString("user_name"), rs.getString("evento_name"), rs.getInt("dorsal"));
    }
}

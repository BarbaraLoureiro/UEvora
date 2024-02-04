/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Inscricao;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.rowmapper.InscricaoRowMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author barbara
 */

@Repository
public class InscricaoDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Inscricao getInscricao(final String user_name) {
        String sql = "SELECT user_name, evento_name, dorsal FROM inscricao WHERE user_name = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{user_name}, new InscricaoRowMapper());
    }
    
    public List<Inscricao> getInscricoesByAtleta(final String userName) {
        String sql = "SELECT user_name, evento_name, dorsal FROM inscricao WHERE user_name = ?";
        return jdbcTemplate.query(sql, new Object[]{userName}, new InscricaoRowMapper());
    }

    public void inscreverAtleta(String userName, String eventoName) {
        String sql = "INSERT INTO inscricao (user_name, evento_name) VALUES (?, ?)";
        jdbcTemplate.update(sql, userName, eventoName);
        System.out.println("InscricaoDao - saved\n" + sql + "\n");

    }

    public List<String> getAtletasList() {
        String sql = "SELECT user_name FROM inscricao WHERE evento_name = ?";
        return jdbcTemplate.queryForList(sql, String.class);
    }


}

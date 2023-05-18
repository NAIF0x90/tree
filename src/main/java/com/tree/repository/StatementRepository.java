package com.tree.repository;

import com.tree.entity.Statement;
import com.tree.entity.StatementRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatementRepository {

    Logger logger = LoggerFactory.getLogger(StatementRepository.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Statement> findByAccountId(Long accountId){

        logger.info("trying to get statements with account id {}", accountId);

        try {
            String sql = "SELECT * FROM statement WHERE account_id = ?";
            return jdbcTemplate.query(sql, new Object[]{accountId}, new StatementRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Statement> findAll(){

        try {
            String sql = "SELECT * FROM statement";
            return jdbcTemplate.query(sql, new StatementRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}

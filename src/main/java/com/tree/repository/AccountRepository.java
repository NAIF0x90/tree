package com.tree.repository;

import com.tree.entity.Account;
import com.tree.entity.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Account findByAccountId(Long accountId){

        try {
            String sql = "SELECT * FROM account WHERE ID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{accountId}, new AccountRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

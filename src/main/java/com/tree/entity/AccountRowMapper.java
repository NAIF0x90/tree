package com.tree.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {


    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

        Account account = new Account();
        account.setId(rs.getString("id"));
        account.setAccountType(rs.getString("account_type"));
        account.setAccountNumber(rs.getString("account_number"));

        return account;
    }
}

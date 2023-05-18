package com.tree.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatementRowMapper implements RowMapper<Statement> {
    @Override
    public Statement mapRow(ResultSet rs, int rowNum) throws SQLException {

        Statement statement = new Statement();
        statement.setId(rs.getString("id"));
        statement.setAccountId(rs.getString("account_id"));
        statement.setDateField(rs.getString("datefield"));
        statement.setAmount(rs.getString("amount"));

        return statement;
    }
}

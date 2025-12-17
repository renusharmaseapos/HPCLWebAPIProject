package com.seapos.webapi.dataaccess;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
public class SQLHelper {

    private static JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Execute a stored procedure that returns data
    public static Map<String, Object> getRecord(String procedureName,  Map<String, Object> inParams) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                //.withSchemaName(schema)
                .withProcedureName(procedureName);
        return jdbcCall.execute(inParams);
    }

    public static List<Map<String, Object>> getRecords(String procedureName, Map<String, Object> inParams) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
               // .withSchemaName(schema)
                .withProcedureName(procedureName);

        Map<String, Object> data = jdbcCall.execute(inParams);
        return (List<Map<String, Object>>) data.get("#result-set-1");
    }

    // Execute a stored procedure that returns a scalar value
    public static Map<String, Object> executeScaler(String procedureName, Map<String, Object> inParams) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
               // .withSchemaName(schema)
                .withProcedureName(procedureName);
        return jdbcCall.execute(inParams);
    }

    // Execute a stored procedure that performs an update/insert/delete
    public static Map<String, Object> executeNonQuery(String procedureName, Map<String, Object> inParams) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
        return jdbcCall.execute(inParams);
    }
}

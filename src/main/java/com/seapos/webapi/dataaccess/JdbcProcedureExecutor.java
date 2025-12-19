package com.seapos.webapi.dataaccess;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JdbcProcedureExecutor {

    private final JdbcTemplate jdbcTemplate;
    private final Map<String, SimpleJdbcCall> cache = new ConcurrentHashMap<>();

    public JdbcProcedureExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private SimpleJdbcCall getCall(String spName) {
        return cache.computeIfAbsent(
                spName,
                n -> new SimpleJdbcCall(jdbcTemplate).withProcedureName(n)
        );
    }

    public List<Map<String, Object>> executeForList(String spName,
                                                    Map<String, Object> params) {
        Map<String, Object> result = getCall(spName).execute(params);
        return (List<Map<String, Object>>) result.get("#result-set-1");
    }
}
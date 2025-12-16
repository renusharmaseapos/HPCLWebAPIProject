package com.seapos.webapi.dataaccess.user;
import com.seapos.webapi.exception.DalException;
import com.seapos.webapi.exception.ValidationException;
import com.seapos.webapi.models.request.AddUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger =
            LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String addOrUpdateUser(AddUserRequest r) {

        try {
            logger.info("Calling uspAddEntityUser | email={}", r.getEmail());

            return jdbcTemplate.queryForObject(
                    "CALL uspAddEntityUser(?,?,?,?,?,?,?,?,?,?,?)",
                    String.class,
                    r.getFirstName(),
                    r.getLastName(),
                    r.getEmail(),
                    r.getMobile(),
                    r.getEntityTypeId(),
                    r.getEntityId(),
                    r.getRoleId(),
                    r.getUserId(),
                    r.getClientIds(),
                    r.getUserStatus(),
                    r.getCreatedBy()
            );

        } catch (DataAccessException dae) {

            logger.error("DB_ERROR | uspAddEntityUser | email={} | msg={}",
                    r.getEmail(), dae.getMostSpecificCause().getMessage());

            String dbMessage = dae.getMostSpecificCause().getMessage();

            // DB VALIDATION → BUSINESS VALIDATION
            if (dbMessage.contains("Duplicate EmailId")) {
                throw new ValidationException("Email already exists");
            }

            if (dbMessage.contains("Please enter valid Merchant Id")) {
                throw new ValidationException("Please enter valid Merchant Id");
            }

            // REAL DB FAILURE
            throw new DalException("Database operation failed", dae);
        }
    }
}
package ru.pariy.tmsystem.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.pariy.tmsystem.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private final UserMapper userMapper = new UserMapper();
    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getByEmail(String email) {
        try {
            final String SQL_GET_USER = "SELECT * FROM users WHERE email=?";
            return jdbcTemplate.queryForObject(SQL_GET_USER, userMapper, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer checkUser(String email) {
        final String SQL_CHECK_USER = "SELECT COUNT(*) FROM users WHERE email=?";
       return jdbcTemplate.queryForObject(SQL_CHECK_USER, Integer.class, email);
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}

package com.example.demo.impl;


import com.example.demo.data.UserGender;
import com.example.demo.data.UserInfo;
import com.example.demo.data.UserToAuth;
import com.example.demo.data.UserToSave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public UserInfo getInfoByUsername(String username)
    {

        try {
            String sql = "SELECT username, name, age, gender, town, balance FROM users WHERE username=?";
            return jdbcTemplate.queryForObject(sql, this::mapUserToUserInfo, username);
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }

    }

    public UserToAuth findByUsername(String username)
    {

        try {
            String sql = "SELECT username, password FROM users WHERE username=?";
            return jdbcTemplate.queryForObject(sql, this::mapUserToAuthRow, username);
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }

    }

    public Double getBalanceByUsername(String username)
    {
        try {
            String sql = "SELECT balance FROM users WHERE username=?";
            return jdbcTemplate.queryForObject(sql, Double.class, username);
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    public String getNameByUsername(String username)
    {
        try {
            String sql = "SELECT name FROM users WHERE username=?";
            return jdbcTemplate.queryForObject(sql, String.class, username);
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    public Boolean existsByUsername(String username)
    {

        try {
            String sql = "SELECT username, password FROM users WHERE username=?";
           UserToAuth user= jdbcTemplate.queryForObject(sql,this::mapUserToAuthRow,username);
           return true;
        }
        catch (EmptyResultDataAccessException e) {
           return false;
        }
    }

    public UserToAuth save(UserToSave user)
    {

        if(this.existsByUsername(user.getUsername())==true||user==null) return null;
        String sql = "INSERT INTO Users (username, name, age, gender, town, password, balance) VALUES (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,user.getUsername(), user.getName(),user.getAge(), user.getGender(), user.getTown(), user.getPassword(), user.getBalance());
        UserToAuth addedUser = new UserToAuth(user.getUsername(), user.getPassword());
        return addedUser;

    }

    public void addUser(UserToSave user)
    {

        if(this.existsByUsername(user.getUsername())==true||user==null) return;
        String sql = "INSERT INTO Users (username, name, age, gender, town, password, balance) VALUES (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,user.getUsername(), user.getName(),user.getAge(), user.getGender(), user.getTown(), user.getPassword(), user.getBalance());


    }

    public void updateBalance(double balance, String username)
    {
        String sql = "UPDATE Users SET balance =? where username = ?";
        jdbcTemplate.update(sql,balance,username);
    }


    private UserToSave mapUserToSaveRow(ResultSet rs, int rowNum) throws SQLException {

        return new UserToSave(

                rs.getString("username"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("town"),
                rs.getDouble("balance"));
    }

    private UserToAuth mapUserToAuthRow(ResultSet rs, int rowNum) throws SQLException {

        return new UserToAuth(

                rs.getString("username"),
                rs.getString("password"));

    }

    private UserInfo mapUserToUserInfo(ResultSet rs, int rowNum) throws SQLException {

        return new UserInfo(
                rs.getString("username"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("town"),
                rs.getDouble("balance")
                );

    }

}

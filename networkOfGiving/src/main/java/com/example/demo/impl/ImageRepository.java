package com.example.demo.impl;

import ch.qos.logback.core.util.FileUtil;
import com.example.demo.data.Charity;

import com.example.demo.data.ImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

@Component
public class ImageRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ImageRepository(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public void saveImage(ImageModel img)
    {
        String sql = "Insert into thumbnails (name,picBytes,id) values (?,?,?)";
        jdbcTemplate.update(sql,img.getName(),img.getPicByte(),img.getCharityId());

    }

    public ImageModel getImageById(int id)
    {
        String sql = "Select id,name,picBytes from thumbnails where id=?";
        ImageModel result = null;
        try
        {
            result = jdbcTemplate.queryForObject(sql,this::mapImageRow,id);
        }
        catch (EmptyResultDataAccessException e)
        {
            result = null;
        }
        return result;

    }

    private ImageModel mapImageRow(ResultSet rs, int rowNum) throws SQLException {

        String name = rs.getString("name");
        int charityId = rs.getInt("id");
        byte[] picByte = rs.getBytes("picBytes");


        ImageModel result = new ImageModel(name,charityId,picByte);




        return result;
    }

}

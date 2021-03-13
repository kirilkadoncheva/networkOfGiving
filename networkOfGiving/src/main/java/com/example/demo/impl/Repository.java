package com.example.demo.impl;

import com.example.demo.IRepository;
import com.example.demo.data.Charity;
import com.example.demo.data.Donation;
import com.example.demo.data.Participation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class Repository implements IRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public Repository(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public int addCharity(Charity charity) {

        String sql = "INSERT INTO Charities (name, description, required_amount, required_participants, donated_amount, participants, completed, path_to_thumbnail, owner_username)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
                ps.setString(1, charity.getName());
                ps.setString(2, charity.getDescription());
                ps.setDouble(3,charity.getRequired_amount());
                ps.setInt(4,charity.getRequired_participants());
                ps.setDouble(5,charity.getDonated_amount());
                ps.setInt(6,charity.getParticipants());
                ps.setBoolean(7,charity.isCompleted());
                ps.setString(8, charity.getPathToThumbnail());
                ps.setString(9,charity.getOwner_username());
                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
//        jdbcTemplate.update(sql,charity.getName(), charity.getDescription(), charity.getRequired_amount(),
//                charity.getRequired_participants(), charity.getDonated_amount(),charity.getParticipants(), charity.isCompleted(), charity.getPathToThumbnail(), charity.getOwner_username());

    }

    @Override
    public void deleteCharity(int id) {

        this.safeDeleteAllDonationsToCharity(id);
        this.deleteAllParticipationByCharity(id);
        String sql = "DELETE FROM Charities WHERE id=?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public List<Charity> getAllCharities() {
        try{
            String sql = "SELECT * FROM Charities";
            return jdbcTemplate.query(sql, this::mapCharityRow);
        }
        catch(EmptyResultDataAccessException e)
        {
            return null;
        }


    }

    @Override
    public Charity getCharityById(int id) {

        String sql = "SELECT * FROM Charities WHERE id=?";
        return jdbcTemplate.queryForObject(sql,this::mapCharityRow,id);
    }

    public void setCharityThumbnailPath(String path, int charityId)
    {
        Charity charity = this.getCharityById(charityId);
        charity.setPathToThumbnail(path);
        this.updateCharity(charityId, charity);
    }

    @Override
    public List<Charity> getCharitiesByName(String name) {
        String sql = "SELECT * FROM Charities WHERE name=";
        return jdbcTemplate.query(sql, this::mapCharityRow,name);
    }

    @Override
    public void updateCharity(int id, Charity charity) {

        String sql = "UPDATE Charities SET " +
                "name =? , description=?, required_amount=?, required_participants=?," +
                " donated_amount=?, participants=?, completed=?, path_to_thumbnail=?, owner_username=? WHERE id=?";
        jdbcTemplate.update(sql, charity.getName(), charity.getDescription(), charity.getRequired_amount(), charity.getRequired_participants(),
                charity.getDonated_amount(), charity.getParticipants(), charity.isCompleted(), charity.getPathToThumbnail(), charity.getOwner_username(),id);

    }

    @Override
    public List<Charity> getAllCharitiesByOwner(String username) {
        String sql = "SELECT * FROM Charities WHERE owner_username=?";
        return jdbcTemplate.query(sql,this::mapCharityRow,username);
    }

    @Override
    public Double estimateDonationToCharityById(int id) {

        List<Donation> donations = this.getAllDonationsToCharity(id);
        Double sum = 0.0;
        int count = donations.size();
        for (Donation d : donations)
        {
            sum+=d.getAmount();
        }

        return sum/(double)count;
    }

    @Override
    public List<Charity> getAllDonatedToCharitiesByUsername(String username)
    {
        List<Donation> donations = this.getAllDonationsByUsername(username);
        Charity[] charities = donations.stream().map(donation -> {return donation.getCharityId();})
                .distinct()
                .map(id -> { return this.getCharityById(Integer.parseInt(id.toString()));}).toArray(Charity[]::new);
        return Arrays.asList(charities);
    }

    @Override
    public List<Charity> getAllParticipatedCharitiesByUsername(String username) {
        List<Participation> participations = this.getAllParticipationsByUsername(username);
        Charity[] charities = participations.stream().map(participation -> {return participation.getCharityId();})
                .distinct()
                .map(id -> { return this.getCharityById(Integer.parseInt(id.toString()));}).toArray(Charity[]::new);
        return Arrays.asList(charities);
    }

    public void updateBalance(double balance, String username)
    {
        String sql = "UPDATE Users SET balance =? where username = ?";
        jdbcTemplate.update(sql,balance,username);
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

//
    @Override
    public boolean addDonation(Donation donation) {

        Charity donatedTo = this.getCharityById(donation.getCharityId());
        if(donation.getAmount()>donatedTo.getNeededMoney()) return false;
        String sql = "INSERT INTO Donations (user_username, charity_id, place_date, amount) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql,donation.getDonorUsername(), donation.getCharityId(),donation.getPlaceDate(), donation.getAmount());
        donatedTo.setDonated_amount(donatedTo.getDonated_amount()+donation.getAmount());
        this.updateCharity(donatedTo.getId(),donatedTo);

        double oldBalance = this.getBalanceByUsername(donation.getDonorUsername());
        double newBalance = oldBalance - donation.getAmount();
        this.updateBalance(newBalance,donation.getDonorUsername());

        return true;
    }

    @Override
    public void deleteDonation(String username, int charityId, Date date) {


        String sql= "DELETE FROM Donations WHERE user_username = ? and charity_id=? and place_date = ?";
        jdbcTemplate.update(sql,username,charityId,date);
    }

    public void safeDeleteAllDonationsToCharity(int charityId)
    {
        List<Donation> donations = this.getAllDonationsToCharity(charityId);
        double sumToReturn=0;
        for (Donation donation : donations)
        {
            sumToReturn=donation.getAmount();
            String username = donation.getDonorUsername();
            double oldBalance = this.getBalanceByUsername(username);
            double newBalance = oldBalance + sumToReturn;
            this.updateBalance(newBalance,username);

            this.deleteDonation(username,charityId, donation.getPlaceDate());
        }


    }


    @Override
    public void deleteAllDonationsToCharity(int charityId) {

        Charity charity = this.getCharityById(charityId);
        charity.setDonated_amount(0);
        this.updateCharity(charityId, charity);
        String sql= "DELETE FROM Donations WHERE charity_id=? ";
        jdbcTemplate.update(sql,charityId);
    }

    @Override
    public List<Donation> getAllDonations() {

        String sql = "SELECT * FROM Donations";
        return jdbcTemplate.query(sql, this::mapDonationRow);
    }

    @Override
    public List<Donation> getAllDonationsByUsernameAndCharity(String username, int charityId) {
        String sql = "SELECT * FROM Donations WHERE user_username=? and charity_id=?";
        return jdbcTemplate.query(sql, this::mapDonationRow,username,charityId);
    }

    @Override
    public List<Donation> getAllDonationsByUsername(String username) {
        String sql = "SELECT * FROM Donations WHERE user_username=? ";
        return jdbcTemplate.query(sql, this::mapDonationRow,username);
    }

    @Override
    public List<Donation> getAllDonationsToCharity(int charityId) {
        String sql = "SELECT * FROM Donations WHERE charity_id=?";
        return jdbcTemplate.query(sql, this::mapDonationRow,charityId);
    }

    @Override
    public List<Donation> getAllDonationsByDate(Date date) {
        String sql = "SELECT * FROM Donations WHERE place_date=>";
        return jdbcTemplate.query(sql, this::mapDonationRow,date);
    }

    @Override
    public boolean addParticipation(Participation participation) {

        Charity charity = this.getCharityById(participation.getCharityId());
        if(charity.getNeededVolunteers()<1) return false;
        charity.setParticipants(charity.getParticipants()+1);

        String sql = "INSERT INTO Participation (user_username, charity_id, sign_up_date) VALUES (?,?,?)";
        jdbcTemplate.update(sql,participation.getVolunteerUsername(), participation.getCharityId(), participation.getSign_up_date());

        this.updateCharity(charity.getId(),charity);
        return true;
    }

    @Override
    public void deleteParticipation(String username, int charityId, Date date) {

        String sql= "DELETE FROM Participation WHERE user_username=? and charity_id=? and sign_up_date=? ";
        jdbcTemplate.update(sql,charityId,username,charityId,date);
    }

    @Override
    public void deleteAllParticipationByCharity(int charityId) {

        Charity charity = this.getCharityById(charityId);
        charity.setParticipants(0);
        this.updateCharity(charityId, charity);
        String sql= "DELETE FROM Participation WHERE charity_id=?";
        jdbcTemplate.update(sql,charityId);
    }

    @Override
    public List<Participation> getAllParticipations() {
        String sql = "SELECT * FROM Participation";
        return jdbcTemplate.query(sql, this::mapParticipationRow);
    }

    @Override
    public List<Participation> getAllParticipationsByUsernameAndCharity(String username, int charityId) {
        String sql = "SELECT * FROM Participation WHERE user_username=? AND charity_id=?";
        return jdbcTemplate.query(sql, this::mapParticipationRow,username,charityId);
    }

    @Override
    public List<Participation> getAllParticipationsByUsername(String username) {
        String sql = "SELECT * FROM Participation WHERE user_username=?";
        return jdbcTemplate.query(sql, this::mapParticipationRow,username);
    }

    @Override
    public List<Participation> getAllParticipationsByCharity(int charityId) {
        String sql = "SELECT * FROM Participation WHERE charity_id=?";
        return jdbcTemplate.query(sql, this::mapParticipationRow,charityId);
    }

    @Override
    public List<Participation> getAllParticipationsByDate(Date date) {
        String sql = "SELECT * FROM Participation WHERE sign_up_date=?";
        return jdbcTemplate.query(sql, this::mapParticipationRow,date);
    }

    private Charity mapCharityRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String owner = rs.getString("owner_username");
        String name = rs.getString("name");
        String description = rs.getString("description");
        double required_amount = rs.getDouble("required_amount");
        double donated_amount = rs.getDouble("donated_amount");
        int required_participants = rs.getInt("required_participants");
        int participants = rs.getInt("participants");
        boolean completed = rs.getBoolean("completed");
        String pathToThumbnail = rs.getString("path_to_thumbnail");

        Charity result = new Charity(id,owner,name,description,required_amount,required_participants,donated_amount,participants,completed,pathToThumbnail);


        return result;
    }

//    private User mapUserRow(ResultSet rs, int rowNum) throws SQLException {
//
//        return new User(rs.getString("username"),
//                rs.getString("name"),
//                rs.getInt("age"),
//                UserGender.fromString(rs.getString("gender")),
//                rs.getString("town"),
//                rs.getString("password"),
//                rs.getDouble("balance"));
//    }

    private Donation mapDonationRow(ResultSet rs, int rowNum) throws SQLException {

        return new Donation(rs.getString("user_username"),
                rs.getInt("charity_id"),
                rs.getDouble("amount"),
                rs.getDate("place_date"));
    }

    private Participation mapParticipationRow(ResultSet rs, int rowNum) throws SQLException {

        return new Participation(rs.getString("user_username"),
                rs.getInt("charity_id"),
                rs.getDate("sign_up_date"));
    }

    private Double mapDoubleRow(ResultSet rs, int rowNum) throws SQLException {

        return rs.getDouble("amount");
    }

    //   @Override
//    public void addUser(User user) {
//
//        String sql = "INSERT INTO Users (username, name, age, gender, town, password, balance) VALUES (?,?,?,?,?,?,?)";
//        jdbcTemplate.update(sql,user.getUsername(), user.getName(),user.getAge(), user.getGender(), user.getTown(), user.getEncodedPassword(), user.getBalance());
//
//    }
//
//    @Override
//    public void deleteUser(String username) {
//        String sql= "DELETE FROM Users WHERE username = ?";
//        jdbcTemplate.update(sql,username);
//    }
//
//    @Override
//    public void updateUser(String username, User user) {
//        this.deleteUser(username);
//        this.addUser(user);
//
//    }
//
//    @Override
//    public User getUserByUsername(String username) {
//        String sql = "SELECT * FROM users WHERE username=?";
//        return jdbcTemplate.queryForObject(sql,this::mapUserRow,username);
//    }
//
//    @Override
//    public boolean isUsernameTaken(String username)
//    {
//
//        User user1 = getUserByUsername(username);
//        if(user1==null) return false;
//        return true;
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        String sql = "SELECT * FROM Users";
//        return jdbcTemplate.query(sql, this::mapUserRow);
//    }

}

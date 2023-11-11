package com.lamar.LamarOnlineBanking.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lamar.LamarOnlineBanking.Model.User;

public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {

		User user = new User();
		user.setUserId(rs.getString("userId"));
		user.setFirstName(rs.getString("firstName"));
		user.setLastName(rs.getString("lastName"));
		user.setEmail(rs.getString("email"));
		user.setMobile(rs.getString("mobile"));
		user.setPassword(rs.getString("password"));
		user.setAge(rs.getInt("age"));
		user.setProfilePic(rs.getBytes("profilePic"));
		user.setDob(rs.getDate("dob")+"");
		user.setGender(rs.getString("gender"));
		user.setMaritialStatus(rs.getString("maritialstatus"));
		user.setCitizen(rs.getString("citizen"));
		return user;
	}

}

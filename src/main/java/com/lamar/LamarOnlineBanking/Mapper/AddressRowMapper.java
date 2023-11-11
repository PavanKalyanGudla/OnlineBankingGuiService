package com.lamar.LamarOnlineBanking.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lamar.LamarOnlineBanking.Model.Address;

public class AddressRowMapper implements RowMapper<Address>{

	@Override
	public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
		Address address = new Address();
		address.setUserId(rs.getString("userId"));
		address.setStreet(rs.getString("street"));
		address.setBuilding(rs.getString("building"));
		address.setState(rs.getString("state"));
		address.setCountry(rs.getString("country"));
		address.setPincode(rs.getString("pincode"));

		return address;
	}

}

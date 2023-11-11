package com.lamar.LamarOnlineBanking.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lamar.LamarOnlineBanking.Model.AppointmentTimeTable;

public class AppointmentTimeTableMapper implements RowMapper<AppointmentTimeTable>{

	@Override
	public AppointmentTimeTable mapRow(ResultSet rs, int rowNum) throws SQLException {
		AppointmentTimeTable a = new AppointmentTimeTable();
		a.setDate(rs.getDate("date")+"");
		a.setSlot1(rs.getString("slot1"));
		a.setSlot2(rs.getString("slot2"));
		a.setSlot3(rs.getString("slot3"));
		a.setSlot4(rs.getString("slot4"));
		a.setSlot5(rs.getString("slot5"));
		a.setSlot6(rs.getString("slot6"));
		a.setSlot7(rs.getString("slot7"));
		a.setSlot8(rs.getString("slot8"));
		a.setSlot9(rs.getString("slot9"));
		a.setSlot10(rs.getString("slot10"));
		return a;
	}

}

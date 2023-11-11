package com.lamar.LamarOnlineBanking.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lamar.LamarOnlineBanking.Model.DebitCardSchedule;

public class DebitCardScheduleRowMapper implements RowMapper<DebitCardSchedule>{

	@Override
	public DebitCardSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
		DebitCardSchedule card = new DebitCardSchedule();
		
		card.setUserId(rs.getString("userId"));
		card.setCardNumber(rs.getString("cardNumber"));
		card.setScheduled(rs.getString("scheduled"));
		card.setFromDate(rs.getString("fromDate"));
		card.setToDate(rs.getString("toDate"));
		
		return card;
	}

}

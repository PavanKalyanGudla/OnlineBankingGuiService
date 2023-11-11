package com.lamar.LamarOnlineBanking.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lamar.LamarOnlineBanking.Model.DebitCard;

public class CardRowMapper implements RowMapper<DebitCard>{

	@Override
	public DebitCard mapRow(ResultSet rs, int rowNum) throws SQLException {
		DebitCard card = new DebitCard();
		card.setUserId(rs.getString("userId"));
		card.setAccountNumber(rs.getString("accountNumber"));
		card.setCardNumber(rs.getString("cardNumber"));
		card.setCvv(rs.getString("cvv"));
		card.setCardStatus(rs.getBoolean("cardStatus"));
		return card;
	}

}

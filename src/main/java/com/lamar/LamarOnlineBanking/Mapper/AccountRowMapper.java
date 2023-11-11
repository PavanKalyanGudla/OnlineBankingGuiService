package com.lamar.LamarOnlineBanking.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lamar.LamarOnlineBanking.Model.Account;

public class AccountRowMapper implements RowMapper<Account>{

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account();
		account.setUserId(rs.getString("userId"));
		account.setAccountNumber(rs.getString("accountNumber"));
		account.setAccountType(rs.getString("accountType"));
		account.setCurrentBalance(rs.getDouble("currentBalance"));
		account.setSavingsBalance(rs.getDouble("savingsBalance"));
		account.setDateOpened(rs.getDate("dateOpened"));
		account.setDateClosed(rs.getDate("dateClosed"));
		account.setAccountStatus(rs.getString("accountStatus"));
		return account;
	}
}

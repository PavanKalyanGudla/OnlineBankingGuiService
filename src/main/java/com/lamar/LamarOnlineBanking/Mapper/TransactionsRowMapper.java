package com.lamar.LamarOnlineBanking.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lamar.LamarOnlineBanking.Model.Transactions;

public class TransactionsRowMapper implements RowMapper<Transactions>{

	@Override
	public Transactions mapRow(ResultSet rs, int rowNum) throws SQLException {
		Transactions transactions = new Transactions();
		transactions.setUserId(rs.getString("userId"));
		transactions.setTransactionId(rs.getInt("transactionId"));
		transactions.setAccountNumber(rs.getString("accountNumber"));
		transactions.setTransactionDate(rs.getDate("transactionDate"));
		transactions.setTransactionType(rs.getString("transactionType"));
		transactions.setBeforeAmount(rs.getDouble("beforeAmount"));
		transactions.setAmountTransfer(rs.getDouble("amountTransfer"));
		transactions.setAfterAmount(rs.getDouble("afterAmount"));
		transactions.setDescription(rs.getString("description"));
		transactions.setSender(rs.getString("sender"));
		transactions.setReceiver(rs.getString("receiver"));
		transactions.setTransactionStatus(rs.getString("TransactionStatus"));
		return transactions;
	}

}

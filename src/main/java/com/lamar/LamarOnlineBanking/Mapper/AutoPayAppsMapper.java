package com.lamar.LamarOnlineBanking.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lamar.LamarOnlineBanking.Model.AutoPayApps;

public class AutoPayAppsMapper implements RowMapper<AutoPayApps>{

	@Override
	public AutoPayApps mapRow(ResultSet rs, int rowNum) throws SQLException {
		AutoPayApps apps = new AutoPayApps();
		apps.setUserId(rs.getString("userId"));
		apps.setCustomer_name(rs.getString("customer_name"));
		apps.setAccount_number(rs.getString("account_number"));
		apps.setPayment_amount(rs.getString("payment_amount"));
		apps.setStart_date(rs.getString("start_date"));
		apps.setAccount_type(rs.getString("account_type"));
		apps.setPayment_frequency(rs.getString("payment_frequency"));
		apps.setNetflix(rs.getBoolean("netflix"));
		apps.setPrime(rs.getBoolean("prime"));
		apps.setHulu(rs.getBoolean("hulu"));
		apps.setAppleTv(rs.getBoolean("appleTv"));
		apps.setDisneyStar(rs.getBoolean("disneyStar"));
		apps.setYoutubeTv(rs.getBoolean("youtubeTv"));

		return apps;
	}

}
package com.lamar.LamarOnlineBanking.Dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.lamar.LamarOnlineBanking.Constants.SqlQueries;
import com.lamar.LamarOnlineBanking.Mapper.AccountRowMapper;
import com.lamar.LamarOnlineBanking.Mapper.AddressRowMapper;
import com.lamar.LamarOnlineBanking.Mapper.CardRowMapper;
import com.lamar.LamarOnlineBanking.Mapper.DebitCardScheduleRowMapper;
import com.lamar.LamarOnlineBanking.Mapper.TransactionsRowMapper;
import com.lamar.LamarOnlineBanking.Mapper.UserRowMapper;
import com.lamar.LamarOnlineBanking.Mapper.AppointmentTimeTableMapper;
import com.lamar.LamarOnlineBanking.Mapper.AutoPayAppsMapper;
import com.lamar.LamarOnlineBanking.Model.Account;
import com.lamar.LamarOnlineBanking.Model.Address;
import com.lamar.LamarOnlineBanking.Model.AmountTransfer;
import com.lamar.LamarOnlineBanking.Model.Appointment;
import com.lamar.LamarOnlineBanking.Model.AppointmentTimeTable;
import com.lamar.LamarOnlineBanking.Model.AutoPayApps;
import com.lamar.LamarOnlineBanking.Model.DebitCard;
import com.lamar.LamarOnlineBanking.Model.DebitCardSchedule;
import com.lamar.LamarOnlineBanking.Model.ResponseObj;
import com.lamar.LamarOnlineBanking.Model.Transactions;
import com.lamar.LamarOnlineBanking.Model.User;
import com.lamar.LamarOnlineBanking.Service.OnlineBankingService;

@Repository
public class DataAccess {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private OnlineBankingService service;
	
	@Autowired
    public DataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	public ResponseObj getUser(String userId, String password) {
		ResponseObj obj = new ResponseObj();
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(SqlQueries.GET_USER_INFO, new Object[] {userId,password}, new UserRowMapper());
			byte[] encode = null;
			if(user.getProfilePic() != null) {
				encode = java.util.Base64.getEncoder().encode(user.getProfilePic());
			}
			obj.setUser(user);
			obj.setResponseCode("200");
			obj.setResponseMessage("SUCCESS");
		}catch (EmptyResultDataAccessException e) {
			obj.setUser(user);
			obj.setResponseCode("0");
			obj.setResponseMessage("FAIL");
        }
		return obj;
	}
	
	public boolean getUser(String userId) {
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(SqlQueries.GET_USER, new Object[] {userId}, new UserRowMapper());
			if(user != null) {
				String body = SqlQueries.forgotEmailUsernameRemember(user);
				service.sendSimpleEmail(user.getEmail(),SqlQueries.EMAIL_FORGOT_PASSWORD,body);
				return true;
			}
		}catch (EmptyResultDataAccessException e) {
			return false;
        }
		return false;
	}
	
	public List<User> getAllUsers(){
		List<User> users = jdbcTemplate.query(SqlQueries.GET_ALL_USERS, new UserRowMapper());
		return users;
	}
	
	public int updateProfilePic(String userId, String password, byte[] image){
		try {
			int update = jdbcTemplate.update(SqlQueries.UPLOAD_PROFILE_PIC , image , userId);
			return update;
		}catch(Exception e) {
			return 0;
		}
	}
	
	public Address getUserAddress(String userId) {
		Address address = null;
		try {
			address = jdbcTemplate.queryForObject(SqlQueries.GET_USER_ADDRESS, new Object[] {userId}, new AddressRowMapper());
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return address;
	}
	
	public Account getUserAccountInfo(String userId) {
		Account account = null;
		try {
			account = jdbcTemplate.queryForObject(SqlQueries.GET_USER_ACCOUNT, new Object[] {userId}, new AccountRowMapper());
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return account;
	}
	
	public String updateUserAccountInfo(DebitCard debitCard) {
		try {
			int update = jdbcTemplate.update(SqlQueries.UPDATE_USER_CARD_INFO , debitCard.isCardStatus(), debitCard.getUserId());
			if(update > 0) {
				return "Success";
			}else {
				return "Fail";
			}
		}catch(Exception e) {
			return "Fail";
		}
	}
	
	public List<Transactions> getUserTransactionsInfo(String userId){
		List<Transactions> Transactions = null;
		try {
			Transactions = jdbcTemplate.query(SqlQueries.GET_USER_TRANSACTIONS, new Object[] {userId}, new TransactionsRowMapper());
		}catch(EmptyResultDataAccessException e) {
		}
		return Transactions;
	}
	
	public String userRegistration(User user, Address address) {
		 LocalDate today = LocalDate.now();
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	     String formattedDate = today.format(formatter);
	     int insUser = 0;
	     int insAdd = 0;
	     int insAcc = 0;
	     int insCard = 0;
	     
		try {
			insUser = jdbcTemplate.update(SqlQueries.INSERT_USER_INFO, user.getUserId(),user.getPassword(),
					user.getMobile(),user.getMaritialStatus(),user.getLastName(),user.getGender(),
					user.getFirstName(),user.getEmail(),user.getDob(),user.getCitizen(),user.getAge());
			
			if( insUser > 0) {
				insAdd = jdbcTemplate.update(SqlQueries.INSERT_USER_ADDRESS,address.getUserId(),address.getStreet(),address.getBuilding(),
						address.getState(),address.getCountry(),address.getPincode());
				
				if(insUser > 0 && insAdd > 0) {
					String accNum = generateAccountNumber();
					insAcc = jdbcTemplate.update(SqlQueries.INSERT_USER_ACCOUNT,user.getUserId(),accNum,
								"CURRENT",0.0,0.0,formattedDate,null,"Active");
					if(insAcc > 0) {
						String cardNum = generateCardNumber();
						String cvv = generateCvv();
						insCard = jdbcTemplate.update(SqlQueries.INSERT_DEBIT_CARD,user.getUserId(),cardNum,accNum,cvv);
						if(insCard > 0) {
							String body = SqlQueries.getEmailRegistrationBody(user.getFirstName()+" "+user.getLastName(), user.getUserId(), user.getEmail(), formattedDate);
							service.sendSimpleEmail(user.getEmail(),SqlQueries.EMAIL_Registration_SUCCESSFUL,body);
							return "User Created Successfully ! ";
						}
					}
				}
			}
		}catch(EmptyResultDataAccessException e) {
		}
		return "";
	}
	
	public static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder(SqlQueries.PREFIX);
        for (int i = 0; i < SqlQueries.ACCOUNT_NUMBER_LENGTH; i++) {
            int digit = random.nextInt(10);
            accountNumber.append(digit);
        }
        return accountNumber.toString();
    }
	
	public static String generateCardNumber() {
		Random random = new Random();
		StringBuilder cardNumber = new StringBuilder("");
		for(int i=0; i< 12; i++) {
			cardNumber.append(random.nextInt(10));
		}
		return cardNumber.toString();
	}
	
	public static String generateCvv() {
		Random random = new Random();
		StringBuilder cvv = new StringBuilder("");
		for(int i=0; i < 3; i++) {
			cvv.append(random.nextInt(10));
		}
		return cvv.toString();
	}
	
	public String saveUserInfo(User userObj) {
		try {
			int update = jdbcTemplate.update(SqlQueries.UPDATE_USER_INFO , userObj.getFirstName(),userObj.getLastName(),
					userObj.getMobile(),userObj.getPassword(),userObj.getAge(),userObj.getDob(),userObj.getGender(),
					userObj.getMaritialStatus(),userObj.getCitizen(),userObj.getUserId());

			if(update > 0) {
				return "User Updated Successfully ! ";
			}else {
				return "User Update Failed ! ";
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "User Update Application Exception ! ";
		}
	}
	
	public DebitCard getDebitCardInfo(String userId, String accountNumber){
		DebitCard card = null;
		try {
			card = (DebitCard) jdbcTemplate.queryForObject(SqlQueries.GET_CARD_INFO,
					new Object[] {userId,accountNumber},
					new CardRowMapper());
		}catch(Exception e) {
			card = null;
		}
		return card;
	}
	
	public String scheduleDebitCard(DebitCard debitCard) {
		DebitCardSchedule cardScheduledInfo = getCardScheduledInfo(debitCard.getUserId(),debitCard.getCardNumber());
		
		if(!StringUtils.isEmpty(cardScheduledInfo) && debitCard.getDayOfWeek().equalsIgnoreCase(cardScheduledInfo.getScheduled())) {
			return "Card Scheduled already Exists for "+debitCard.getDayOfWeek()+" !";
		}else {
			try {
				int insert = jdbcTemplate.update(SqlQueries.INSERT_SCHEDULE_DEBIT_CARD ,debitCard.getUserId()
						,debitCard.getCardNumber(),debitCard.getDayOfWeek(),debitCard.getStartTime()
						,debitCard.getStopTime());
				
				if(insert > 0) {
					return "Card Scheduled Successfully ! ";
				}else {
					return "Card Scheduled Failed ! ";
				}
			}catch(Exception e) {
				return "Card Scheduled Application Exception ! ";
			}
		}
	}
	
	public DebitCardSchedule getCardScheduledInfo(String userId,String cardNumber) {
		DebitCardSchedule debitCard = null;
		try {
			debitCard = jdbcTemplate.queryForObject(SqlQueries.GET_CARD_SCHEDULE_INFO,
					new Object[] {userId , cardNumber},
					new DebitCardScheduleRowMapper());
		}catch(Exception e) {
		}
		return debitCard;
	}
	
	public List<DebitCardSchedule> getCardScheduledInfoList(String userId,String cardNumber) {
		List<DebitCardSchedule> debitCard = null;
		try {
			debitCard = jdbcTemplate.query(SqlQueries.GET_CARD_SCHEDULE_INFO,
					new Object[] {userId , cardNumber},
					new DebitCardScheduleRowMapper());
		}catch(Exception e) {
		}
		return debitCard;
	}
	
	public String deleteDebitCardScheduled(String scheduledDay, String cardNumber) {
		try {
			int delete = jdbcTemplate.update(SqlQueries.DELETE_SCHEDULED_CARD,
					cardNumber,scheduledDay);
			
			if(delete > 0) {
				return "Delete Success";
			}else {
				return "Delete Failed";
			}
		}catch(Exception e) {
			return "Application Exception";
		}
	}
	
	public String zelleTransaction(AmountTransfer zelleobject) {
		User sender = zelleobject.getSender();
		User receiver = zelleobject.getReceiver();
		int zelAmount = zelleobject.getZelAmount();
		
		try {
			Account senderAccountInfo = getUserAccountInfo(sender.getUserId());
			double senCurBal = senderAccountInfo.getCurrentBalance();
			Account receiverAccountInfo = getUserAccountInfo(receiver.getUserId());
			double recCurBal = receiverAccountInfo.getCurrentBalance();
			LocalDate today = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String todaysDate = today.format(formatter);
	        String transactionStatus = "";
			if(senCurBal >= zelAmount) {
				double senUpdBal = senCurBal-zelAmount;
				int senderBalance = jdbcTemplate.update(SqlQueries.UPDATE_ACCOUNT,senUpdBal,sender.getUserId(),senderAccountInfo.getAccountNumber());
				if(senderBalance > 0) {
					double recUpdBal = recCurBal+zelAmount;
					int receiverBalance = jdbcTemplate.update(SqlQueries.UPDATE_ACCOUNT,recUpdBal,receiver.getUserId(),receiverAccountInfo.getAccountNumber());
					if(receiverBalance > 0) {
						transactionStatus = "Success";
					}else {
						transactionStatus = "Fail";
					}
					int updSendTran = jdbcTemplate.update(SqlQueries.INSERT_TRANSACTION,sender.getUserId(),senderAccountInfo.getAccountNumber(),
							todaysDate,"DEBIT",senCurBal,zelAmount,senUpdBal,"DEBIT",sender.getUserId(),receiver.getUserId(),transactionStatus);
						
					int updReceTran = jdbcTemplate.update(SqlQueries.INSERT_TRANSACTION,receiver.getUserId(),receiverAccountInfo.getAccountNumber(),
							todaysDate,"CREDIT",recCurBal,zelAmount,recUpdBal,"CREDIT",receiver.getUserId(),sender.getUserId(),transactionStatus);
					
					if(updSendTran > 0 && updReceTran > 0) {
						return "Transaction Success";
					}
				}				
			}else {
				return "Balance In Sufficient";
			}
		}catch(Exception e) {
			return "Application Exception";
		}
		return "";
	}
	
	public AppointmentTimeTable getAppointmentTimeTable(Appointment appointment) {
		AppointmentTimeTable timetable = null;
		try {
			timetable = jdbcTemplate.queryForObject(SqlQueries.GET_APPOINTMENT_TIMETABLE_BY_DATE,
					new Object[] {appointment.getDate()},new AppointmentTimeTableMapper());
			return timetable;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return timetable;
	}
	
	public List<AppointmentTimeTable> getViewAppointmentTimeTable() {
		List<AppointmentTimeTable> timetable = null;
		try {
			timetable = jdbcTemplate.query(SqlQueries.GET_APPOINTMENT_TIMETABLE,
					new AppointmentTimeTableMapper());
			return timetable;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return timetable;
	}
	
	public AutoPayApps getAutoPay(String userid) { 
		AutoPayApps autoPay = null;
		try {
			autoPay = jdbcTemplate.queryForObject(SqlQueries.GET_AUTO_PAY,new Object[]{userid}, new AutoPayAppsMapper());
		}catch(Exception e) {
		}
		return autoPay;
	}
	
	public String bookAppointment(Appointment appointment) {
		String response = "";
		try {
			AppointmentTimeTable timetable = getAppointmentTimeTable(appointment);
			if(timetable != null) {
				String slot = getSlot(appointment.getTime());
				String appId = generateAppointmentId();
				switch(slot) {
					case "Slot1":
						if(timetable.getSlot1() == null) {
							timetable.setSlot1(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
					case "Slot2":
						if(timetable.getSlot2() == null) {
							timetable.setSlot2(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
					case "Slot3":
						if(timetable.getSlot3() == null) {
							timetable.setSlot3(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
					case "Slot4":
						if(timetable.getSlot4() == null) {
							timetable.setSlot4(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
					case "Slot5":
						if(timetable.getSlot5() == null) {
							timetable.setSlot5(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
					case "Slot6":
						if(timetable.getSlot6() == null) {
							timetable.setSlot6(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
					case "Slot7":
						if(timetable.getSlot7() == null) {
							timetable.setSlot7(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
					case "Slot8":
						if(timetable.getSlot8() == null) {
							timetable.setSlot8(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
					case "Slot9":
						if(timetable.getSlot9() == null) {
							timetable.setSlot9(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
					case "Slot10":
						if(timetable.getSlot10() == null) {
							timetable.setSlot10(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
					case "Slot11":
						if(timetable.getSlot11() == null) {
							timetable.setSlot11(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
					case "Slot12":
						if(timetable.getSlot12() == null) {
							timetable.setSlot1(appId);
						}else {
							return "Slot "+ appointment.getTime()+" On "+appointment.getDate()+" Already Booked !!!";
						}
						break;
				}

				try {
					int upd = jdbcTemplate.update(SqlQueries.UPDATE_APPOINTMENT_TIMETABLE_BY_DATE,timetable.getSlot1(),timetable.getSlot2(),
						timetable.getSlot3(),timetable.getSlot4(),timetable.getSlot5(),timetable.getSlot6(),timetable.getSlot7(),
						timetable.getSlot8(),timetable.getSlot9(),timetable.getSlot10(),timetable.getSlot11(),timetable.getSlot12(),
						timetable.getDate());
					
					if(upd > 0) {
						int appIns = jdbcTemplate.update(SqlQueries.INSERT_APPOINTMENT,appId,appointment.getFullName(),appointment.getPhoneNumber(),appointment.getEmail(),appointment.getDate()
								,appointment.getTime(),appointment.getLocality(),appointment.getCity(),appointment.getState(),appointment.getPinCode());
						
						if(appIns > 0) {
							String body = SqlQueries.getEmailAppointmentContent(appointment.getFullName(), appointment.getDate(), appointment.getTime(), appointment.getLocality());
							service.sendSimpleEmail(appointment.getEmail(),SqlQueries.EMAIL_APPOINTMENT_SUBJECT,body);
							response = "Appointment Scheduled Successfully, Appointment Id : "+appId;
						}
					}else {
						response = "Appointment Scheduled Failed, Appointment Id : "+appId;
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}else {
				response = "Date Not Scheduled For Appointment";
			}
		}catch(Exception e) {
		}
		return response;
	}
	
	public String updateAutoPay(AutoPayApps apps){
		try {
			AutoPayApps autoPay = getAutoPay(apps.getUserId());
			if(autoPay != null) {
				int update = jdbcTemplate.update(SqlQueries.UPDATE_AUTO_PAY,apps.isNetflix(),apps.isPrime(),apps.isHulu(),
						apps.isAppleTv(),apps.isDisneyStar(),apps.isYoutubeTv(),apps.getUserId());
				if(update > 0) {
					return "SUCCESSFULLY UPDATED";
				}else {
					return "UPDATE_FAILED";
				}
			}else {
				int insert = jdbcTemplate.update(SqlQueries.INSERT_INTO_AUTOPAY,apps.isNetflix(),apps.isPrime(),apps.isHulu(),
						apps.isAppleTv(),apps.isDisneyStar(),apps.isYoutubeTv(),apps.getUserId());
				if(insert > 0) {
					return "SUCCESSFULLY INSERTED";
				}else {
					return "INSERT_FAILED";
				}
			}
		}catch(Exception e) {
		}
		return "";
	}
	
	public String getSlot(String time ) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime time1 = LocalTime.parse(time, formatter);

		if(time1.compareTo(LocalTime.parse("09:00", formatter)) == 0 || (time1.compareTo(LocalTime.parse("09:00", formatter)) > 0 && time1.compareTo(LocalTime.parse("09:30", formatter)) < 0)) {
			return "Slot1";
		}else if(time1.compareTo(LocalTime.parse("09:30", formatter)) == 0 || (time1.compareTo(LocalTime.parse("09:30", formatter)) > 0 && time1.compareTo(LocalTime.parse("10:00", formatter)) < 0)) {
			return "Slot2";
		}else if(time1.compareTo(LocalTime.parse("10:00", formatter)) == 0 || (time1.compareTo(LocalTime.parse("10:00", formatter)) > 0 && time1.compareTo(LocalTime.parse("10:30", formatter)) < 0)) {
			return "Slot3";
		}else if(time1.compareTo(LocalTime.parse("10:30", formatter)) == 0 || (time1.compareTo(LocalTime.parse("10:30", formatter)) > 0 && time1.compareTo(LocalTime.parse("11:00", formatter)) < 0)) {
			return "Slot4";
		}else if(time1.compareTo(LocalTime.parse("11:00", formatter)) == 0 || (time1.compareTo(LocalTime.parse("11:00", formatter)) > 0 && time1.compareTo(LocalTime.parse("11:30", formatter)) < 0)) {
			return "Slot5";
		}else if(time1.compareTo(LocalTime.parse("11:30", formatter)) == 0 || (time1.compareTo(LocalTime.parse("11:30", formatter)) > 0 && time1.compareTo(LocalTime.parse("12:00", formatter)) < 0)) {
			return "Slot6";
		}else if(time1.compareTo(LocalTime.parse("13:00", formatter)) == 0 || (time1.compareTo(LocalTime.parse("13:00", formatter)) > 0 && time1.compareTo(LocalTime.parse("13:30", formatter)) < 0)) {
			return "Slot7";
		}else if(time1.compareTo(LocalTime.parse("13:30", formatter)) == 0 || (time1.compareTo(LocalTime.parse("13:30", formatter)) > 0 && time1.compareTo(LocalTime.parse("14:00", formatter)) < 0)) {
			return "Slot8";
		}else if(time1.compareTo(LocalTime.parse("14:00", formatter)) == 0 || (time1.compareTo(LocalTime.parse("14:00", formatter)) > 0 && time1.compareTo(LocalTime.parse("14:30", formatter)) < 0)) {
			return "Slot9";
		}else if(time1.compareTo(LocalTime.parse("14:30", formatter)) == 0 || (time1.compareTo(LocalTime.parse("14:30", formatter)) > 0 && time1.compareTo(LocalTime.parse("15:00", formatter)) < 0)) {
			return "Slot10";
		}else if(time1.compareTo(LocalTime.parse("15:00", formatter)) == 0 || (time1.compareTo(LocalTime.parse("15:00", formatter)) > 0 && time1.compareTo(LocalTime.parse("15:30", formatter)) < 0)) {
			return "Slot11";
		}else if(time1.compareTo(LocalTime.parse("15:30", formatter)) == 0 || (time1.compareTo(LocalTime.parse("15:30", formatter)) > 0 && time1.compareTo(LocalTime.parse("16:00", formatter)) < 0)) {
			return "Slot12";
		}
		return "";
	}
	
	public static String generateAppointmentId() {
        int length = 5;
        StringBuilder appointmentId = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            appointmentId.append(digit);
        }
        return appointmentId.toString();
    }

}

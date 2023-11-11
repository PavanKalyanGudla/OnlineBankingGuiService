package com.lamar.LamarOnlineBanking.Constants;

import com.lamar.LamarOnlineBanking.Model.User;

public class SqlQueries {
	
	public static final String PREFIX = "ACCT";
	
	public static final int ACCOUNT_NUMBER_LENGTH = 10;
	
	public static final String GET_ALL_USERS = "select * from user";
	
	public static final String GET_USER = "select * from user where userId = ?";
	
	public static final String GET_USER_INFO = "select * from user where userId = ? and password = ?";
	
	public static final String GET_USER_ADDRESS = "select * from Address where userId = ?";
	
	public static final String GET_USER_ACCOUNT = "select * from Account where userId = ?";
	
	public static final String GET_USER_TRANSACTIONS = "select * from Transactions where userId = ? order by 'transactionId' desc";
	
	public static final String GET_CARD_INFO = "select * from debitcard where userId = ? and accountNumber = ?";
	
	public static final String GET_CARD_SCHEDULE_INFO = "select * from DebitCardSchedule where userId=? and cardNumber=?;";
	
	public static final String GET_APPOINTMENT_TIMETABLE_BY_DATE = "select * from appointmentTimeTable where date = ?";
	
	public static final String GET_APPOINTMENT_TIMETABLE = "select * from appointmentTimeTable;";
	
	public static final String GET_BOOKING_INFO = "select * from bookingTable;";
	
	public static final String GET_AUTO_PAY = "select * from autopay where userId = ?;";
	
	public static final String UPLOAD_PROFILE_PIC = "update user set profilePic = ? where userId = ?";
	
	public static final String UPDATE_USER_INFO = "update user set "
			+ "firstName = ?, lastName = ?, mobile=?, password=?, age=?, dob=?, gender=?, maritialStatus=?,"
			+ "citizen = ? where userId = ?;";
	
	public static final String UPDATE_USER_CARD_INFO = "update debitcard set cardStatus = ? where userid = ?;";
	
	public static final String UPDATE_ACCOUNT = "update Account set currentBalance = ? where userId = ? and accountNumber = ?;";
	
	public static final String UPDATE_APPOINTMENT_TIMETABLE_BY_DATE = "update appointmentTimeTable set slot1=?, slot2=?, slot3=?, slot4=?, slot5=?, slot6=?, slot7=?, slot8=?, slot9=?, slot10=?, slot11=?, slot12=? where date = ?";

	public static final String UPDATE_AUTO_PAY = "update autopay set netflix = ?,prime = ?,"
			+ "hulu = ?,appleTv = ?,disneyStar = ?,youtubeTv = ? where userId = ?";
	
	public static final String INSERT_USER_INFO = "insert into user(userId,password,mobile,maritialstatus,lastName,gender,firstName,email,dob,citizen,age)"
			+ "values(?,?,?,?,?,?,?,?,?,?,?);";
	
	public static final String INSERT_USER_ADDRESS = "insert into Address(userId,street,building,state,country,pincode)"
			+"values(?,?,?,?,?,?);";
	
	public static final String INSERT_USER_ACCOUNT = "INSERT INTO Account (userId ,accountNumber, accountType, currentBalance, savingsBalance, dateOpened, dateClosed, accountStatus)"
			+ "VALUES (?,?,?,?,?,?,?,?);";
	
	public static final String INSERT_SCHEDULE_DEBIT_CARD = "INSERT INTO DebitCardSchedule(userId,cardNumber,scheduled,fromDate,toDate)"
			+"VALUES (?,?,?,?,?);";
	
	public static final String INSERT_DEBIT_CARD = "INSERT INTO debitcard(userId,cardNumber,accountNumber,cvv)"
			+"VALUES(?,?,?,?);";
			
	public static final String DELETE_SCHEDULED_CARD = "Delete from DebitCardSchedule where cardNumber = ? and scheduled=?;";
	
	public static final String INSERT_TRANSACTION = "insert into Transactions(userId,accountNumber,transactionDate, transactionType,beforeAmount,amountTransfer,afterAmount,description,sender,receiver,TransactionStatus)"
				+"values(?,?,?,?,?,?,?,?,?,?,?);";
	
	public static final String INSERT_APPOINTMENT = "insert into bookingTable(bookingId,fullName,phoneNumber,email,date,time,locality,city,state,pinCode) values (?,?,?,?,?,?,?,?,?,?)";
		
	public static final String INSERT_INTO_AUTOPAY = "INSERT INTO autopay (userId, customer_name, account_number, payment_amount, payment_frequency, start_date, account_type, netflix, hulu, prime, appleTv, disneyStar, youtubeTv)"
	+"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static final String EMAIL_APPOINTMENT_SUBJECT = "Online Banking Appointment Scheduled Successfully ...";
	
	public static final String EMAIL_Registration_SUCCESSFUL = "Online Banking User Registration Successful ...";

	public static final String EMAIL_FORGOT_PASSWORD = "Online Banking : Your Password Remember Request";
	
	public static String getEmailAppointmentContent(String fullName, String date, String time, String location) {
		String EMAIL_APPOINTMENT_CONTENT = String.format(
			    "Dear %s,%n%n" +
			    "We are pleased to inform you that your appointment has been successfully scheduled. Please find the details of your appointment below:%n%n" +
			    "- **Appointment Date:** %s %n" +
			    "- **Appointment Time:** %s %n" +
			    "- **Location:** %s %n" +
			    "Additional Information:%n" +
			    "Please make sure all the mandatory documents %n%n" +
			    "If you have any questions or need to make changes to your appointment, please do not hesitate to contact us. We are here to assist you.%n%n" +
			    "Thank you for choosing our services, and we look forward to serving you on the scheduled date.%n%n" +
			    "Best regards,%n" +
			    "Online Banking System%n" +
			    "Street, Beaumont , Texas.%n" +
			    "+1 9999999999%n" +
			    "onlinebanking@example.com", fullName, date, time ,location
			);
		return EMAIL_APPOINTMENT_CONTENT;
	}
	
	public static String getEmailRegistrationBody(String fullName, String userName, String email, String date) {
		String EMAIL_REGISTRATION_CONTENT = String.format(
			    "Dear %s,%n%n" +
			    "We are thrilled to welcome you to Online Banking System! Your registration has been successfully completed, and you are now a part of our community.%n%n" +
			    "Here are your registration details:%n%n"+
			    "- **Username:** %s%n" +
			    "- **Email Address:** %s%n" +
			    "- **Registration Date:** %s%n" +
			    "We are committed to providing you with the best experience and services. If you have any questions or need assistance, feel free to reach out to our support team.%n%n" +
			    "Thank you for choosing Online Banking System! We look forward to serving you and providing you with a great experience.%n%n" +
			    "Best regards,%n" +
			    "Online Banking System%n" +
			    "Street, Beaumont , Texas.%n" +
			    "+1 9999999999%n" +
			    "onlinebanking@example.com", fullName,userName, email, date
			);
		return EMAIL_REGISTRATION_CONTENT;
	}

	public static String forgotEmailUsernameRemember(User user) {
		String EMAIL_FORGOT_PASSWORD = String.format(
				"Dear %s,%n%n" +
				"We have received your request to reset your password for UserId : %s.%n" +
				"We're happy to assist you with this. Your password is as follows:%n%n" +
				"Password: %s%n%n"+
				"Thank you for using Online Banking. We are committed to ensuring the safety and security of your account.%n%n"+
				"Sincerely,%n"+
				"Online Banking System%n" +
			    "Street, Beaumont , Texas.%n" +
			    "+1 9999999999%n" +
			    "onlinebanking@example.com",
			    user.getFirstName()+" "+user.getLastName(),
			    user.getUserId(),
			    user.getPassword()
			);
		return EMAIL_FORGOT_PASSWORD;
	}
	

}

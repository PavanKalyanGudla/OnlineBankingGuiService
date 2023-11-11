package com.lamar.LamarOnlineBanking.Controller;

import java.io.IOException;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lamar.LamarOnlineBanking.Dao.DataAccess;
import com.lamar.LamarOnlineBanking.Model.Account;
import com.lamar.LamarOnlineBanking.Model.Address;
import com.lamar.LamarOnlineBanking.Model.AmountTransfer;
import com.lamar.LamarOnlineBanking.Model.Appointment;
import com.lamar.LamarOnlineBanking.Model.AppointmentTimeTable;
import com.lamar.LamarOnlineBanking.Model.AutoPayApps;
import com.lamar.LamarOnlineBanking.Model.DebitCard;
import com.lamar.LamarOnlineBanking.Model.DebitCardSchedule;
import com.lamar.LamarOnlineBanking.Model.Registration;
import com.lamar.LamarOnlineBanking.Model.ResponseObj;
import com.lamar.LamarOnlineBanking.Model.Transactions;
import com.lamar.LamarOnlineBanking.Model.User;
import com.lamar.LamarOnlineBanking.Service.OnlineBankingService;
import com.mysql.cj.util.StringUtils;

@RestController
@CrossOrigin
public class OnlineBankingController {

	private final DataAccess dao;
	
	@Autowired
	private final OnlineBankingService service;

	@Autowired
	public OnlineBankingController(DataAccess dao) {
		this.dao = dao;
		this.service = new OnlineBankingService();
	}

//	http://localhost:8080/login?userId=pgudla&password=1234
	@GetMapping("/login")
	public ResponseObj login(String userId,String password) {
		ResponseObj response = dao.getUser(userId,password);
		return response;
	}
	
	@PostMapping("/upload")
    public String uploadImage(String userId,String password,@RequestParam("file") MultipartFile file) {
        try {
        	System.out.println(userId);
        	System.out.println(file.getBytes());
        	ResponseObj response = dao.getUser(userId,password);
        	if(response.getResponseMessage().equals("SUCCESS") && response.getResponseCode().equals("200")) {
        		int updateProfilePic = dao.updateProfilePic(userId,password,file.getBytes());
        		return "Image uploaded successfully.";
        	}else {
        		return "Error uploading image.";
        	}            
        } catch (IOException e) {
            return "Error uploading image.";
        }
    }
	
//	http://localhost:8080/profilePic?userId=pgudla&password=1234
	@GetMapping("/profilePic")
    public ResponseEntity<byte[]> getImage(String userId,String password) {
    	ResponseObj response = dao.getUser(userId,password);
        if (response.getResponseMessage().equals("SUCCESS")) {
            byte[] imageData = response.getUser().getProfilePic();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/userRegistration")
    public String userRegistration(@RequestBody Registration registration){
    	String userRegistration = dao.userRegistration(registration.getUser(), registration.getAddress());
    	return userRegistration;
    }
    
    @PostMapping("/updateUserInfo")
    public String saveUserInfo(@RequestBody User userObj){
    	String response = dao.saveUserInfo(userObj);
		return response;
    }
    
//   http://localhost:8080/getUserAddress?userId=pgudla
    @GetMapping("/getUserAddress")
	public Address getUserAddress(String userId) {
    	Address userAddress = dao.getUserAddress(userId);
		return userAddress;
	}
    
//	http://localhost:8080/getAllUsers
	@GetMapping("/getAllUsers")
	public List<User> getAllUsers(){
		List<User> allUsers = dao.getAllUsers();
		return allUsers;
	}
	
	@GetMapping("/getZelleContactsOfUser")
	public List<User> getZelleContactsOfUser(){
		List<User> allUsers = dao.getAllUsers();
		return allUsers;
	}
	
	@GetMapping("/getHealthCheck")
	public String healthCheck() {
//		dao.getAllEntities();
		return "AllHealthy";
	}
    
//    http://localhost:8080/getUserAccountInfo?userId=pgudla
    @GetMapping("/getUserAccountInfo")
    public Account getUserAccountInfo(String userId) {
    	Account userAccountInfo = dao.getUserAccountInfo(userId);
    	return userAccountInfo;
    }
    
//    http://localhost:8080/getUserTransactionInfo?userId=pgudla
    @GetMapping("/getUserTransactionInfo")
    public List<Transactions> getUserTransactionsInfo(String userId){
    	List<Transactions> transactionsInfo = dao.getUserTransactionsInfo(userId);
		return transactionsInfo;
    }
    
//    http://localhost:8080/getDebitCard?userId=Aboyina&accountNumber=1234567892
    @GetMapping("/getDebitCard")
    public DebitCard getDebitCardUser(String userId,String accountNumber) {
    	DebitCard debitCardInfo = dao.getDebitCardInfo(userId,accountNumber);
    	return debitCardInfo;
    }
    
    @PostMapping("/scheduleDebitCard")
    public String scheduleDebitCard(@RequestBody DebitCard debitCard) {
    	String scheduleDebitCard = dao.scheduleDebitCard(debitCard);
    	return scheduleDebitCard;
    }
    
//    http://localhost:8080/getScheduleDebitCard?userId=Aboyina&cardNumber=111111111111
    @GetMapping("/getScheduleDebitCard")
    public List<DebitCardSchedule> getScheduleDebitCard(String userId, String cardNumber) {
    	List<DebitCardSchedule> cardScheduledInfo = dao.getCardScheduledInfoList(userId,cardNumber);
    	return cardScheduledInfo;
    }
    
    @DeleteMapping("/deleteCardSchedule")
    public String deleteDebitCardScheduled(String scheduledDay, String cardNumber) {
    	String response = dao.deleteDebitCardScheduled(scheduledDay, cardNumber);
    	return response;
    }
    
    @PostMapping("/updateCardStatus")
    public String updateCardStatus(@RequestBody DebitCard debitCard) {
    	String response = dao.updateUserAccountInfo(debitCard);
    	return response;
    }
    
    @PostMapping("/zelleTransaction")
    public String zelleTransaction(@RequestBody AmountTransfer zelleobject) {
    	String zelleTransaction = dao.zelleTransaction(zelleobject);
    	return zelleTransaction;
    }
    
    @PostMapping("/bookAppointment")
    public String bookAppointment(@RequestBody Appointment appointment) {
    	String response = dao.bookAppointment(appointment);
    	return response;
    }
    
    @GetMapping("/viewAppointment")
    public List<AppointmentTimeTable> getAppointment(){
    	List<AppointmentTimeTable> timeTable = dao.getViewAppointmentTimeTable();
    	return timeTable;
    }
    
    @GetMapping("/forgotPassword")
    public boolean forgotPassword(String userName) {
    	boolean flag = dao.getUser(userName);
    	return flag;
    }
    
    @GetMapping("/getAutoPay")
    public AutoPayApps getAutoPay(String userId){
    	AutoPayApps autoPay = dao.getAutoPay(userId);
    	return autoPay;
    }
    
    @PostMapping("/updateAutopay")
    public String updateAutoPay(@RequestBody AutoPayApps apps) {
    	String updateAutoPay = dao.updateAutoPay(apps);
    	return updateAutoPay;
    }
    
	
}
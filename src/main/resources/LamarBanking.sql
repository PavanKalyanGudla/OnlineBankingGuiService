create database LamarBanking;
use LamarBanking;
-- User table 
create table user(
	userId varchar(15),
    firstName varchar(15),
    lastName varchar(15),
    email varchar(30),
    mobile varchar(10),
    password varchar(20),
    address varchar(30),
    age int(3),
    profilePic longblob);
	
alter table user add dob date, add gender varchar(5), add maritialstatus varchar(10), add citizen varchar(20);
alter table user modify gender varchar(10);
alter table user modify firstName varchar(50);

alter table user modify email varchar(50);
ALTER TABLE user modify profilePic longblob;

ALTER TABLE user ADD PRIMARY KEY (userId);

select * from user;
-- update user set maritialStatus = "UnMarried" where userId != 'skollipara3';
desc user;

-- delete from user where userId = 'vkilaru1';
update user set mobile='4098672793' where userId = 'skollipara3';
insert into user(userId,firstName,lastName,email,mobile,password,address,age)
values('vkilaru','VenkataTirumala','Kilaru','kilaru@gmail.com','4094447313','1234','1280 Saxe st,beaumont',25);

insert into user(userId,firstName,lastName,email,mobile,password,address,age)
values('vkilaru1','VenkataTirumala','Kilaru','kilaru@gmail.com','4094447313','1234','1280 Saxe st,beaumont',25);

insert into user(userId,firstName,lastName,email,mobile,password,address,age)
values('skollipara3','sampath','kolli','kolli@gmail.com','4094447313','1234','1280 Saxe st,beaumont',25);

update user set dob='1998-11-22', gender='Male', maritialstatus = 'UnMarried' , citizen = 'Indian' 
where userId='skollipara3';

update user set maritialstatus = 'UnMarried' where userId='Aboyina';
update user set gender='Female' where userId='Aboyina';
-- Address table
CREATE TABLE Address (
    street varchar(15),
    building varchar(15),
    state varchar(15),
    country varchar(15),
    pincode varchar(10),
    userId varchar(15),
    FOREIGN KEY (userId) REFERENCES `user`(userId)
);
ALTER TABLE Address MODIFY country VARCHAR(50);
select * from Address;

desc Address;

insert into Address(street,building,state,country,pincode,userId)
values("Cochran Street","5045 Apt A","Texas","USA","77705","pgudla");

insert into Address(street,building,state,country,pincode,userId)
values("Cochran Street","5045 Apt A","Texas","USA","77705","skollipara3");

-- Account table

create table Account(
	userId varchar(15),
    accountNumber VARCHAR(10) PRIMARY KEY,
    accountType VARCHAR(50) NOT NULL,
    currentBalance DECIMAL(10, 2) NOT NULL,
    savingsBalance DECIMAL(10, 2) NOT NULL,
    dateOpened DATE NOT NULL,
    dateClosed DATE,
    accountStatus ENUM('Active', 'Closed') NOT NULL,
    FOREIGN KEY (userId) REFERENCES `user`(userId)
);
ALTER TABLE Account MODIFY accountNumber VARCHAR(50);
ALTER TABLE Address MODIFY pincode VARCHAR(15);
select * from Account;
update account set accountNumber='1234567892' where userId = 'Aboyina';
desc Account;

INSERT INTO Account (userId ,accountNumber, accountType, currentBalance, savingsBalance, dateOpened, dateClosed, accountStatus)
VALUES ('skollipara3','1234567892', 'Savings', 1000.00, 1000.00,'2023-10-01',null, 'Active');

-- Transactions table
CREATE TABLE Transactions (
	userId varchar(15),
    transactionId INT AUTO_INCREMENT PRIMARY KEY,
    accountNumber VARCHAR(10) NOT NULL,
    transactionDate TIMESTAMP NOT NULL,
    transactionType ENUM('Deposit', 'Withdrawal', 'Transfer', 'Zelle') NOT NULL,
    beforeAmount DECIMAL(10, 2) NOT NULL,
    amountTransfer DECIMAL(10, 2) NOT NULL,
    afterAmount DECIMAL(10, 2) NOT NULL,
    description varchar(15),
    sender varchar(15),
    receiver varchar(15),
    TransactionStatus ENUM('Success', 'Fail', 'Denied') NOT NULL,
    FOREIGN KEY (userId) REFERENCES user(userId),
    FOREIGN KEY (accountNumber) REFERENCES Account(accountNumber)
);
alter table Transactions modify accountNumber varchar(20);
ALTER TABLE Transactions MODIFY transactionType VARCHAR(20);

insert into Transactions(userId,accountNumber,transactionDate, transactionType,beforeAmount,amountTransfer,afterAmount,description,sender,receiver,TransactionStatus)
    values('skollipara3','1234567892','2023-4-10','Deposit',1000,200,1200,'cooldrink','Nagu','Sampath','Success');
	
insert into Transactions(userId,accountNumber,transactionDate, transactionType,beforeAmount,amountTransfer,afterAmount,description,sender,receiver,TransactionStatus)
    values('skollipara3','1234567892','2023-4-10','Deposit',1200,200,1400,'cooldrink','Nagu','Pavan','Success');

insert into Transactions(userId,accountNumber,transactionDate, transactionType,beforeAmount,amountTransfer,afterAmount,description,sender,receiver,TransactionStatus)
    values('PGudla','ACCT0080810732','2023-4-10','Deposit',1200,200,1400,'cooldrink','PGudla','skollipara3','Success');

update Transactions set sender = 'skollipara3' where userId='skollipara3'; 
update Transactions set receiver = 'vkilaru' where userId='skollipara3' and transactionId=2; 

select * from Transactions;
update Transactions set transactionType="add" where userId='skollipara3' and transactionId = 1;
select * from Transactions where userId = 'skollipara3' and receiver='vkilaru' order by transactionId;

-- Branch table

create table Branch (
	branchId varchar(50) PRIMARY KEY,
	branchName varchar(50),
	branchCode varchar(50), 
    branchAddress varchar(50), 
    contactNumber varchar(50)
);
-- Debit card

create table DebitCard(
	userId varchar(15),
    cardNumber varchar(50) PRIMARY KEY,
    accountNumber VARCHAR(10),
    cvv varchar(5),
    FOREIGN KEY (userId) REFERENCES user(userId),
    FOREIGN KEY (accountNumber) REFERENCES Account(accountNumber)
);
alter table DebitCard add cardStatus boolean;
-- Debit card Schedule

create table DebitCardSchedule(
	userId varchar(15),
    cardNumber varchar(50),
    scheduled varchar(15),
    fromDate varchar(10),
    toDate varchar(10),
    FOREIGN KEY (userId) REFERENCES user(userId),
    FOREIGN KEY (cardNumber) REFERENCES debitcard(cardNumber)
);
select * from debitcard;
desc debitcard;
ALTER TABLE debitcard MODIFY accountNumber VARCHAR(50);
ALTER TABLE DebitCardSchedule modify fromDate varchar(10);
ALTER TABLE DebitCardSchedule modify toDate varchar(10);
delete from debitcard where userId = 'Aboyina';
update debitcard set cardStatus = true where userid = 'PGudla';
insert into debitcard(userId,cardNumber,accountNumber,cvv) values('skollipara3','111111111111','1234567892','123');
-- insert into DebitCardSchedule(userId,cardNumber,scheduled,fromDate,toDate)values 
select * from DebitCardSchedule;
-- truncate table DebitCardSchedule;
desc DebitCardSchedule;
INSERT INTO DebitCardSchedule (userId, cardNumber, scheduled, fromDate, toDate)
VALUES ('skollipara3', '111111111111', 'Monday', '01:00', '23:54');
delete from user where userId = 'PGudla';
--
CREATE TABLE appointmentTimeTable (
    date DATE PRIMARY KEY,
    slot1 VARCHAR(50),
    slot2 VARCHAR(50),
    slot3 VARCHAR(50),
    slot4 VARCHAR(50),
    slot5 VARCHAR(50),
    slot6 VARCHAR(50),
    slot7 VARCHAR(50),
    slot8 VARCHAR(50),
    slot9 VARCHAR(50),
    slot10 VARCHAR(50),
    slot11 VARCHAR(50),
    slot12 VARCHAR(50)
);

CREATE TABLE bookingTable (
    bookingId VARCHAR(50) PRIMARY KEY ,
    fullName VARCHAR(100),
    phoneNumber VARCHAR(20),
    email VARCHAR(100),
    date DATE,
    time VARCHAR(50),
    locality VARCHAR(50),
	city VARCHAR(50),
	state VARCHAR(50),
	pinCode VARCHAR(50),
    FOREIGN KEY (date) REFERENCES appointmentTimeTable(date)
);

select * from bookingTable;
select * from appointmentTimeTable;

CREATE TABLE autopay (
    autopay_id INT AUTO_INCREMENT PRIMARY KEY,
    userId varchar(15),
    customer_name VARCHAR(255) NOT NULL,
    account_number VARCHAR(20) NOT NULL,
    payment_amount DECIMAL(10, 2) NOT NULL,
    payment_frequency VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    account_type VARCHAR(20),
    netflix BOOLEAN DEFAULT false,
    hulu BOOLEAN DEFAULT false,
    prime BOOLEAN DEFAULT false,
    appleTv BOOLEAN DEFAULT false,
    disneyStar BOOLEAN DEFAULT false,
    youtubeTv BOOLEAN DEFAULT false,
    FOREIGN KEY (userId) REFERENCES user(userId)
);

desc autopay;
select * from autopay;
INSERT INTO autopay (userId, customer_name, account_number, payment_amount, payment_frequency, start_date, account_type, netflix, hulu, prime)
VALUES ('PGudla', 'Pavan kalyan', 'ACCT0080810732', 25.00, 'Monthly', '2023-02-01', 'Checking', true, true, true);

--

create table autopayapps(
	app_id INT AUTO_INCREMENT PRIMARY KEY,
    app_name varchar(50),
    amount DECIMAL(10, 2) NOT NULL,
    date_of_pay date
);

select * from autopayapps;
insert into autopayapps(app_name,amount,date_of_pay) values("Netflix",20,curdate()-2);
insert into autopayapps(app_name,amount,date_of_pay) values("Prime",20,curdate()-2);
insert into autopayapps(app_name,amount,date_of_pay) values("Hulu",20,curdate()-2);
insert into autopayapps(app_name,amount,date_of_pay) values("AppleTv",20,curdate()-2);
insert into autopayapps(app_name,amount,date_of_pay) values("DisneyTv",20,curdate()-2);
insert into autopayapps(app_name,amount,date_of_pay) values("youtubeTv",20,curdate()-2);


-- INSERT INTO appointmentTimeTable (date, slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, slot10, slot11, slot12)
-- VALUES (CURDATE()+4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
commit;
show tables;

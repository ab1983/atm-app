package com.jnj.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jnj.codingchallenge.AppTestConfig;
import com.jnj.codingchallenge.bo.BalanceBo;
import com.jnj.codingchallenge.bo.WithdrawalBo;
import com.jnj.codingchallenge.exception.AtmException;
import com.jnj.codingchallenge.model.Account;
import com.jnj.codingchallenge.model.Note;
import com.jnj.codingchallenge.service.AtmService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={AppTestConfig.class})
@TestPropertySource(
		  locations = "classpath:applicationTest.properties")
@ActiveProfiles("test")
@Transactional
public class DemoApplicationTests {

	@Autowired
	private AtmService atmService;
	
	private String accountNumber1 = "123456789";
	private String pin1 = "1234";
	
	private String accountNumber2 = "987654321";
	private String pin2 = "4321";	
	
	@Before
	public void contextLoads() {
		List<Note> notes = new ArrayList<>();
		notes.add(new Note("EURO", 50, 20));
		notes.add(new Note("EURO", 20, 30));
		notes.add(new Note("EURO", 10, 30));
		notes.add(new Note("EURO", 5, 20));
		atmService.saveNotes(notes);
		Account account1 = new Account();
		account1.setAccountNumber("123456789");
		account1.setPin("1234");
		account1.setOpeningBalance(BigDecimal.valueOf(800.0));
		account1.setOverDraft(BigDecimal.valueOf(200.0));
		atmService.saveAccount(account1);
		Account account2 = new Account();
		account2.setAccountNumber("987654321");
		account2.setPin("4321");
		account2.setOpeningBalance(BigDecimal.valueOf(1230.0));
		account2.setOverDraft(BigDecimal.valueOf(150.0));
		atmService.saveAccount(account2);
	}
	
	@Test
	public void testGetBalance(){
		try {
			BalanceBo balance = atmService.getBalance(accountNumber1, pin1);
			System.out.println(balance);
		} catch (AtmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} 
	}
	
	@Test
	public void testGetBalanceWrongPin(){
		try {
			BalanceBo balance = atmService.getBalance(accountNumber1, "0000");
			System.out.println(balance);
			fail();
		} catch (AtmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} 
	}	
	
	@Test
	public void testWithdraw(){
		try {
			WithdrawalBo withdrawalBo = atmService.withdraw(accountNumber1, pin1, BigDecimal.valueOf(120));
			System.out.println(withdrawalBo);
			assertEquals(withdrawalBo.getAmountWithdrawal().doubleValue(), 120d, 0);
		} catch (AtmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} 
	}

	
	@Test
	public void testWithdraw2AndBalance(){
		try {
			WithdrawalBo withdrawalBo = atmService.withdraw(accountNumber1, pin1, BigDecimal.valueOf(90));
			System.out.println(withdrawalBo);
			assertEquals(90d, withdrawalBo.getAmountWithdrawal().doubleValue(), 0);
			
			WithdrawalBo withdrawalBo2 = atmService.withdraw(accountNumber1, pin1, BigDecimal.valueOf(110));
			System.out.println(withdrawalBo2);
			assertEquals(110d, withdrawalBo2.getAmountWithdrawal().doubleValue(), 0);		
			
			BalanceBo balance = atmService.getBalance(accountNumber1, pin1);
			System.out.println(balance);
			assertEquals(600d, balance.getBalance().doubleValue(), 0);
		} catch (AtmException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} 
	}	

	@Test
	public void testWithdraw2GreaterThanAtmAvailability(){
		try {
			WithdrawalBo withdrawalBo = atmService.withdraw(accountNumber1, pin1, BigDecimal.valueOf(800));
			System.out.println(withdrawalBo);
			assertEquals(800d, withdrawalBo.getAmountWithdrawal().doubleValue(), 0);
			
			WithdrawalBo withdrawalBo2 = atmService.withdraw(accountNumber2, pin2, BigDecimal.valueOf(1230));
			System.out.println(withdrawalBo2);
			assertEquals(1200d, withdrawalBo2.getAmountWithdrawal().doubleValue(), 0);		
			fail("The last withdrawal should throw an exception due to the limit available in the ATM.");
		} catch (AtmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} 
	}		
	
	@Test
	public void testWithdrawalGreaterThanBalance(){
		try {
			WithdrawalBo withdrawalBo = atmService.withdraw(accountNumber1, pin1, BigDecimal.valueOf(900));
			System.out.println(withdrawalBo);
			fail();
		} catch (AtmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} 
	}
	
	@Test
	public void testWithdrawalWrongPin(){
		try {
			WithdrawalBo withdrawalBo = atmService.withdraw(accountNumber1, "0000", BigDecimal.valueOf(900));
			System.out.println(withdrawalBo);
			fail();
		} catch (AtmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} 
	}	
}

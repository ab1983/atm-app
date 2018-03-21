package com.jnj.codingchallenge.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jnj.codingchallenge.bo.BalanceBo;
import com.jnj.codingchallenge.bo.NoteBo;
import com.jnj.codingchallenge.bo.WithdrawalBo;
import com.jnj.codingchallenge.dao.AccountDao;
import com.jnj.codingchallenge.dao.NoteDao;
import com.jnj.codingchallenge.exception.AtmException;
import com.jnj.codingchallenge.model.Account;
import com.jnj.codingchallenge.model.Note;
import com.jnj.codingchallenge.model.pk.NotePk;

@Service
public class AtmService {
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private NoteDao noteDao;
	
	/**
	 * This method obtain the balance and the maximum amount available to withdraw.
	 * @param accountNumber
	 * @param pin
	 * @return BalanceBo 
	 * @throws AtmException
	 */
	public BalanceBo getBalance(String accountNumber, String pin) throws AtmException{
		BalanceBo balanceBo = new BalanceBo();
		Account account = getAccount(accountNumber, pin);
		WithdrawalBo maxWithdrawal = getMaxWithdrawal(account.getOpeningBalance());
		balanceBo.setBalance(account.getOpeningBalance());
		balanceBo.setMaxWithdrawal(maxWithdrawal.getAmountWithdrawal());
		return balanceBo;
	}
	
	/**
	 * This method calculates the amount allowed to withdraw.
	 * @param amountRequested
	 * @return A WithdrawalBo object containing amountWithdrawal, remainBalance and notes
	 */
	private WithdrawalBo getMaxWithdrawal(BigDecimal amountRequested){
		WithdrawalBo withdrawalBo =  new WithdrawalBo();
		List<NoteBo> notesBo = new ArrayList<>();
		withdrawalBo.setNotes(notesBo);
		BigDecimal maxWithdrawal = BigDecimal.valueOf(0);
		
		List<Note> notes = new ArrayList<Note>();
		noteDao.findAll().forEach(n->notes.add(n));
		/*
		 * Sorting values notes by descending order to assure that
		 * the ATM dispenses the minimum number of notes per withdrawal
		 */
		notes.sort((Note e1, Note e2) -> e2.getValue().compareTo(e1.getValue()));
		
		BigDecimal valuePending = amountRequested;
		for (Iterator iterator = notes.iterator(); iterator.hasNext();) {
			Note note = (Note) iterator.next();
			NoteBo noteBo = new NoteBo();
			BigDecimal noteValue = BigDecimal.valueOf(note.getValue());
			Integer numberOfNotesToWithdraw = valuePending.divide(noteValue).intValue()>note.getNumberOfNotes()?note.getNumberOfNotes():valuePending.divide(noteValue).intValue();
			if(numberOfNotesToWithdraw > 0){
				noteBo.setNumberOfNotes(numberOfNotesToWithdraw);
				noteBo.setValue(note.getValue());
				notesBo.add(noteBo);	
				BigDecimal totalNotesValue = noteValue.multiply(BigDecimal.valueOf(numberOfNotesToWithdraw));
				maxWithdrawal = maxWithdrawal.add(totalNotesValue);
				valuePending = valuePending.subtract(totalNotesValue);
			}
		}
		withdrawalBo.setAmountWithdrawal(maxWithdrawal);
		return withdrawalBo;
	}
	
	/**
	 * This method consults account and validates PIN to assure that the ATM do not 
	 * dispense funds and not expose the customer balance if the pin is incorrect.
	 * @param accountNumber
	 * @param pin
	 * @return
	 * @throws AtmException
	 */
	private Account getAccount(String accountNumber, String pin) throws AtmException{
		Optional<Account> accounts = accountDao.findById(accountNumber);
		accounts = accounts.filter(a ->a.getPin().equals(pin));
		if(!accounts.isPresent()){
			throw new AtmException("Account or PIN is invalid.");		
		}
		Account account = accounts.get();
		return account;
	}
	
	/**
	 * This method is responsible for subtracting the amount withdrawn from the account and the notes
	 * stored.
	 * @param accountNumber
	 * @param pin
	 * @param amount
	 * @return A WithdrawalBo object containing amountWithdrawal, remainBalance and notes
	 * @throws AtmException
	 */
	public WithdrawalBo withdraw(String accountNumber, String pin, BigDecimal amount) throws AtmException{
		Account account = getAccount(accountNumber, pin);
		/*
		 * The customer cannot withdraw more funds then they have access to.
		 */
		if(amount.compareTo(account.getOpeningBalance())>0){
			throw new AtmException("Amount requested is greater than balance available.");
		}
		WithdrawalBo withdrawalBo = getMaxWithdrawal(amount);
		
		if(withdrawalBo.getAmountWithdrawal().compareTo(BigDecimal.ZERO)==0){
			throw new AtmException("There are no notes available to this transaction.");
		}		
		/*
		 * The ATM cannot dispense more money than it holds. 
		 */
		if(amount.compareTo(withdrawalBo.getAmountWithdrawal())>0){
			throw new AtmException("The limit available in the ATM for this transaction is "+withdrawalBo.getAmountWithdrawal());
		}
		withdrawalBo.setRemainBalance(account.getOpeningBalance().subtract(withdrawalBo.getAmountWithdrawal()));
		try {
			updateTransaction(withdrawalBo, account);
		} catch (Exception e) {
			throw new AtmException("Transaction conclusion error.",e);
		}
		return withdrawalBo;
		
	}	
	
	@Transactional
	private void updateTransaction(WithdrawalBo withdrawalBo, Account account){
		updateNotes(withdrawalBo.getNotes());
		updateAccount(withdrawalBo, account);
	}
	private void updateNotes(List<NoteBo> notesBo){
		for (NoteBo noteBo : notesBo) {
			Note note = noteDao.findById(new NotePk("EURO", noteBo.getValue())).get();
			note.setNumberOfNotes(note.getNumberOfNotes()-noteBo.getNumberOfNotes());
			noteDao.save(note);
		}
	}
	
	private void updateAccount(WithdrawalBo withdrawalBo, Account account){
		account.setOpeningBalance(withdrawalBo.getRemainBalance());
		accountDao.save(account);
	}

	public List<Note> getNotes(){
		List<Note> notes = new ArrayList<Note>();
		return notes;
	}
	
	/**
	 * Save notes using the same transaction to keep integrity
	 * @param notes
	 */
	@Transactional
	public void saveNotes(List<Note> notes){
		for (Note note : notes) {
			noteDao.save(note);
		}
	}

	public void saveAccount(Account account1) {
		accountDao.save(account1);
		
	}	
	
	/**
	 * This method is to set initial values into the database
	 */
	public void populateDatabase(){
		List<Note> notes = new ArrayList<>();
		notes.add(new Note("EURO", 50, 20));
		notes.add(new Note("EURO", 20, 30));
		notes.add(new Note("EURO", 10, 30));
		notes.add(new Note("EURO", 5, 20));
		saveNotes(notes);
		Account account1 = new Account();
		account1.setAccountNumber("123456789");
		account1.setPin("1234");
		account1.setOpeningBalance(BigDecimal.valueOf(800.0));
		account1.setOverDraft(BigDecimal.valueOf(200.0));
		saveAccount(account1);
		Account account2 = new Account();
		account2.setAccountNumber("987654321");
		account2.setPin("4321");
		account2.setOpeningBalance(BigDecimal.valueOf(1230.0));
		account2.setOverDraft(BigDecimal.valueOf(150.0));
		saveAccount(account2);
	}
}

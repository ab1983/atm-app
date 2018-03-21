package com.jnj.codingchallenge.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {
	
	@Id
	private String accountNumber;
	private String pin;
	private BigDecimal openingBalance;
	private BigDecimal overDraft;
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}
	public BigDecimal getOverDraft() {
		return overDraft;
	}
	public void setOverDraft(BigDecimal overDraft) {
		this.overDraft = overDraft;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((openingBalance == null) ? 0 : openingBalance.hashCode());
		result = prime * result + ((overDraft == null) ? 0 : overDraft.hashCode());
		result = prime * result + ((pin == null) ? 0 : pin.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (openingBalance == null) {
			if (other.openingBalance != null)
				return false;
		} else if (!openingBalance.equals(other.openingBalance))
			return false;
		if (overDraft == null) {
			if (other.overDraft != null)
				return false;
		} else if (!overDraft.equals(other.overDraft))
			return false;
		if (pin == null) {
			if (other.pin != null)
				return false;
		} else if (!pin.equals(other.pin))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", pin=" + pin + ", openingBalance=" + openingBalance
				+ ", overDraft=" + overDraft + "]";
	}
	
	
}

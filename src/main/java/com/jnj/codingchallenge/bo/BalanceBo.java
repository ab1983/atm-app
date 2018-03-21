package com.jnj.codingchallenge.bo;

import java.math.BigDecimal;

public class BalanceBo {
	private BigDecimal balance;
	private BigDecimal maxWithdrawal;
	
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getMaxWithdrawal() {
		return maxWithdrawal;
	}
	public void setMaxWithdrawal(BigDecimal maxWithdrawal) {
		this.maxWithdrawal = maxWithdrawal;
	}
	
	
}

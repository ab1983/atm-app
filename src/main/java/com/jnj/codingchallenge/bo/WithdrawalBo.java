package com.jnj.codingchallenge.bo;

import java.math.BigDecimal;
import java.util.List;

public class WithdrawalBo {

	private BigDecimal amountWithdrawal;
	private BigDecimal remainBalance;
	private List<NoteBo> notes;
	public BigDecimal getAmountWithdrawal() {
		return amountWithdrawal;
	}
	public void setAmountWithdrawal(BigDecimal amountWithdrawal) {
		this.amountWithdrawal = amountWithdrawal;
	}
	public BigDecimal getRemainBalance() {
		return remainBalance;
	}
	public void setRemainBalance(BigDecimal remainBalance) {
		this.remainBalance = remainBalance;
	}
	public List<NoteBo> getNotes() {
		return notes;
	}
	public void setNotes(List<NoteBo> notes) {
		this.notes = notes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amountWithdrawal == null) ? 0 : amountWithdrawal.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((remainBalance == null) ? 0 : remainBalance.hashCode());
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
		WithdrawalBo other = (WithdrawalBo) obj;
		if (amountWithdrawal == null) {
			if (other.amountWithdrawal != null)
				return false;
		} else if (!amountWithdrawal.equals(other.amountWithdrawal))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (remainBalance == null) {
			if (other.remainBalance != null)
				return false;
		} else if (!remainBalance.equals(other.remainBalance))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WithdrawalBo [amountWithdrawal=" + amountWithdrawal + ", remainBalance=" + remainBalance + ", notes="
				+ notes + "]";
	} 
	
	
	
}

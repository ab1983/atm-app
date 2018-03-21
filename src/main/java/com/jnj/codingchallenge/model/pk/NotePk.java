package com.jnj.codingchallenge.model.pk;

import java.io.Serializable;

public class NotePk implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1110731195575584357L;
	private String currency;
	private Integer value;
	
	public NotePk() {
		super();
	}

	public NotePk(String currency, Integer value) {
		super();
		this.currency = currency;
		this.value = value;
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		NotePk other = (NotePk) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NotePk [currency=" + currency + ", value=" + value + "]";
	}
	
	
}

package com.jnj.codingchallenge.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.jnj.codingchallenge.model.pk.NotePk;

@Entity
@IdClass(value = NotePk.class)
public class Note {
	@Id
	private String currency;
	@Id
	private Integer value;
	private Integer numberOfNotes;
	public String getCurrency() {
		return currency;
	}
	
	public Note(String currency, Integer value, Integer numberOfNotes) {
		super();
		this.currency = currency;
		this.value = value;
		this.numberOfNotes = numberOfNotes;
	}
	
	public Note() {
		super();
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
	public Integer getNumberOfNotes() {
		return numberOfNotes;
	}
	public void setNumberOfNotes(Integer numberOfNotes) {
		this.numberOfNotes = numberOfNotes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((numberOfNotes == null) ? 0 : numberOfNotes.hashCode());
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
		Note other = (Note) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (numberOfNotes == null) {
			if (other.numberOfNotes != null)
				return false;
		} else if (!numberOfNotes.equals(other.numberOfNotes))
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
		return "Note [currency=" + currency + ", value=" + value + ", numberOfNotes=" + numberOfNotes + "]";
	}
	
	
	
}

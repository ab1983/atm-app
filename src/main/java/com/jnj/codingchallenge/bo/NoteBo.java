package com.jnj.codingchallenge.bo;

public class NoteBo {
	private Integer value;
	private Integer numberOfNotes;
	
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
		NoteBo other = (NoteBo) obj;
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
		return "NoteBo [value=" + value + ", numberOfNotes=" + numberOfNotes + "]";
	}
	
	
}

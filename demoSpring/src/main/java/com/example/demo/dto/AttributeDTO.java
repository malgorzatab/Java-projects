package com.example.demo.dto;

public class AttributeDTO
{
	private int attributeID;
	
	private String question;
	
	private int ordinal;
	
	private String attributeType;
	
	private String answer;

	public AttributeDTO(int attributeID, String question, int ordinal, String attributeType, String answer) {
		this.attributeID = attributeID;
		this.question = question;
		this.ordinal = ordinal;
		this.attributeType = attributeType;
		this.answer = answer;
	}

	public int getAttributeID() {
		return attributeID;
	}

	public void setAttributeID(int attributeID) {
		this.attributeID = attributeID;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}

package com.example.demo.dto;

import java.util.List;

public class FormDTO
{
	private int formID;

	private String formName;

	private String version;
	
	private List<AttributeDTO> fields;
	
	public int getFormID() {
		return formID;
	}

	public void setFormID(int formID) {
		this.formID = formID;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<AttributeDTO> getFields() {
		return fields;
	}

	public void setFields(List<AttributeDTO> fields) {
		this.fields = fields;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public FormDTO(int formID, String formName, String version, List<AttributeDTO> fields) {
		this.formID = formID;
		this.formName = formName;
		this.version = version;
		this.fields = fields;
	}

	public FormDTO(int formID, String version, List<AttributeDTO> fields) {
		this.formID = formID;
		this.version = version;
		this.fields = fields;
	}
}

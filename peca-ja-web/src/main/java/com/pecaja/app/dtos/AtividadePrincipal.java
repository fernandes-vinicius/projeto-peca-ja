package com.pecaja.app.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AtividadePrincipal {

	@SerializedName("text")
	@Expose
	private String text;
	@SerializedName("code")
	@Expose
	private String code;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

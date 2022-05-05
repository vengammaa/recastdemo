package com.lti.recast.web.model;

import java.util.Date;

public class PasswordResetTokenModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private UserModel user;

	private String token;

	private Date expiryDate;

	private boolean pwChanged;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserModel getUser() {
		return this.user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isPwChanged() {
		return this.pwChanged;
	}

	public void setPwChanged(boolean pwChanged) {
		this.pwChanged = pwChanged;
	}

	@Override
	public String toString() {
		return "PasswordResetTokenModel [id=" + id + ", user=" + user + ", token=" + token + ", expiryDate="
				+ expiryDate + ", pwChanged=" + pwChanged + "]";
	}

}

package com.example.police.models;

public class PeopleInfo {
	private Integer id = 0;
	private String userName = "";
	private String userPasswd = "";
	private Integer userStatus = 0;

	public PeopleInfo() {
	}

	public PeopleInfo(int id, String userName, String userPasswd,
			Integer userStatus) {
		this.id = id;
		this.userName = userName;
		this.userPasswd = userPasswd;
		this.userStatus = userStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return userName;
	}

	public void setName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return userPasswd;
	}

	public void setPrice(String userPasswd) {
		this.userPasswd = userPasswd;
	}

	public Integer getStatus() {
		return userStatus;
	}

	public void setStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

}
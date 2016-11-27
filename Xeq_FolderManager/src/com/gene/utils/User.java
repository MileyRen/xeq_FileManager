package com.gene.utils;

import java.sql.Timestamp;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userName;
	private String passWord;
	private String email;
	private Integer roleId;
	private Integer isAvailable;
	private Timestamp registerTime;
	private String folder;
	private Float initStorge;
	private Float usedStorage;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String userName, String passWord, String email) {
		this.userName = userName;
		this.passWord = passWord;
		this.email = email;
	}

	/** full constructor */
	public User(String userName, String passWord, String email, Integer roleId,
			Integer isAvailable, Timestamp registerTime, String folder,
			Float initStorge, Float usedStorage) {
		this.userName = userName;
		this.passWord = passWord;
		this.email = email;
		this.roleId = roleId;
		this.isAvailable = isAvailable;
		this.registerTime = registerTime;
		this.folder = folder;
		this.initStorge = initStorge;
		this.usedStorage = usedStorage;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return this.passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Timestamp getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public String getFolder() {
		return this.folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public Float getInitStorge() {
		return this.initStorge;
	}

	public void setInitStorge(Float initStorge) {
		this.initStorge = initStorge;
	}

	public Float getUsedStorage() {
		return this.usedStorage;
	}

	public void setUsedStorage(Float usedStorage) {
		this.usedStorage = usedStorage;
	}

}
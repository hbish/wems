package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;

	private String contactNumber;

	private String email;

	private String firstName;

	private boolean isTempPass;

	private String lastName;

	private int lockedOut;

	private int loginAttempts;

	private String passHash;

	private String username;

	//bi-directional many-to-one association to UserGroup
    @ManyToOne
	@JoinColumn(name="userGroup")
	private UserGroup userGroupBean;

	//bi-directional many-to-one association to UserSession
    @ManyToOne
	@JoinColumn(name="sessionId")
	private UserSession userSession;

	//bi-directional many-to-one association to UserType
    @ManyToOne
	@JoinColumn(name="userTypeId")
	private UserType userType;

    public User() {
    }

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean getIsTempPass() {
		return this.isTempPass;
	}

	public void setIsTempPass(boolean isTempPass) {
		this.isTempPass = isTempPass;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getLockedOut() {
		return this.lockedOut;
	}

	public void setLockedOut(int lockedOut) {
		this.lockedOut = lockedOut;
	}

	public int getLoginAttempts() {
		return this.loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public String getPassHash() {
		return this.passHash;
	}

	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserGroup getUserGroupBean() {
		return this.userGroupBean;
	}

	public void setUserGroupBean(UserGroup userGroupBean) {
		this.userGroupBean = userGroupBean;
	}
	
	public UserSession getUserSession() {
		return this.userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
	
	public UserType getUserType() {
		return this.userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
}
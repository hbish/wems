package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the user_session database table.
 * 
 */
@Entity
@Table(name="user_session")
public class UserSession implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int sessionId;

	private String ipAddress;

    @Temporal( TemporalType.TIMESTAMP)
	private Date loggedInTime;

	private int sessionStatus;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="userSession")
	private Set<User> users;

    public UserSession() {
    }

	public int getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getLoggedInTime() {
		return this.loggedInTime;
	}

	public void setLoggedInTime(Date loggedInTime) {
		this.loggedInTime = loggedInTime;
	}

	public int getSessionStatus() {
		return this.sessionStatus;
	}

	public void setSessionStatus(int sessionStatus) {
		this.sessionStatus = sessionStatus;
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}
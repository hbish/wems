package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the user_group database table.
 * 
 */
@Entity
@Table(name="user_group")
public class UserGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userGroupId;

	private String userGroupDescription;

	private String userGroupEmail;

	private String userGroupName;

	//bi-directional many-to-one association to AlertSetting
	@OneToMany(mappedBy="userGroup")
	private Set<AlertSetting> alertSettings;

	//bi-directional many-to-one association to SensordataAddress
	@OneToMany(mappedBy="userGroupBean")
	private Set<SensordataAddress> sensordataAddresses;

	//bi-directional many-to-one association to SensordataDevice
	@OneToMany(mappedBy="userGroupBean")
	private Set<SensordataDevice> sensordataDevices;

	//bi-directional many-to-one association to SensordataRoom
	@OneToMany(mappedBy="userGroupBean")
	private Set<SensordataRoom> sensordataRooms;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="userGroupBean")
	private Set<User> users;

    public UserGroup() {
    }

	public int getUserGroupId() {
		return this.userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getUserGroupDescription() {
		return this.userGroupDescription;
	}

	public void setUserGroupDescription(String userGroupDescription) {
		this.userGroupDescription = userGroupDescription;
	}

	public String getUserGroupEmail() {
		return this.userGroupEmail;
	}

	public void setUserGroupEmail(String userGroupEmail) {
		this.userGroupEmail = userGroupEmail;
	}

	public String getUserGroupName() {
		return this.userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public Set<AlertSetting> getAlertSettings() {
		return this.alertSettings;
	}

	public void setAlertSettings(Set<AlertSetting> alertSettings) {
		this.alertSettings = alertSettings;
	}
	
	public Set<SensordataAddress> getSensordataAddresses() {
		return this.sensordataAddresses;
	}

	public void setSensordataAddresses(Set<SensordataAddress> sensordataAddresses) {
		this.sensordataAddresses = sensordataAddresses;
	}
	
	public Set<SensordataDevice> getSensordataDevices() {
		return this.sensordataDevices;
	}

	public void setSensordataDevices(Set<SensordataDevice> sensordataDevices) {
		this.sensordataDevices = sensordataDevices;
	}
	
	public Set<SensordataRoom> getSensordataRooms() {
		return this.sensordataRooms;
	}

	public void setSensordataRooms(Set<SensordataRoom> sensordataRooms) {
		this.sensordataRooms = sensordataRooms;
	}
	
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}
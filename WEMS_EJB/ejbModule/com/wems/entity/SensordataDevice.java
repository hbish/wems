package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;

/**
 * The persistent class for the sensordata_device database table.
 * 
 */
@Entity
@Table(name = "sensordata_device")
public class SensordataDevice implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int deviceuid;

  private String brand;

  private boolean connected;

  private int id;

  private String macaddress;

  private String model;

  private String serial;

  private String type;

  // bi-directional many-to-one association to EventsLog
  @OneToMany(mappedBy = "sensordataDevice")
  private Set<EventsLog> eventsLogs;

  // bi-directional many-to-one association to SensordataRoom
  @ManyToOne
  @JoinColumn(name = "roomuid")
  private SensordataRoom sensordataRoom;

  // bi-directional many-to-one association to UserGroup
  @ManyToOne
  @JoinColumn(name = "userGroup")
  private UserGroup userGroupBean;

  // bi-directional many-to-one association to SensordataDeviceusage
  @OneToMany(mappedBy = "sensordataDevice")
  private Set<SensordataDeviceusage> sensordataDeviceusages;

  // bi-directional many-to-one association to SensordataParameter
  @OneToMany(mappedBy = "sensordataDevice")
  private Set<SensordataParameter> sensordataParameters;

  public SensordataDevice() {
  }

  public int getDeviceuid() {
    return this.deviceuid;
  }

  public void setDeviceuid(int deviceuid) {
    this.deviceuid = deviceuid;
  }

  public String getBrand() {
    return this.brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public boolean getConnected() {
    return this.connected;
  }

  public void setConnected(boolean connected) {
    this.connected = connected;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMacaddress() {
    return this.macaddress;
  }

  public void setMacaddress(String macaddress) {
    this.macaddress = macaddress;
  }

  public String getModel() {
    return this.model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getSerial() {
    return this.serial;
  }

  public void setSerial(String serial) {
    this.serial = serial;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Set<EventsLog> getEventsLogs() {
    return this.eventsLogs;
  }

  public void setEventsLogs(Set<EventsLog> eventsLogs) {
    this.eventsLogs = eventsLogs;
  }

  public SensordataRoom getSensordataRoom() {
    return this.sensordataRoom;
  }

  public void setSensordataRoom(SensordataRoom sensordataRoom) {
    this.sensordataRoom = sensordataRoom;
  }

  public UserGroup getUserGroupBean() {
    return this.userGroupBean;
  }

  public void setUserGroupBean(UserGroup userGroupBean) {
    this.userGroupBean = userGroupBean;
  }

  public Set<SensordataDeviceusage> getSensordataDeviceusages() {
    return this.sensordataDeviceusages;
  }

  public void setSensordataDeviceusages(Set<SensordataDeviceusage> sensordataDeviceusages) {
    this.sensordataDeviceusages = sensordataDeviceusages;
  }

  public Set<SensordataParameter> getSensordataParameters() {
    return this.sensordataParameters;
  }

  public void setSensordataParameters(Set<SensordataParameter> sensordataParameters) {
    this.sensordataParameters = sensordataParameters;
  }

}
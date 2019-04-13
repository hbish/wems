package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Set;

/**
 * The persistent class for the sensordata_room database table.
 * 
 */
@Entity
@Table(name = "sensordata_room")
public class SensordataRoom implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int roomuid;

  private boolean connected;

  private String id;

  private String name;

  private boolean powerstatus;

  // bi-directional many-to-one association to SensordataDevice
  @OneToMany(mappedBy = "sensordataRoom")
  private Set<SensordataDevice> sensordataDevices;

  // bi-directional many-to-one association to SensordataPowerusage
  @OneToMany(mappedBy = "sensordataRoom")
  private Set<SensordataPowerusage> sensordataPowerusages;

  // bi-directional many-to-one association to SensordataAddress
  @ManyToOne
  @JoinColumn(name = "addressuid")
  private SensordataAddress sensordataAddress;

  // bi-directional many-to-one association to UserGroup
  @ManyToOne
  @JoinColumn(name = "userGroup")
  private UserGroup userGroupBean;

  // bi-directional many-to-one association to EventsLog
  @OneToMany(mappedBy = "sensordataRoom")
  private Set<EventsLog> eventsLogs;

  public SensordataRoom() {
  }

  public int getRoomuid() {
    return this.roomuid;
  }

  public void setRoomuid(int roomuid) {
    this.roomuid = roomuid;
  }

  public boolean getConnected() {
    return this.connected;
  }

  public void setConnected(boolean connected) {
    this.connected = connected;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getPowerstatus() {
    return this.powerstatus;
  }

  public void setPowerstatus(boolean powerstatus) {
    this.powerstatus = powerstatus;
  }

  public Set<SensordataDevice> getSensordataDevices() {
    return this.sensordataDevices;
  }

  public void setSensordataDevices(Set<SensordataDevice> sensordataDevices) {
    this.sensordataDevices = sensordataDevices;
  }

  public Set<SensordataPowerusage> getSensordataPowerusages() {
    return this.sensordataPowerusages;
  }

  public void setSensordataPowerusages(Set<SensordataPowerusage> sensordataPowerusages) {
    this.sensordataPowerusages = sensordataPowerusages;
  }

  public SensordataAddress getSensordataAddress() {
    return this.sensordataAddress;
  }

  public void setSensordataAddress(SensordataAddress sensordataAddress) {
    this.sensordataAddress = sensordataAddress;
  }

  public UserGroup getUserGroupBean() {
    return this.userGroupBean;
  }

  public void setUserGroupBean(UserGroup userGroupBean) {
    this.userGroupBean = userGroupBean;
  }

  public Set<EventsLog> getEventsLogs() {
    return eventsLogs;
  }

  public void setEventsLogs(Set<EventsLog> eventsLogs) {
    this.eventsLogs = eventsLogs;
  }

}
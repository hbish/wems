package com.wems.eao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.wems.entity.*;

/**
 * Session Bean implementation class SensorDataEao
 */
@Stateless
@LocalBean
public class SensorDataEao {
  @PersistenceContext
  EntityManager em;

  /**
   * Default constructor.
   */
  public SensorDataEao() {
  }

  // Getters
  @SuppressWarnings("unchecked")
  public List<SensordataAddress> getSensordataAddresses() throws NoResultException {
    Query query = em.createQuery("SELECT e FROM SensordataAddress e");
    List<SensordataAddress> sensordataAddresses = (List<SensordataAddress>) query.getResultList();
    return sensordataAddresses;
  }
  
  @SuppressWarnings("unchecked")
  public List<SensordataAddress> getAddressbyBuilding(String building) throws NoResultException {
  Query query = em.createQuery("SELECT e FROM SensordataAddress e WHERE e.building = :building");
  query.setParameter("building", building);	
	List<SensordataAddress> result = (List<SensordataAddress>) query.getResultList();
	return result;
  }
  
  @SuppressWarnings("unchecked")
  public List<String> getLevelsByBuildingName(String building) throws NoResultException {
  Query query = em.createQuery("SELECT distinct e.level FROM SensordataAddress e WHERE e.building = :bName ORDER BY e.level asc");
  query.setParameter("bName", building); 
    List<String> result = (List<String>) query.getResultList();
    return result;
  }
  
  @SuppressWarnings("unchecked")
  public List<String> getAllBuildingNames() throws NoResultException {
  Query query = em.createQuery("SELECT distinct e.building FROM SensordataAddress e");
    List<String> result = (List<String>) query.getResultList();
    return result;
  }

  @SuppressWarnings("unchecked")
  public List<SensordataAddress> getAddressbyBuildingandLevel(String building, String level) throws NoResultException {
  Query query = em.createQuery("SELECT e FROM SensordataAddress e WHERE e.building = :building AND e.level = :level");
  query.setParameter("building", building);
  query.setParameter("level", level);
	List<SensordataAddress> result = (List<SensordataAddress>) query.getResultList();
	return result;
  }
  
  @SuppressWarnings("unchecked")
  public List<Integer> getAddressUidByBuildingandLevel(String building, String level) throws NoResultException {
  Query query = em.createQuery("SELECT e.addressuid FROM SensordataAddress e WHERE e.building = :building AND e.level = :level");
  query.setParameter("building", building);
  query.setParameter("level", level);
    List<Integer> result = (List<Integer>) query.getResultList();
    return result;
  }
  
  @SuppressWarnings("unchecked")
  public List<Integer> getAddressUidByBuilding(String building) throws NoResultException {
  Query query = em.createQuery("SELECT e.addressuid FROM SensordataAddress e WHERE e.building = :building");
  query.setParameter("building", building);
    List<Integer> result = (List<Integer>) query.getResultList();
    return result;
  }
  
  @SuppressWarnings("unchecked")
  public List<Integer> getRoomUidByAddressUid(SensordataAddress address) throws NoResultException {
  Query query = em.createQuery("SELECT e.roomuid FROM SensordataRoom e WHERE e.sensordataAddress = :addressEntity");
  query.setParameter("addressEntity", address);
    List<Integer> result = (List<Integer>) query.getResultList();
    return result;
  }
  
  @SuppressWarnings("unchecked")
  public List<SensordataRoom> getRoomByUserGroup(UserGroup userGroup) throws NoResultException {
  Query query = em.createQuery("SELECT e FROM SensordataRoom e WHERE e.userGroup = :ugEntity");
  query.setParameter("ugEntity", userGroup);
    List<SensordataRoom> result = (List<SensordataRoom>) query.getResultList();
    return result;
  }

  public SensordataRoom getSensordataRoom(String id) throws NoResultException {
    Query query = em.createQuery("SELECT rooms FROM SensordataRoom rooms WHERE rooms.id = :id");
    query.setParameter("id", id);
    SensordataRoom sensordataRoom = (SensordataRoom) query.getSingleResult();
    return sensordataRoom;
  }
  
  public SensordataRoom getSensordataRoomByName(String id) throws NoResultException {
	    Query query = em.createQuery("SELECT rooms FROM SensordataRoom rooms WHERE rooms.name = :id");
	    query.setParameter("id", id);
	    SensordataRoom sensordataRoom = (SensordataRoom) query.getSingleResult();
	    return sensordataRoom;
	  }

  @SuppressWarnings("unchecked")
  public List<SensordataRoom> getRooms() throws NoResultException {
    Query query = em.createQuery("SELECT rooms FROM SensordataRoom rooms");
    List<SensordataRoom> sensordataRooms = (List<SensordataRoom>) query.getResultList();
    return sensordataRooms;
  }

  @SuppressWarnings("unchecked")
  public List<SensordataRoom> getAllRoomsByAddressId(int addressId) throws NoResultException {
    Query query = em.createQuery("SELECT rooms FROM SensordataRoom rooms WHERE rooms.addressid = :id");
    query.setParameter("id", addressId);
    List<SensordataRoom> sensordataRooms = (List<SensordataRoom>) query.getResultList();
    return sensordataRooms;
  }

  @SuppressWarnings("unchecked")
  public List<SensordataRoom> getAllRoomsByUserGroup(int userGroup) throws NoResultException {
    Query query = em.createQuery("SELECT rooms FROM SensordataRoom rooms WHERE rooms.userGroup = :ug");
    query.setParameter("ug", userGroup);
    List<SensordataRoom> sensordataRooms = (List<SensordataRoom>) query.getResultList();
    return sensordataRooms;
  }

  public SensordataDevice getDeviceByRoomByDeviceId(SensordataRoom sensordataRoom, int deviceId) throws NoResultException {
    Query query = em.createQuery("SELECT devices FROM SensordataDevice devices WHERE devices.sensordataRoom = :sensordataRoom AND devices.id = :deviceid");
    query.setParameter("sensordataRoom", sensordataRoom);
    query.setParameter("deviceid", deviceId);
    SensordataDevice sensordataDevice = (SensordataDevice) query.getSingleResult();
    return sensordataDevice;
  }


  @SuppressWarnings("unchecked")
  public List<SensordataDevice> getDevicesByRoom(SensordataRoom sensordataRoom) throws NoResultException {
    Query query = em.createQuery("SELECT devices FROM SensordataDevice devices WHERE devices.sensordataRoom = :sensordataRoom");
    query.setParameter("sensordataRoom", sensordataRoom);
    List<SensordataDevice> sensordataDevices = (List<SensordataDevice>) query.getResultList();
    return sensordataDevices;
  }

  public SensordataDevice getDeviceById(int deviceUID) throws NoResultException {
    Query query = em.createQuery("SELECT devices FROM SensordataDevice devices WHERE devices.deviceuid = :deviceuid");
    query.setParameter("deviceuid", deviceUID);
    SensordataDevice device = (SensordataDevice) query.getSingleResult();
    return device;
  }
  
  @SuppressWarnings("unchecked")
  public List<SensordataDevice> getDevicesByUserGroup(int userGroup) throws NoResultException {
	//Get user group
	Query query = em.createQuery("SELECT ug FROM UserGroup ug WHERE ug.userGroupId = :ugid");
	query.setParameter("ugid", userGroup);
	UserGroup userGroupObject = (UserGroup) query.getSingleResult();
	
	return new ArrayList<SensordataDevice>(userGroupObject.getSensordataDevices());
  }
  

  //added by David K - not sure if it's gonna work, fingers crossed.
  //DON'T think it's needed
//  @SuppressWarnings("unchecked")
//  public List<Integer> getDevicesIDsByUserGroup(int userGroup) throws NoResultException {
//	Query query = em.createQuery("SELECT devices.deviceuid FROM SensordataDevice devices WHERE devices.userGroup = :ug");
//	query.setParameter("ug", userGroup);
//	List<Integer> deviceList = (List<Integer>) query.getResultList();
//	return deviceList;
//  }

  @SuppressWarnings("unchecked")
  public SensordataAddress getSensordataAddressById(int id) throws NoResultException {
    Query query = em
        .createQuery("SELECT addresses FROM SensordataAddress addresses WHERE addresses.addressuidS = :id");
    query.setParameter("id", id);
    SensordataAddress result = (SensordataAddress) query.getResultList();
    return result;
  }

  @SuppressWarnings("unchecked")
  public List<SensordataAddress> getSensordataAddressByUserGroup(String userGroup) throws NoResultException {
    Query query = em.createQuery("SELECT addresses FROM SensordataAddress addresses WHERE addresses.userGroup = :ug");
    query.setParameter("ug", userGroup);
    List<SensordataAddress> result = (List<SensordataAddress>) query.getResultList();
    return result;
  }

  @SuppressWarnings("unchecked")
  public List<SensordataAddress> getSensordataAddressByBuilding(String building) throws NoResultException {
    Query query = em
        .createQuery("SELECT addresses FROM SensordataAddress addresses WHERE addresses.building = :building");
    query.setParameter("building", building);
    List<SensordataAddress> result = (List<SensordataAddress>) query.getResultList();
    return result;
  }
  
  @SuppressWarnings("unchecked")
  public List<SensordataAddress> getAddressByAddress(String stNo, String stName, String suburb, String state, String building, String level) throws NoResultException {
    Query query = em.createQuery("SELECT addresses FROM SensordataAddress addresses WHERE addresses.streetnumber = :stNo AND addresses.streetname = :stName AND " +
    		"addresses.suburb = :suburb AND addresses.state = :state AND addresses.building = :building AND addresses.level = :level");
    query.setParameter("stNo", stNo);
    query.setParameter("stName", stName);
    query.setParameter("suburb", suburb);
    query.setParameter("state", state);
    query.setParameter("building", building);
    query.setParameter("level", level);
    List<SensordataAddress> result = (List<SensordataAddress>) query.getResultList();
    return result;
  }

  @SuppressWarnings("unchecked")
  public List<SensordataAddress> getSensordataAddressByBuildingByLevel(String building, String level)
      throws NoResultException {
    Query query = em
        .createQuery("SELECT addresses FROM SensordataAddress addresses WHERE addresses.building = :building AND address.level = :level");
    query.setParameter("building", building);
    query.setParameter("level", level);
    List<SensordataAddress> result = (List<SensordataAddress>) query.getResultList();
    return result;
  }

  public SensordataPowerusage getRoomPowerUsage(int powerUsageId) throws NoResultException {
    Query query = em.createQuery("SELECT e FROM SensordataPowerusage e WHERE e.roomuid = :id");
    query.setParameter("id", powerUsageId);
    SensordataPowerusage result = (SensordataPowerusage) query.getSingleResult();
    return result;
  }

  public double getAveragePowerUsageByRoom(int roomId) throws NoResultException {
    Query query = em.createQuery("SELECT AVG(e.watt) FROM SensordataPowerusage e WHERE e.roomuid = :id");
    query.setParameter("id", roomId);
    double result = Double.parseDouble(query.getSingleResult().toString());
    return result;
  }

  @SuppressWarnings("unchecked")
  public List<Double> getLatestPowerUsageByRoomId(SensordataRoom sensordataRoom) throws NoResultException {
    Query query = em.createQuery("SELECT e.watt FROM SensordataPowerusage e WHERE e.sensordataRoom = :entity ORDER BY e.time desc");
    query.setParameter("entity", sensordataRoom);
    query.setMaxResults(1);
    List<Double> result = (List<Double>) query.getResultList();
    return result;
  }
  
  @SuppressWarnings("unchecked")
  public List<SensordataDeviceusage> getLatestPowerStatusByDeviceId(SensordataDevice sensordatadevice) throws NoResultException {
    Query query = em.createQuery("SELECT e FROM SensordataDeviceusage e WHERE e.sensordataDevice = :entity ORDER BY e.time desc");
    query.setParameter("entity", sensordatadevice);
    query.setMaxResults(1);
    List<SensordataDeviceusage> result = (List<SensordataDeviceusage>) query.getResultList();
    return result;
  }
  
  @SuppressWarnings("unchecked")
  public List<Date> getLatestPowerTimeByRoomId(SensordataRoom sensordataRoom) throws NoResultException {
    Query query = em.createQuery("SELECT e.time FROM SensordataPowerusage e WHERE e.sensordataRoom = :entity ORDER BY e.time desc");
    query.setParameter("entity", sensordataRoom);
    query.setMaxResults(1);
    List<Date> result = (List<Date>) query.getResultList();
    return result;
  }
  
  public SensordataParameter getSensorDataParameterById(int parameterId) throws NoResultException {
    Query query = em.createQuery("SELECT e FROM SensordataParameter e WHERE e.parameterId = :id");
    query.setParameter("id", parameterId);
    SensordataParameter result = (SensordataParameter) query.getSingleResult();
    return result;
  }

  @SuppressWarnings("unchecked")
  public List<SensordataParameter> getSensorDataParametersByDevice(int deviceId) throws NoResultException {
    Query query = em.createQuery("SELECT e FROM SensordataParameter e WHERE e.deviceid = :id");
    query.setParameter("id", deviceId);
    List<SensordataParameter> result = (List<SensordataParameter>) query.getSingleResult();
    return result;
  }

  public SensordataParameter getSensorDataParametersByDeviceByName(SensordataDevice sensordataDevice, String name) throws NoResultException {
    Query query = em.createQuery("SELECT e FROM SensordataParameter e WHERE e.sensordataDevice = :sensordataDevice AND e.name = :name");
    query.setParameter("sensordataDevice", sensordataDevice);
    query.setParameter("name", name);
    SensordataParameter result = (SensordataParameter) query.getSingleResult();
    return result;
  }
  
  //Checkers
  public int checkRoomExistById(String roomId) throws Exception {
    Query query = em.createQuery("SELECT COUNT(e.id) FROM SensordataRoom e WHERE e.id=:id");
    query.setParameter("id", roomId);
    int result = Integer.parseInt(query.getSingleResult().toString());
    return result;
  }
  
  public int checkDeviceExistById(int deviceId) throws Exception {
    Query query = em.createQuery("SELECT COUNT(e.id) FROM SensordataDevice e WHERE e.id=:id");
    query.setParameter("id", deviceId);
    int result = Integer.parseInt(query.getSingleResult().toString());
    return result;
  }
  
  public int checkDevicehasPowerUsage(SensordataDevice device) throws Exception {
	    Query query = em.createQuery("SELECT COUNT(e.deviceusageuid) FROM SensordataDeviceusage e WHERE e.sensordataDevice=:device");
	    query.setParameter("device", device);
	    int result = Integer.parseInt(query.getSingleResult().toString());
	    return result;
	  }
  
  
  public int checkDeviceExist(SensordataRoom room, int deviceId) throws Exception {
	    Query query = em.createQuery("SELECT COUNT(e.id) FROM SensordataDevice e WHERE e.id=:id AND e.sensordataRoom = :room");
	    query.setParameter("id", deviceId);
	    query.setParameter("room", room);
	    int result = Integer.parseInt(query.getSingleResult().toString());
	    return result;
  }

  // Add Methods
  public void addNewSensordataAddress(SensordataAddress sensordataAddress) throws Exception {
    em.persist(sensordataAddress);
  }

  public void addNewSensordataRoom(SensordataRoom sensordataRoom) throws Exception {
    em.persist(sensordataRoom);
  }

  public void addNewSensordataDevice(SensordataDevice sensordataDevice) throws Exception {
    em.persist(sensordataDevice);
  }

  public void addNewSensordataDeviceusage(SensordataDeviceusage sensordataDeviceusage) throws Exception {
    em.persist(sensordataDeviceusage);
  }

  public void addNewSensordataParameter(SensordataParameter sensordataParameter) throws Exception {
    em.persist(sensordataParameter);
  }

  public void addNewSensordataPowerusage(SensordataPowerusage sensordataPowerusage) throws Exception {
    em.persist(sensordataPowerusage);
  }

  // Update Methods
  public void updateSensordataAddress(SensordataAddress sensordataAddress) throws Exception {
    em.merge(sensordataAddress);
  }

  public void updateSensordataRoom(SensordataRoom sensordataRoom) throws Exception {
    em.merge(sensordataRoom);
  }

  public void updateSensordataDevice(SensordataDevice sensordataDevice) throws Exception {
    em.merge(sensordataDevice);
  }

  public void updateSensordataDeviceusage(SensordataDeviceusage sensordataDeviceusage) throws Exception {
    em.merge(sensordataDeviceusage);
  }

  public void updateSensordataParameter(SensordataParameter sensordataParameter) throws Exception {
    em.merge(sensordataParameter);
  }

  public void updateSensordataPowerusage(SensordataPowerusage sensordataPowerusage) throws Exception {
    em.merge(sensordataPowerusage);
  }

  // Remove Methods
  public void removeSensordataAddress(SensordataAddress sensordataAddress) throws Exception {
    em.merge(sensordataAddress);
  }

  public void removeSensordataRoom(SensordataRoom sensordataRoom) throws Exception {
    em.merge(sensordataRoom);
  }

  public void removeSensordataDevice(SensordataDevice sensordataDevice) throws Exception {
    em.merge(sensordataDevice);
  }

  public void removeSensordataDeviceusage(SensordataDeviceusage sensordataDeviceusage) throws Exception {
    em.merge(sensordataDeviceusage);
  }

  public void removeSensordataParameter(SensordataParameter sensordataParameter) throws Exception {
    em.merge(sensordataParameter);
  }

  public void removeSensordataPowerusage(SensordataPowerusage sensordataPowerusage) throws Exception {
    em.merge(sensordataPowerusage);
  }

}

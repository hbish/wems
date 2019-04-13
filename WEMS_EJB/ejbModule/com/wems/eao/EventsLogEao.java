package com.wems.eao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.wems.entity.*;

/**
 * Session Bean implementation class UserEao
 */
@Stateless
@LocalBean
public class EventsLogEao {
    @PersistenceContext
    EntityManager em;

    /**
     * Default constructor.
     */
    public EventsLogEao() {
    }

    // Getters
    @SuppressWarnings("unchecked")
    public List<EventsLog> getAllEvents() throws NoResultException {
	Query query = em.createQuery("SELECT e FROM EventsLog e");
	List<EventsLog> eventsList = (List<EventsLog>) query.getResultList();
	return eventsList;
    }

    @SuppressWarnings("unchecked")
    public List<EventsLog> getAllEventsByType(int eventTypes) throws NoResultException {
	Query query = em.createQuery("SELECT e FROM EventsLog e WHERE e.eventType = :type");
	List<EventsLog> eventsList = (List<EventsLog>) query.getResultList();
	return eventsList;
    }

    public EventsLog getEventById(int eventId) throws NoResultException {
	Query query = em.createQuery("SELECT e FROM EventsLog e WHERE e.eventType = :id");
	query.setParameter("id", eventId);
	EventsLog eventLog = (EventsLog) query.getSingleResult();
	return eventLog;
    }
    
    @SuppressWarnings("unchecked")
    public List<EventsLog> getEventByDeviceId(SensordataDevice device) throws NoResultException {
	Query query = em.createQuery("SELECT e FROM EventsLog e WHERE e.sensordataDevice = :device");
	query.setParameter("device", device);
	List<EventsLog> eventsList = (List<EventsLog>) query.getResultList();
	return eventsList;
    }
    
    @SuppressWarnings("unchecked")
    public List<EventsLog> getEventByRoomId(SensordataRoom room) throws NoResultException {
    Query query = em.createQuery("SELECT e FROM EventsLog e WHERE e.sensordataRoom = :room");
    query.setParameter("room", room);
    List<EventsLog> eventsList = (List<EventsLog>) query.getResultList();
    return eventsList;
    }

    @SuppressWarnings("unchecked")
    public List<AlertLog> getAllAlerts() throws NoResultException {
	Query query = em.createQuery("SELECT e FROM AlertLog e");
	List<AlertLog> alertsList = (List<AlertLog>) query.getResultList();
	return alertsList;
    }

    @SuppressWarnings("unchecked")
    public List<AlertLog> getAlertsByType(String alertType) throws NoResultException {
	Query query = em.createQuery("SELECT e FROM AlertLog e WHERE e.alertType = :type");
	query.setParameter("id", alertType);
	List<AlertLog> alertsList = (List<AlertLog>) query.getResultList();
	return alertsList;
    }

    @SuppressWarnings("unchecked")
    public List<AlertLog> getAlertsByPriority(String alertPriority) throws NoResultException {
	Query query = em.createQuery("SELECT e FROM AlertLog e WHERE e.alertPriority = :priority");
	query.setParameter("priority", alertPriority);
	List<AlertLog> alertsList = (List<AlertLog>) query.getResultList();
	return alertsList;
    }
    
    public AlertLog getAlertById(int alertId) throws NoResultException {
	Query query = em.createQuery("SELECT e FROM AlertLog e WHERE e.alertsId = :id");
	query.setParameter("id", alertId);
	AlertLog alertLog = (AlertLog) query.getSingleResult();
	return alertLog;
    }
    
    @SuppressWarnings("unchecked")
    public List<AlertLog> getAlertsByAlertSetting(AlertSetting alertSetting) throws NoResultException {
    Query query = em.createQuery("SELECT e FROM AlertLog e WHERE e.alertSetting = :asId");
    query.setParameter("asId", alertSetting);
    List<AlertLog> resultList = (List<AlertLog>) query.getResultList();
    return resultList;
    }
    
    @SuppressWarnings("unchecked")
    public List<AlertLog> getAlertsByEvents(EventsLog eventsLog) throws NoResultException {
    Query query = em.createQuery("SELECT e FROM AlertLog e WHERE e.eventsLogs = :events");
    query.setParameter("events", eventsLog);
    List<AlertLog> resultList = (List<AlertLog>) query.getResultList();
    return resultList;
    }

    // Add methods
    public void addNewEventLog(EventsLog event) throws Exception {
	em.persist(event);
    }

    public void addNewAlertLog(AlertLog alert) throws Exception {
	em.persist(alert);
    }

    // Update methods
    public void updateEventLog(EventsLog event) throws NoResultException {
	em.merge(event);
    }

    public void updateAlertLog(AlertLog alert) throws NoResultException {
	em.merge(alert);
    }

    // remove methods
    public void removeEventLog(EventsLog event) throws NoResultException {
	em.remove(event);
    }

    public void removeAlertLog(AlertLog alert) throws NoResultException {
	em.remove(alert);
    }

}

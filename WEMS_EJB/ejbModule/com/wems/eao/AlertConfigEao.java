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
public class AlertConfigEao {
    @PersistenceContext
    EntityManager em;

    /**
     * Default constructor.
     */
    public AlertConfigEao() {
    }
    
    // Getters for Setting
    @SuppressWarnings("unchecked")
    public List<AlertSetting> getAllAlertSettings() throws NoResultException {
	Query query = em.createQuery("SELECT e FROM AlertSetting e");
	List<AlertSetting> alarmSettingList = (List<AlertSetting>) query.getResultList();
	return alarmSettingList;
    }

    public AlertSetting getAllAlertSettingsById(int alarmSettingId) throws NoResultException {
	Query query = em.createQuery("SELECT e FROM AlertSetting e WHERE e.alertId = :id");
	query.setParameter("id", alarmSettingId);
	AlertSetting alarmSettingList = (AlertSetting) query.getSingleResult();
	return alarmSettingList;
    }

    @SuppressWarnings("unchecked")
    public List<AlertSetting> getAllAlertSettingsByPriority(String alertPriority) throws NoResultException {
	Query query = em.createQuery("SELECT e FROM AlertSetting e WHERE e.alertPriority = :priority");
	query.setParameter("priority", alertPriority);
	List<AlertSetting> alarmSettingList = (List<AlertSetting>) query.getResultList();
	return alarmSettingList;
    }

    @SuppressWarnings("unchecked")
    public List<AlertSetting> getAllAlertSettingsByUserGroup(UserGroup userGroup) throws NoResultException {
	Query query = em.createQuery("SELECT e FROM AlertSetting e WHERE e.userGroup = :group");
	query.setParameter("group", userGroup);
	List<AlertSetting> alarmSettingList = (List<AlertSetting>) query.getResultList();
	return alarmSettingList;
    }
    
    @SuppressWarnings("unchecked")
    public List<Integer> getAllAlertSettingsIDByUserGroup(UserGroup userGroup) throws NoResultException {
	Query query = em.createQuery("SELECT e.alertId FROM AlertSetting e WHERE e.userGroup = :group");
	query.setParameter("group", userGroup);
	List<Integer> alarmSettingList = (List<Integer>) query.getResultList();
	return alarmSettingList;
    }

    // Getters for Alarm Logic

    @SuppressWarnings("unchecked")
    public List<AlertSetting> getAlertsExceedsMax(double sensorValue) throws NoResultException {
	Query query = em
		.createQuery("SELECT e FROM AlertSetting e WHERE e.maxThresholdEnabled = true AND e.maxThresholdValue < :value1");
	query.setParameter("value1", sensorValue);
	List<AlertSetting> resultList = (List<AlertSetting>) query.getResultList();
	return resultList;
    }

    @SuppressWarnings("unchecked")
    public List<AlertSetting> getAlertsExceedsMin(double sensorValue) throws NoResultException {
	Query query = em
		.createQuery("SELECT e FROM AlertSetting e WHERE e.minThresholdEnabled = true AND e.minThresholdValue > :value1");
	query.setParameter("value1", sensorValue);
	List<AlertSetting> resultList = (List<AlertSetting>) query.getResultList();
	return resultList;
    }

    @SuppressWarnings("unchecked")
    public List<AlertSetting> getAlertsMatchValue(double sensorValue) throws NoResultException {
	Query query = em
		.createQuery("SELECT e FROM AlertSetting e WHERE e.exactThresholdValue = true AND e.exactThresholdEnabled = :value1");
	query.setParameter("value1", sensorValue);
	List<AlertSetting> resultList = (List<AlertSetting>) query.getResultList();
	return resultList;
    }

    @SuppressWarnings("unchecked")
    public List<AlertSetting> getAlertsExceedsScore(int score) throws NoResultException {
	Query query = em.createQuery("SELECT e FROM AlertSetting e WHERE e.scoreEnabled = true AND e.score = :score");
	query.setParameter("score", score);
	List<AlertSetting> resultList = (List<AlertSetting>) query.getResultList();
	return resultList;
    }

    // Add methods

    public void addNewAlertSetting(AlertSetting alertSetting) throws Exception {
	em.persist(alertSetting);
    }

    // Update methods

    public void updateAlertSetting(AlertSetting alertSetting) throws NoResultException {
	em.merge(alertSetting);
    }

    // remove methods

    public void removeAlertSetting(AlertSetting alertSetting) throws NoResultException {
	em.remove(alertSetting);
    }

}

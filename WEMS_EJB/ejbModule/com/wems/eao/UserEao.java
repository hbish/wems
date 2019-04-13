package com.wems.eao;

import java.util.ArrayList;
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
public class UserEao {
    @PersistenceContext
    EntityManager em;

    /**
     * Default constructor.
     */
    public UserEao() {
    }

    public UserGroup getUserGroupByUsername(String username) throws NoResultException {
        Query query = em.createQuery("SELECT users FROM User users WHERE users.username = :un");
        query.setParameter("un", username);
        User user = (User) query.getSingleResult();
        return user.getUserGroupBean();
           }
    
    public UserType getUserTypeByName(String usertype) throws NoResultException {
        Query query = em.createQuery("SELECT usertype FROM UserType usertype WHERE usertype.userTypeName = :usertype");
        query.setParameter("usertype", usertype);
        UserType type = (UserType) query.getSingleResult();
        return type;
           }
    
    // Getters
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() throws NoResultException {
	Query query = em.createQuery("SELECT users FROM User users");
	List<User> userList = (List<User>) query.getResultList();
	return userList;
    }
    
    @SuppressWarnings("unchecked")
    public List<Integer> getAllUsersId() throws NoResultException {
	Query query = em.createQuery("SELECT users.userId FROM User users");
	List<Integer> userList = (List<Integer>) query.getResultList();
	return userList;
    }
    
    public User getUserById(int id) throws NoResultException {
	Query query = em.createQuery("SELECT users FROM User users WHERE users.userId = :id");
	query.setParameter("id", id);
	User user = (User) query.getSingleResult();
	return user;
    }
    
    public UserGroup getUserGroupById(int id) throws NoResultException {
	Query query = em.createQuery("SELECT usergroup FROM UserGroup usergroup WHERE usergroup.userGroupId = :id");
	query.setParameter("id", id);
	UserGroup usergroup = (UserGroup) query.getSingleResult();
	return usergroup;
    }
    
    public UserGroup getUserGroupByName(String userGroupName) throws NoResultException {
    	Query query = em.createQuery("SELECT usergroup FROM UserGroup usergroup WHERE usergroup.userGroupName = :userGroupName");
    	query.setParameter("userGroupName", userGroupName);
    	UserGroup usergroup = (UserGroup) query.getSingleResult();
    	return usergroup;
        }
    
    @SuppressWarnings("unchecked")
	public List<String> getAllUserTypes() throws NoResultException {
    	Query query = em.createQuery("SELECT usertype.userTypeName FROM UserType usertype");
    	List<String>  types = (List<String>) query.getResultList();
    	return types;
        }
    
    @SuppressWarnings("unchecked")
    public List<UserGroup> getAllUserGroups() throws NoResultException {
    	Query query = em.createQuery("SELECT usergroup FROM UserGroup usergroup");
    	List<UserGroup> userGroup = (List<UserGroup>) query.getResultList();
    	return userGroup;
    }

    public UserType getUserTypeById(int id) throws NoResultException {
	Query query = em.createQuery("SELECT usertype FROM UserType usertype WHERE usertype.userTypeId = :id");
	query.setParameter("id", id);
	UserType usertype = (UserType) query.getSingleResult();
	return usertype;
    }
    
    public UserSession getUserSession(int id) throws NoResultException {
	Query query = em.createQuery("SELECT session FROM UserSession session WHERE session.sessionId = :id");
	query.setParameter("id", id);
	UserSession userSession = (UserSession) query.getSingleResult();
	return userSession;
    }
    
    @SuppressWarnings("unchecked")
    public List<UserSession> getUserSessionByIP(String ip) throws NoResultException {
    	Query query = em.createQuery("SELECT session FROM UserSession session WHERE session.ipAddress = :ip ORDER BY session.sessionId asc");
    	query.setParameter("ip", ip);
    	List<UserSession> userSession = (List<UserSession>) query.getResultList();
    	return userSession;
        }
        
    public User getUserByUsername(String username) throws NoResultException {
	Query query = em.createQuery("SELECT users FROM User users WHERE users.username = :username");
	query.setParameter("username", username);
	User user = (User) query.getSingleResult();
	return user;
    }

    public User getUserBySession(UserSession session) throws NoResultException {
    	Query query = em.createQuery("SELECT users FROM User users WHERE users.userSession = :session");
    	query.setParameter("session", session);
    	User user = (User) query.getSingleResult();
    	return user;
        }
    
    @SuppressWarnings("unchecked")
    public List<User> getAllUserByGroup(String userGroup) throws NoResultException {
	Query query = em.createQuery("SELECT users FROM User users WHERE users.userGroupId = :userGroup");
	query.setParameter("userGroup", userGroup);
	List<User> userList = (List<User>) query.getResultList();
	return userList;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUserByType(String userType) throws NoResultException {
	Query query = em.createQuery("SELECT users FROM User users WHERE users.userTypeId = :userType");
	query.setParameter("id", userType);
	List<User> userList = (List<User>) query.getResultList();
	return userList;
    }

    //Checkers
    public int checkUserExistByUsername(String username) throws Exception {
      Query query = em.createQuery("SELECT COUNT(e.username) FROM User e WHERE e.username=:uname");
      query.setParameter("uname", username);
      int result = Integer.parseInt(query.getSingleResult().toString());
      return result;
    }
    
    public int checkUserExistByEmail(String email) throws Exception {
      Query query = em.createQuery("SELECT COUNT(e.email) FROM User e WHERE e.email=:email");
      query.setParameter("email", email);
      int result = Integer.parseInt(query.getSingleResult().toString());
      return result;
    }
    
    public int checkUserGroupByName(String name) throws Exception {
    	System.out.println("in UserEao, name=" + name);
      Query query = em.createQuery("SELECT COUNT(e.name) FROM UserGroup e WHERE e.userGroupName=:name");
      query.setParameter("name", name);
      int result = Integer.parseInt(query.getSingleResult().toString());
      return result;
    }
    
    public int checkUserLogin(String username, String hash) throws Exception {
      Query query = em.createQuery("SELECT COUNT(e.username) FROM User e WHERE e.username=:uname AND e.passHash=:hash");
      query.setParameter("uname", username);
      query.setParameter("hash", hash);
      int result = Integer.parseInt(query.getSingleResult().toString());
      return result;
    }

    // Add methods

    public void addNewUser(User user) throws Exception {
	em.persist(user);
    }

    public void addUserSession(UserSession userSession) throws Exception {
	em.persist(userSession);
    }

    public void addUserType(UserType userType) throws Exception {
	em.persist(userType);
    }

    public void addUserGroup(UserGroup userGroup) throws Exception {
	em.persist(userGroup);
    }

    // Update methods

    public void updateUser(User user) throws Exception {
	em.merge(user);
    }

    public void updateUserSession(UserSession userSession) throws Exception {
	em.merge(userSession);
    }

    public void updateUserType(UserType userType) throws Exception {
	em.merge(userType);
    }

    public void updateUserGroup(UserGroup userGroup) throws Exception {
	em.merge(userGroup);
    }

    // remove methods

    public void removeUser(User user) throws Exception {
	em.remove(user);
    }

    public void removeUserSession(UserSession userSession) throws Exception {
	em.remove(userSession);
    }

    public void removeUserType(UserType userType) throws Exception {
	em.remove(userType);
    }

    public void removeUserGroup(UserGroup userGroup) throws Exception {
	em.remove(userGroup);
    }

}

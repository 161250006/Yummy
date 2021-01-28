package com.hungry.daoImpl;

import com.hungry.dao.UserDao;
import com.hungry.model.Address;
import com.hungry.model.Manager;
import com.hungry.model.Restaurant;
import com.hungry.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

   @Override
    public void insertUser(User user,ArrayList<Address> addressArrayList) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            session.save(user);
            session.flush();
            session.clear();
            for (Address a:addressArrayList) {
                session.save(a);
                session.flush();
                session.clear();
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByUserName(String userName) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM User WHERE userName=?";
            Query query = session.createQuery(hql);
            query.setParameter(0, userName);
            List<User> results = (List<User>)query.list();
            if (!results.isEmpty()){
                for (User user:results){
                    if(user.getIfDelete()==0){
                        tx.commit();
                        session.close();
                        return user;
                    }
                }
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean ifSame(String userName) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM User WHERE userName=?";
            Query query = session.createQuery(hql);
            query.setParameter(0, userName);
            List<User> results = (List<User>)query.list();
            if (!results.isEmpty()){
                for (User user:results){
                    if(user.getIfDelete()==0){
                        tx.commit();
                        session.close();
                        return true;
                    }
                }
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void modifyUser(User user,ArrayList<Address> addressArrayList) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM User WHERE userName=?";
            Query query = session.createQuery(hql);
            query.setParameter(0, user.getUserName());
            List<User> results = (List<User>)query.list();
            if (!results.isEmpty()){
                for (User u:results){
                    if(u.getIfDelete()==0){
                        u.setPhoneNumber(user.getPhoneNumber());
                        u.setName(user.getName());
                        u.setPassword(user.getPassword());
                        session.save(u);
                        break;
                    }
                }
            }
            session.flush();
            session.clear();
            String hql1 = "delete FROM Address WHERE userName=?";
            Query query1 = session.createQuery(hql1);
            query1.setParameter(0, user.getUserName());
            int result = query1.executeUpdate();
            for (Address a:addressArrayList) {
                session.save(a);
                session.flush();
                session.clear();
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyRestaurant(Restaurant restaurant) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            Restaurant res=session.get(Restaurant.class, restaurant.getRestaurantID());
            res.setType(restaurant.getType());
            res.setPassword(restaurant.getPassword());
            res.setName(restaurant.getName());
            res.setAddress(restaurant.getAddress());
            session.save(res);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Restaurant getRestaurantByID(String restaurantID) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            Restaurant restaurant=session.get(Restaurant.class,restaurantID);
            tx.commit();
            session.clear();
            session.close();
            return restaurant;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertRestaurant(Restaurant restaurant) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            session.save(restaurant);
            session.flush();
            session.clear();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Manager getManager(String userName) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            Manager manager=session.get(Manager.class,userName);
            tx.commit();
            session.close();
            return manager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getUserNumber() {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "select count(*) FROM User";
            Query query = session.createQuery(hql);
            int result=((Number)query.uniqueResult()).intValue();
            tx.commit();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getRestaurantNumber() {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "select count(*) FROM Restaurant";
            Query query = session.createQuery(hql);
            tx.commit();
            int result=((Number)query.uniqueResult()).intValue();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Address> getAddressByUserName(String userName) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM Address WHERE userName=?";
            Query query = session.createQuery(hql);
            query.setParameter(0, userName);
            List<Address> results = (List<Address>)query.list();
            tx.commit();
            session.close();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteUser(String userName) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM User WHERE userName=?";
            Query query = session.createQuery(hql);
            query.setParameter(0, userName);
            List<User> results = (List<User>)query.list();
            if (!results.isEmpty()){
                for (User u:results){
                    if(u.getIfDelete()==0){
                        u.setIfDelete(1);
                        session.save(u);
                        break;
                    }
                }
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Restaurant> showRestaurant() {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM Restaurant";
            Query query = session.createQuery(hql);
            query.setFirstResult(0);
            query.setMaxResults(3);
            List<Restaurant> results = (List<Restaurant>)query.list();
            tx.commit();
            session.close();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Restaurant> searchRestaurant(String input) {

        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM Restaurant r WHERE (r.name like ?) or (r.type like ?) or (r.address like ?)";
            Query query = session.createQuery(hql);
            query.setParameter(0, "%"+input+"%");
            query.setParameter(1, "%"+input+"%");
            query.setParameter(2, "%"+input+"%");
            List<Restaurant> results = (List<Restaurant>)query.list();
            tx.commit();
            session.close();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void payForMoney(int userID, int money) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            User user=session.get(User.class, userID);
            System.out.println(userID);
            System.out.println(user.getUserName());
            if (user.getBalance()>=money) {
                user.setBalance(user.getBalance() - money);
            }
            session.save(user);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMoney(int userID, int money) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            User user=session.get(User.class, userID);
                user.setBalance(user.getBalance() + money);
            session.save(user);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void changeLevel(int userID, int level) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            User user=session.get(User.class, userID);
            user.setLevel(level);
            session.save(user);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.hungry.daoImpl;

import com.hungry.dao.MenuDao;
import com.hungry.model.Menu;
import com.hungry.model.Restaurant;
import com.hungry.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuDaoImpl implements MenuDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @Override
    public void insertMenu(List<Menu> menuList) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "delete FROM Menu WHERE restaurant_id=?";
            Query query = session.createQuery(hql);
            query.setParameter(0, menuList.get(0).getRestaurantID());
            int result=query.executeUpdate();
            for (Menu menu:menuList){
                session.save(menu);
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Menu> getMenu(String restaurantID) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM Menu WHERE restaurant_id=?";
            Query query = session.createQuery(hql);
            query.setParameter(0, restaurantID);
            List<Menu> results=(List<Menu>) query.list();
            tx.commit();
            session.close();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

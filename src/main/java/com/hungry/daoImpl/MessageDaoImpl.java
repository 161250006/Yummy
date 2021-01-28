package com.hungry.daoImpl;

import com.hungry.dao.MessageDao;
import com.hungry.model.Address;
import com.hungry.model.Modify;
import com.hungry.model.Restaurant;
import com.hungry.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import sun.plugin2.message.Message;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class MessageDaoImpl implements MessageDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @Override
    public void insertMessage(Modify modify) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            session.save(modify);
            tx.commit();
            session.close();
            System.out.println("insert");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getExamineResult(String restaurantID) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM Modify WHERE restaurantID=? order by time desc ";
            Query query = session.createQuery(hql);
            query.setParameter(0, restaurantID);
            List<Modify> results = (List<Modify>)query.list();
            if (!results.isEmpty()){
                Modify modify=results.get(0);
                    if(modify.getState().equals("审核中")){
                        tx.commit();
                        session.close();
                        return "doing";
                    }
                    else if(modify.getState().equals("通过")){
                        if (modify.getIfInform()==0) {
                            modify.setIfInform(1);
                            session.save(modify);
                            tx.commit();
                            session.close();
                            return "pass";
                        }
                        else {
                            tx.commit();
                            session.close();
                            return "none";
                        }
                    }
                    else if(modify.getState().equals("未通过")){
                        if (modify.getIfInform()==0) {
                            modify.setIfInform(1);
                            session.save(modify);
                            tx.commit();
                            session.close();
                            return "notPass";
                        }
                        else {
                            tx.commit();
                            session.close();
                            return "none";
                        }
                    }
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "none";
    }

    @Override
    public List<Modify> getAllModify() {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM Modify order by time desc ";
            Query query = session.createQuery(hql);
            List<Modify> results = (List<Modify>)query.list();
            tx.commit();
            session.close();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void approve(Integer modifyID, String result) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            Modify modify=session.get(Modify.class, modifyID);
            modify.setState(result);
            session.save(modify);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.hungry.daoImpl;

import com.hungry.dao.OrderDao;
import com.hungry.model.Modify;
import com.hungry.model.Orders;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoImpl implements OrderDao{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @Override
    public Map<String, Integer> getAllMoney() {
        Map<String,Integer> result=new HashMap<>();
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "select sum(moneyTotal),sum(moneyRestaurant) FROM Orders";
            Query query = session.createQuery(hql);
            List<Object[]> results = (List<Object[]>)query.list();
            result.put("total", ((Number)results.get(0)[0]).intValue());
            result.put("income", ((Number)results.get(0)[0]).intValue()-((Number)results.get(0)[1]).intValue());
            String sql = "SELECT sum(moneyTotal),sum(moneyRestaurant) FROM Orders WHERE DATE_FORMAT( order_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )";
            SQLQuery query1 = session.createSQLQuery(sql);
            query1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List<Object[]> result1 = (List<Object[]>)query.list();
            result.put("month", ((Number)result1.get(0)[0]).intValue()-((Number)result1.get(0)[1]).intValue());
            tx.commit();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertOrder(Orders orders) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            session.save(orders);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Orders> getAllOrdersUser(String userName) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM Orders where userName=?";
            Query query = session.createQuery(hql);
            query.setParameter(0, userName );
            List<Orders> results = (List<Orders>)query.list();
            tx.commit();
            session.close();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Orders> getAllOrdersRestaurant(String restaurantID) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "FROM Orders where restaurantID=? and (state='已结束' or state='已支付')";
            Query query = session.createQuery(hql);
            query.setParameter(0, restaurantID );
            List<Orders> results = (List<Orders>)query.list();
            tx.commit();
            session.close();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cancelOrder(int OrderID) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            Orders orders=session.get(Orders.class, OrderID);
            orders.setState("已取消");
            session.save(orders);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void payOrder(int OrderID) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            Orders orders=session.get(Orders.class, OrderID);
            orders.setState("已支付");
            session.save(orders);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unsubscribeOrder(int OrderID) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            Orders orders=session.get(Orders.class, OrderID);
            orders.setState("已结束");
            orders.setType("退订");
            session.save(orders);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmOrder(int OrderID) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            Orders orders=session.get(Orders.class, OrderID);
            orders.setState("已结束");
            session.save(orders);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean ifOutOfTime(int OrderID) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            Orders orders=session.get(Orders.class, OrderID);
            Date date=new Date();
            tx.commit();
            session.close();
            if ((date.getTime()-orders.getOrderTime().getTime())<=120000){
                return false;
            }
            else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Orders getOrder(int OrderID) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            Orders orders=session.get(Orders.class, OrderID);
            tx.commit();
            session.close();
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void changeRestaurantMoney(int OrderID, int unsubscribeMoney) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            Orders orders=session.get(Orders.class, OrderID);
            orders.setMoneyRestaurant(orders.getMoneyTotal()-unsubscribeMoney);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getTotalBuyMoney(int userID) {
        try {
            Session session = getSession();
            Transaction tx=session.beginTransaction();
            String hql = "select sum(moneyTotal) FROM Orders where userID=? and state='已结束' and type='购买'";
            Query query = session.createQuery(hql);
            query.setParameter(0, userID );
            int results = ((Number)query.uniqueResult()).intValue();
            tx.commit();
            session.close();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}

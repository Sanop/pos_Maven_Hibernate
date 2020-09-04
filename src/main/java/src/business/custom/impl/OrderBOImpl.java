package src.business.custom.impl;

import business.custom.OrderBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import db.DBConnection;
import db.HibernateUtil;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.OrderDetailTM;
import util.OrderTM;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    OrderDetailDAO orderDetailDAO =DAOFactory.getInstance().getDAO(DAOType.ORDER_DETAIL);
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);

    public void placeOrder(OrderTM order, List<OrderDetailTM> orderDetails)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        orderDAO.setSession(session);
        orderDetailDAO.setSession(session);
        itemDAO.setSession(session);
        customerDAO.setSession(session);

        try{
            tx = session.beginTransaction();
            orderDAO.add(new Order(order.getOrderId(), Date.valueOf(order.getOrderDate()), customerDAO.find(order.getCustomerId())));
            for (OrderDetailTM orderDetail : orderDetails) {
                orderDetailDAO.add(new OrderDetail(order.getOrderId(), orderDetail.getCode(), orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())));
                Item item = itemDAO.find(orderDetail.getCode());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
            }
            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.rollback();
            throw t;
        }finally {
            session.close();
        }

    }

    public String autoGeneratePlaceOrderID()throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        orderDAO.setSession(session);
        Transaction tx = null;
        String id = null;
        try{
            tx = session.beginTransaction();
            String oldID = orderDAO.getLastOrderID();
            oldID = oldID.substring(2, 5);
            int newID = Integer.parseInt(oldID) + 1;
            if (newID < 10) {
                id =   "OD00" + newID;
            } else if (newID < 100) {
                id =   "OD0" + newID;
            } else {
                id =   "OD" + newID;
            }
            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.rollback();
        }finally {
            session.close();
        }
        return id;
    }
}

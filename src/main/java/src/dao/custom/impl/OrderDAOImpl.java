package src.dao.custom.impl;

import dao.CrudDAOImpl;
import dao.custom.OrderDAO;
import entity.Order;
import org.hibernate.Session;

import java.util.List;

public class OrderDAOImpl extends CrudDAOImpl<Order,String> implements OrderDAO {

    @Override
    public String getLastOrderID() throws Exception {
        List list = session.createQuery("SELECT O.id FROM entity.Order O ORDER BY O.id DESC").setMaxResults(1).list();

        return (list.size() > 0) ? (String) list.get(0) : null;
    }
}

package src.dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.Order;

public interface OrderDAO extends CrudDAO<Order, String> {
    public String getLastOrderID()throws Exception;
}

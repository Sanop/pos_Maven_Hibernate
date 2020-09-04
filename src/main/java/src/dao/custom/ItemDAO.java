package src.dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.Item;

public interface ItemDAO extends CrudDAO<Item , String> {
    public String getLastItemID()throws Exception;
}

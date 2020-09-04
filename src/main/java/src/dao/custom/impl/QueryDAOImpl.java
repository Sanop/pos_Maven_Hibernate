package src.dao.custom.impl;

import dao.custom.QueryDAO;
import entity.CustomEntity;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public CustomEntity getOrderDetail(String id) throws Exception {
        return (CustomEntity) session.createNativeQuery("SELECT c.name AS customerName," +
                "o.id AS orderID," +
                "o.date orderDate FROM `Order` o INNER JOIN " +
                "Customer c ON o.customerId = c.id WHERE o.id = ?1").setParameter(1,id).
                setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
    }

    @Override
    public CustomEntity getOrderDetail2(String id) throws Exception {
        return (CustomEntity) session.createQuery("SELECT NEW entity.CustomEntity(C.id,C.name,O.id) " +
                "FROM Order O INNER JOIN O.customer C WHERE O.id = :id").
                setParameter("id",id);
    }

    @Override
    public List<CustomEntity> getAllOrderDetail() throws Exception {
        return (List<CustomEntity>) session.createNativeQuery("SELECT O.id AS orderID,O.date AS orderDate,C.id AS customerID,C.name AS customerName,SUM(od.qty * od.unitPrice)as total FROM " +
                "`Order` O inner join Customer C on " +
                "O.customerId = c.id inner join OrderDetail OD " +
                "on OD.orderId = O.id group by O.id, O.date, O.id, C.name").setResultTransformer(Transformers.aliasToBean(CustomEntity.class));

    }

    @Override
    public List<CustomEntity> SearchAllOrderDetail(String key) throws Exception {

//        ResultSet resultSet = CrudUtil.execute("select o.id,o.date,c.id,c.name,SUM(od.qty * od.unitPrice) as total from \" +\n" +
//                "                    \"`Order` o inner join Customer c on \" +\n" +
//                "                    \"o.customerId = c.id inner join OrderDetail od on \" +\n" +
//                "                    \"od.orderId = o.id where o.id like ? or \" +\n" +
//                "                    \"o.date like ? or c.id like ? or \" +\n" +
//                "                    \"c.name like ? group by \" +\n" +
//                "                    \"o.id, o.date, c.id, c.name", key, key, key, key);
//        List<CustomEntity> entityList = new ArrayList<>();
//        while (resultSet.next()) {
//            entityList.add(new CustomEntity(resultSet.getString(1),
//                    resultSet.getDate(2),
//                    resultSet.getString(3),
//                    resultSet.getString(4),
//                    resultSet.getDouble(5)));
//        }
//        return entityList;
        return null;
    }


}

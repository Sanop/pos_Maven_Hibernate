package src.business.custom.impl;

import business.custom.CustomerBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.CustomerDAO;
import dao.custom.impl.CustomerDAOImpl;
import db.HibernateUtil;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.CustomerTM;

import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO =DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);

    public String getNewCustomerId()throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        customerDAO.setSession(session);
        Transaction tx = null;
        String id = "";
        try{
            tx = session.beginTransaction();
            String lastCustomerId = customerDAO.getLastCustomerID();
            System.out.println(lastCustomerId+"cid");
            if (lastCustomerId == null){
                return "C001";
            }else{
                int maxId=  Integer.parseInt(lastCustomerId.replace("C",""));
                maxId = maxId + 1;

                if (maxId < 10) {
                    id = "C00" + maxId;
                } else if (maxId < 100) {
                    id = "C0" + maxId;
                } else {
                    id = "C" + maxId;
                }
                System.out.println(id);
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

    public List<CustomerTM> getAllCustomers()throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        customerDAO.setSession(session);
        Transaction tx = null;
        List<CustomerTM> customerTMS = new ArrayList<>();
        try{
            tx = session.beginTransaction();
            List<Customer> allCustomers = customerDAO.findAll() ;
            for (Customer customerTM : allCustomers) {
                customerTMS.add(new CustomerTM(customerTM.getId(),customerTM.getName(),customerTM.getAddress()));
            }
            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.commit();
        }finally {
            session.close();
        }
        return customerTMS;
    }

    public void saveCustomer(String id, String name, String address)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        customerDAO.setSession(session);
        Transaction tx = null;

        try{
            tx = session.beginTransaction();

            customerDAO.add(new Customer(id, name, address));

            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.rollback();
        }finally {
            session.close();
        }

    }

    public void deleteCustomer(String customerId)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        customerDAO.setSession(session);
        Transaction tx = null;

        try{
            tx = session.beginTransaction();

            customerDAO.delete(customerId);

            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.rollback();
        }finally {
            session.close();
        }
    }

    public void updateCustomer( String customerId ,String name, String address)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        customerDAO.setSession(session);
        Transaction tx = null;

        try{
            tx = session.beginTransaction();

            customerDAO.update(new Customer(customerId,name,address));

            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.rollback();
            throw t;
        }finally {
            session.close();
        }
    }

}

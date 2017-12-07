package com.lazermann.AddApplication.dao;

import com.lazermann.AddApplication.dto.PurchaseDto;
import com.lazermann.AddApplication.model.Purchase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class PurchaseDao {
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void fillDB() throws Exception{
        Purchase purchase = new Purchase(1,"Coca-Cola",2);
        Date date = new Date();
        purchase.setDate(date);
        getSession().save(purchase);
        Purchase purchase2 = new Purchase(1,"Coca-Cola",2);
        purchase2.setDate(date);
        getSession().save(purchase2);
    }

    public void addPurchase(String card_id, String name, String price) throws Exception  {
        Purchase purchase = new Purchase(Long.parseLong(card_id),"Coca-Cola",2);
        Date date = new Date();
        purchase.setDate(date);
        getSession().save(purchase);
    }


    public List<PurchaseDto> getAllPurchases(String card_id) throws Exception {
        return getSession().createQuery(
                "from Purchase purchase where purchase.card_id = :card_id")
                .setParameter("card_id", Long.parseLong(card_id))
                .list();
    }

    public List<PurchaseDto> getAllPurchases(Date from, Date to) throws Exception {
        return getSession().createQuery(
                "from Purchase purchase where purchase.date between :from and :to")
                .setParameter("from", from)
                .setParameter("to", to)
                .list();
    }
}

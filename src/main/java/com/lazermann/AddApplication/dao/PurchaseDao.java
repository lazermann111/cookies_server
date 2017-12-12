package com.lazermann.AddApplication.dao;

import com.lazermann.AddApplication.dto.PurchaseDto;
import com.lazermann.AddApplication.helpers.DozerHelper;
import com.lazermann.AddApplication.model.Purchase;
import org.dozer.DozerBeanMapper;
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

    @Autowired
    DozerBeanMapper dozerMapper;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void fillDB() throws Exception{
        Purchase purchase = new Purchase(1,200);
        Date date = new Date();
        purchase.setDate(date);
        getSession().save(purchase);
        Purchase purchase2 = new Purchase(1,200);
        purchase2.setDate(date);
        getSession().save(purchase2);
    }

    public void addPurchase(String card_id, String price) throws Exception  {
        Purchase purchase = new Purchase(Long.parseLong(card_id),Float.parseFloat(price));
        Date date = new Date();
        purchase.setDate(date);
        getSession().save(purchase);
    }

    /*public void addRefund(String card_id, String name, String price) throws Exception  {
        Purchase purchase = new Purchase(Long.parseLong(card_id),"Coca-Cola",Float.parseFloat(price)*(-1));
        Date date = new Date();
        purchase.setDate(date);
        getSession().save(purchase);
    }*/

    @SuppressWarnings("unchecked")
    public Purchase getPurchase(String card_id, String purchaseId) throws Exception {
        return (Purchase) getSession().createQuery(
                "from Purchase purchase where purchase.card_id = :card_id and purchase.id = :purchaseId")
                .setParameter("card_id", Long.parseLong(card_id))
                .setParameter("purchaseId", Long.parseLong(purchaseId))
                .uniqueResult();

    }

    @SuppressWarnings("unchecked")
    public List<PurchaseDto> getAllPurchases(String card_id) throws Exception {
        List<Purchase> list = getSession().createQuery(
                "from Purchase purchase where purchase.card_id = :card_id")
                .setParameter("card_id", Long.parseLong(card_id))
                .list();
        return DozerHelper.map(dozerMapper, list, PurchaseDto.class);
    }

    @SuppressWarnings("unchecked")
    public List<PurchaseDto> getAllPurchases(Date from, Date to) throws Exception {
        List<Purchase> list = getSession().createQuery(
                "from Purchase purchase where purchase.date between :from and :to")
                .setParameter("from", from)
                .setParameter("to", to)
                .list();
        return DozerHelper.map(dozerMapper, list, PurchaseDto.class);
    }

    public void update(Purchase purchase) {
        getSession().update(purchase);
        return;
    }
}

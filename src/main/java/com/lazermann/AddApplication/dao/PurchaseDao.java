package com.lazermann.AddApplication.dao;

import com.lazermann.AddApplication.dto.PurchaseDto;
import com.lazermann.AddApplication.helpers.DozerHelper;
import com.lazermann.AddApplication.model.Employee;
import com.lazermann.AddApplication.model.Password;
import com.lazermann.AddApplication.model.Purchase;
import org.dozer.DozerBeanMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
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
        Purchase purchase = new Purchase("1",200);
        purchase.setEmployeeId("1111");
        //LocalDateTime dateTime = LocalDateTime.now();
        Calendar calendar = Calendar.getInstance();
        purchase.setDate(calendar);
        getSession().save(purchase);
        Purchase purchase2 = new Purchase("1",200);
        purchase2.setEmployeeId("1111");
        purchase2.setDate(calendar);
        getSession().save(purchase2);
    }

    public PurchaseDto addPurchase(String card_id, String price, String employeeId) throws Exception  {
        Purchase purchase = new Purchase(card_id,Float.parseFloat(price));
        purchase.setEmployeeId(employeeId);
        //LocalDateTime dateTime = LocalDateTime.now();
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar);
        //Date date = new Date();
        //date.setTime(date.getTime());
        purchase.setDate(calendar);
        getSession().save(purchase);
        return dozerMapper.map(purchase, PurchaseDto.class);
    }

    @SuppressWarnings("unchecked")
    public Purchase getPurchase(String card_id, String purchaseId) throws Exception {
        return (Purchase) getSession().createQuery(
                "from Purchase purchase where purchase.badgeId = :card_id and purchase.id = :purchaseId")
                .setParameter("card_id", card_id)
                .setParameter("purchaseId", Long.parseLong(purchaseId))
                .uniqueResult();

    }

    @SuppressWarnings("unchecked")
    public List<PurchaseDto> getAllPurchases(String card_id, String employeeId) throws Exception {
        List<Purchase> list = getSession().createQuery(
                "from Purchase purchase where purchase.badgeId = :card_id and purchase.employeeId = :employeeId")
                .setParameter("card_id", card_id)
                .setParameter("employeeId", employeeId)
                .list();
        return DozerHelper.map(dozerMapper, list, PurchaseDto.class);
    }

    public List<PurchaseDto> getAllPurchases(String employeeId) throws Exception {
        List<Purchase> list = getSession().createQuery(
                "from Purchase purchase where purchase.employeeId = :employeeId")
                .setParameter("employeeId", employeeId)
                .list();
        return DozerHelper.map(dozerMapper, list, PurchaseDto.class);
    }

    @SuppressWarnings("unchecked")
    public List<PurchaseDto> getAllPurchases(Calendar from, Calendar to, String employeeId) throws Exception {
        List<Purchase> list = getSession().createQuery(
                "from Purchase purchase where purchase.date between :from and :to and employeeId = :employeeId")
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("employeeId", employeeId)
                .list();
        return DozerHelper.map(dozerMapper, list, PurchaseDto.class);
    }

    public void update(Purchase purchase) {
        getSession().update(purchase);
        return;
    }

    public Password getPassword(String password) {
        return (Password)getSession().createQuery(
                "from Password pass where pass.password = :password")
                .setParameter("password", password)
                .uniqueResult();
    }

    public Employee getEmployee(String employeeId) {
        return (Employee)getSession().createQuery(
        "from Employee emp where emp.employeeId = :employeeId")
                .setParameter("employeeId", employeeId)
                .uniqueResult();
    }

    public void saveEmployee(Employee employee) {
        getSession().save(employee);
    }

    public void updateEmployee(Employee employee) {
        getSession().update(employee);
    }
}

package com.lazermann.cookies.dao;

import com.lazermann.cookies.dto.CookieInfoDto;
import com.lazermann.cookies.dto.YtCounterDto;
import com.lazermann.cookies.helpers.DozerHelper;
import com.lazermann.cookies.model.CookieInfo;
import com.lazermann.cookies.model.YtCounter;
import org.dozer.DozerBeanMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class Dao {

    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    DozerBeanMapper dozerMapper;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(CookieInfo coockieInfo) throws Exception {
        getSession().save(coockieInfo);
        //return;
    }





    public void delete(CookieInfo coockieInfo) throws Exception {
        getSession().delete(coockieInfo);
        //return;
    }

    public void updateCookie(String proxy, String coockie) {

        CookieInfo res = (CookieInfo) getSession().createQuery(
                "from CookieInfo c where c.proxy = :proxy")
                .setParameter("proxy", proxy)
                .uniqueResult();

        if(res != null)
        {
            res.setCookie(coockie);
            getSession().update(res);
        }

    }

    @SuppressWarnings("unchecked")
    public CookieInfoDto getCookieByProxy(String proxy) {
        CookieInfo res = (CookieInfo) getSession().createQuery(
                "from CookieInfo c where c.proxy = :proxy")
                .setParameter("proxy", proxy)
                .uniqueResult();

        if(res == null) return null;
        return dozerMapper.map(res, CookieInfoDto.class);
    }

    public YtCounterDto getYtCounterByURL(String url) {
        YtCounter res = (YtCounter) getSession().createQuery(
                "from YtCounter c where c.videoURL = :videoURL")
                .setParameter("videoURL", url)
                .uniqueResult();

        return dozerMapper.map(res, YtCounterDto.class);
    }

    public long increaseYtCounter(String url) {
        YtCounter res = (YtCounter) getSession().createQuery(
                "from YtCounter c where c.videoURL = :videoURL")
                .setParameter("videoURL", url)
                .uniqueResult();

        if(res == null)
        {
            res = new YtCounter();
            res.setCounter(1);
            res.setVideoURL(url);

            getSession().save(res);
            return 1;
        }
        else
        {
          long c = res.getCounter()+1;
          res.setCounter(c);
          getSession().update(res);
          return c;
        }
    }


    @SuppressWarnings("unchecked")
    public List<CookieInfoDto> getAllCookies() {
        List<CookieInfo> list = getSession().createQuery("from CookieInfo").list();
        return DozerHelper.map(dozerMapper, list, CookieInfoDto.class);
    }

    public List<String> getAllProxies()
    {
        List<CookieInfo> list = getSession().createQuery("from CookieInfo").list();

        return list.stream().map(CookieInfo::getProxy).collect(Collectors.toList());
    }


    public void updateCookie(CookieInfo coockieInfo) {
        getSession().update(coockieInfo);
        return;
    }



}

package com.lazermann.myio.master.dao;

import com.lazermann.myio.master.dto.TeamDto;
import com.lazermann.myio.master.model.Team;
import org.dozer.DozerBeanMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional
public class TeamDao
{
    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    DozerBeanMapper dozerMapper;


    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public Long create(String teamLeader) throws Exception
    {
        Team t = new Team();

        List<String> teamNames = new ArrayList<>();
        teamNames.add(teamLeader);
        t.setTeamMemberNames(teamNames);

        t.setTeamLeaderName(teamLeader);

        return (Long) getSession().save(t);
    }

    public void update(Team team) throws Exception
    {
        getSession().update(team);
        getSession().flush();
    }

    public void remove(Team team) throws Exception
    {
        getSession().remove(team);
        getSession().flush();
    }

    public TeamDto getTeamDtoById(Long id) throws Exception
    {
        return dozerMapper.map(getSession().get(Team.class, id), TeamDto.class);
    }


    public Team getTeamById(Long id) throws Exception
    {
        return getSession().get(Team.class, id);
    }
 }

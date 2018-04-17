package com.lazermann.myio.master.controllers;

import com.lazermann.myio.master.dao.ServerDao;
import com.lazermann.myio.master.dao.TeamDao;
import com.lazermann.myio.master.dto.HttpServerDto;
import com.lazermann.myio.master.dto.TeamDto;
import com.lazermann.myio.master.model.GameType;
import com.lazermann.myio.master.model.Region;
import com.lazermann.myio.master.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value="/team")
public class TeamController
{

    public static final int MAX_TEAM_SIZE = 4;

    @Autowired
    TeamDao teamDao;

    @Autowired
    ServerDao serverDao;


   /*
    *  Creates new team
    *  Returns id of team, which team leader needs to give his teammates
    */
    @RequestMapping(value="/create", method = RequestMethod.GET)
    public ResponseEntity createTeam(String leaderName)
    {
        if(leaderName == null)
            return new ResponseEntity<>("Provide leader name!" , HttpStatus.BAD_REQUEST);
        Long teamId;
        try {
            teamId =  teamDao.create(leaderName);
        }  catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(teamId+"" , HttpStatus.OK );
    }


   /*
    *  Joins to given team
    *  Used by all members except team leader
    *  Returns:
    * 1 team info
    * 2 404 if team is not found
    * 3 500 if team is full/not active
    */
    @RequestMapping(value="/join", method = RequestMethod.GET)
    public ResponseEntity joinTeam(long teamId, String newTeamMember)
    {
        if(newTeamMember == null || teamId ==0)
            return new ResponseEntity<>("Provide newTeamMember and teamId!" , HttpStatus.BAD_REQUEST);

        Team team;
        TeamDto res;
        try {
            team = teamDao.getTeamById(teamId);
            if(team == null)
                return new ResponseEntity<>("No such team" , HttpStatus.NOT_FOUND);

            if( team.getTeamMemberNames().size() >= MAX_TEAM_SIZE)
                return new ResponseEntity<>("Team is full" , HttpStatus.INTERNAL_SERVER_ERROR);

            team.getTeamMemberNames().add(newTeamMember);
            teamDao.update(team);

            res = teamDao.getTeamDtoById(teamId);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>(res , HttpStatus.OK);
    }

  /*
   *  Request for latest team info
   *  Used by all team members
   *  Returns
   *  1 team info
   *  2 404 if team is not found
   */
    @RequestMapping(value="/getUpdate", method = RequestMethod.GET)
    public ResponseEntity updateTeamInfo(long teamId)
    {

        TeamDto team;
        try {
            team = teamDao.getTeamDtoById(teamId);
            if(team == null)
                return new ResponseEntity<>("No such team" , HttpStatus.NOT_FOUND);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>(team , HttpStatus.OK);
    }


 /*
  *  Request for leaving team. If team leader leaving team, random member should became new team lead
  *
  */
    @RequestMapping(value="/leave", method = RequestMethod.GET)
    public ResponseEntity leaveTeam(long teamId, String leftTeamMember/*, boolean isTeamLeader*/)
    {
        if(leftTeamMember == null)
            return new ResponseEntity<>("Provide leftTeamMember name!" , HttpStatus.BAD_REQUEST);
        try {
            Team team = teamDao.getTeamById(teamId);
            if(team == null)
                return new ResponseEntity<>("No such team" , HttpStatus.NOT_FOUND);
            if(!team.getTeamMemberNames().contains(leftTeamMember))
                return new ResponseEntity<>("No such member" , HttpStatus.NOT_FOUND);

            team.getTeamMemberNames().remove(leftTeamMember);

            //team leader left, need either to choose new leader,
            // or delete team if there is no anyone left in it
            if(leftTeamMember.equals(team.getTeamLeaderName())) //todo make more reliable check
            {
                if(team.getTeamMemberNames().isEmpty()){
                    teamDao.remove(team);
                    return  new ResponseEntity<>("Left successfully" , HttpStatus.OK);
                }
                else
                {
                    team.setTeamLeaderName(team.getTeamMemberNames().get(0));
                }

            }

            teamDao.update(team);

        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<>("Left successfully" , HttpStatus.OK);
    }



/*
 *  Request for start match. Used by team leader only
 *  At this point we should find non-full server in given region with given game type,
 *  and
 *  1) set its URL in  serverToConnect field of Team entity.
 *  2) set ingame to true;
 *  on next update (updateTeamInfo method) each team member will get server to connect
 *
 *  On connecting it will pass teamId, team size, autofill, gamemode options to HttpServer, and its LoadBalancer will move
 *  all team members to same GameWorld instance and to same squad
 *
 *  //todo maybe we should reserve player slots in HttpServer entity
 */
    @RequestMapping(value="/start", method = RequestMethod.GET)
    public ResponseEntity startTeamMatch(long teamId, Region region, GameType gameType, boolean autofill)
    {

        if(region == null || gameType == null)
            return new ResponseEntity<>("No needed parameters" , HttpStatus.BAD_REQUEST);

        Team team;
        try {
            team = teamDao.getTeamById(teamId);
            if(team == null)
                return new ResponseEntity<>("No such team" , HttpStatus.NOT_FOUND);

            HttpServerDto s = serverDao.getServerToConnect(region,gameType);
            if (s == null)
                return new ResponseEntity<>("Cannot find server to join in " + region , HttpStatus.NOT_FOUND);


            team.setAutoFill(autofill);
            team.setServerToConnect(s.getURL());
            team.setGameType(gameType);
            team.setIngame(true);

            teamDao.update(team);
        }

        catch (Exception ex)
        {
            return new ResponseEntity<>(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>("Match marked as started!" , HttpStatus.OK);
    }
}

package com.may.apimanagementsystem.dao;


import com.may.apimanagementsystem.po.TeamMember;
import com.may.apimanagementsystem.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberMapper {

    boolean insertTeamMember(TeamMember teamMember);

    boolean deleteTeamMember(TeamMember teamMember);

    List<User> selectTeamMember(@Param("teamId") int teamId);
}

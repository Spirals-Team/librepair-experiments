package com.cmpl.web.core.responsibility;

import java.util.List;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.Responsibility;

public class ResponsibilityServiceImpl extends BaseServiceImpl<ResponsibilityDTO, Responsibility>
    implements ResponsibilityService {

  private final ResponsibilityDAO responsibilityDAO;

  public ResponsibilityServiceImpl(ResponsibilityDAO responsibilityDAO, ResponsibilityMapper responsibilityMapper) {
    super(responsibilityDAO, responsibilityMapper);
    this.responsibilityDAO = responsibilityDAO;
  }

  @Override
  public List<ResponsibilityDTO> findByUserId(String userId) {
    return mapper.toListDTO(responsibilityDAO.findByUserId(userId));
  }

  @Override
  public List<ResponsibilityDTO> findByRoleId(String roleId) {
    return mapper.toListDTO(responsibilityDAO.findByRoleId(roleId));
  }

  @Override
  public ResponsibilityDTO findByUserIdAndRoleId(String userId, String roleId) {
    return mapper.toDTO(responsibilityDAO.findByUserIdAndRoleId(userId, roleId));
  }

}

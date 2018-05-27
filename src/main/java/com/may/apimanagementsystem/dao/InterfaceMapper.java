package com.may.apimanagementsystem.dao;

import com.may.apimanagementsystem.po.Interfaces;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface InterfaceMapper {

    boolean insertInterface(Interfaces interfaces);

    boolean deleteInterface(int interfaceId);

    boolean updateInterface(Interfaces interfaces);

    List<Interfaces> getInterfaceList(int projectId);

    Interfaces findInterfaceByInterfaceId(int interfaceId);
}

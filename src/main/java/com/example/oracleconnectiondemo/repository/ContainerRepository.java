package com.example.oracleconnectiondemo.repository;

import com.example.oracleconnectiondemo.DTO.DamagedContainerDTO;
import com.example.oracleconnectiondemo.domain.Container;

import java.sql.SQLException;
import java.util.List;

public interface ContainerRepository {

    List<Container> findAll(String month,String year) throws SQLException, ClassNotFoundException;
    Container findByContainerNumber(String containerNumber) throws SQLException, ClassNotFoundException;
    List<String> getAllShippingLines() throws SQLException, ClassNotFoundException;
    List<DamagedContainerDTO> getAllDamagedContainers(String month, String year) throws SQLException, ClassNotFoundException;
}

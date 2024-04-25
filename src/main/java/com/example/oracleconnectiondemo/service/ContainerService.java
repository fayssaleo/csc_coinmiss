package com.example.oracleconnectiondemo.service;

import com.example.oracleconnectiondemo.DTO.ContainerDTO;
import com.example.oracleconnectiondemo.DTO.DamagedContainerDTO;
import com.example.oracleconnectiondemo.repository.ContainerRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.oracleconnectiondemo.DTO.ContainerDTO.toDTO;


@Service
public class ContainerService {
    private final ContainerRepository containerRepository;

    public ContainerService(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    public List<ContainerDTO> loadAll(String month,String year) throws SQLException, ClassNotFoundException {
        List<ContainerDTO> containerDTOS =  containerRepository.findAll(month,year).stream().map(ContainerDTO::toDTO).collect(Collectors.toList());
        List<DamagedContainerDTO> damagedContainerDTOS = getDamagedContainers(month,year);
        for (DamagedContainerDTO damagedContainerDTO : damagedContainerDTOS)
            for (ContainerDTO containerDTO : containerDTOS)
                if (damagedContainerDTO.containerId.equals(containerDTO.containerNumber) && damagedContainerDTO.invoiceCategory.equals(containerDTO.invoiceCategory) && damagedContainerDTO.fullOrEmpty.equals(containerDTO.fullOrEmpty)) {
                    containerDTO.dmg = true;
                }
        return containerDTOS;
    }

    public List<DamagedContainerDTO> getDamagedContainers(String month,String year) throws SQLException, ClassNotFoundException {
        return containerRepository.getAllDamagedContainers(month,year);
    }
    public ContainerDTO getContainerByNumber(String containerNumber) throws SQLException, ClassNotFoundException {
        return toDTO(containerRepository.findByContainerNumber(containerNumber));
    }
    public List<String> getAllShippingLines() throws SQLException, ClassNotFoundException {
        return containerRepository.getAllShippingLines();
    }

}

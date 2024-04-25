package com.example.oracleconnectiondemo.web;

import com.example.oracleconnectiondemo.DTO.ContainerDTO;
import com.example.oracleconnectiondemo.service.ContainerService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ContainerResource {
    private final ContainerService containerService;

    public ContainerResource(ContainerService containerService) {
        this.containerService = containerService;
    }

    @GetMapping("/all")
    public List<ContainerDTO> loadAllContainers(@RequestParam("month") String month,@RequestParam("year") String year) throws SQLException, ClassNotFoundException {
        return containerService.loadAll(month,year);
    }
    @GetMapping("/container/{id}")
    public ContainerDTO getContainerByNumber(@PathVariable("id")String containerNumber) throws SQLException, ClassNotFoundException {
        return containerService.getContainerByNumber(containerNumber);
    }
    @GetMapping ("/shippingLines")
    public List<String> getShippingLines() throws SQLException, ClassNotFoundException {
        return  containerService.getAllShippingLines();
    }
}

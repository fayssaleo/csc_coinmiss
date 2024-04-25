package com.example.oracleconnectiondemo.DTO;

import com.example.oracleconnectiondemo.domain.Container;

import java.util.Date;

import static com.example.oracleconnectiondemo.helper.Fixtures.calculteInvoiceStorageDuration;
import static com.example.oracleconnectiondemo.helper.Fixtures.getInvoiceCategory;

public class ContainerDTO {

    public String shippingLine;
    public String containerNumber;
    public String type;
    public String length;
    public String invoiceCategory;
    public String fullOrEmpty;
    public boolean reef;
    public boolean imdg;
    public boolean oog;
    public boolean dmg;
    public Date incDate;
    public Date outDate;
    public int invoiceStorageDuration;

    public static ContainerDTO toDTO(Container container) {
        ContainerDTO containerDTO = new ContainerDTO();
        containerDTO.shippingLine = container.getShippingLine();
        containerDTO.containerNumber = container.getContainerNumber();
        containerDTO.length = container.getLength();
        containerDTO.type = container.getType();
        containerDTO.invoiceCategory = getInvoiceCategory(container.getInvoiceCategory());
        containerDTO.fullOrEmpty = container.getFullOrEmpty().equals("F") ? "Full" : "Empty";
        containerDTO.reef = container.getReef().equals("Y");
        containerDTO.imdg = container.getImdg().equals("Y");
        containerDTO.oog = container.getOog().equals("Y");
        containerDTO.dmg = false;
        containerDTO.incDate = container.getIncDate();
        containerDTO.outDate = container.getOutDate();
        containerDTO.invoiceStorageDuration = calculteInvoiceStorageDuration(containerDTO.incDate,containerDTO.outDate);
        return containerDTO;
    }
}

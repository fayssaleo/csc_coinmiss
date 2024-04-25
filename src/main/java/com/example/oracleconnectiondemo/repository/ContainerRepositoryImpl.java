package com.example.oracleconnectiondemo.repository;

import com.example.oracleconnectiondemo.DTO.DamagedContainerDTO;
import com.example.oracleconnectiondemo.domain.Container;
import com.example.oracleconnectiondemo.config.DbConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static com.example.oracleconnectiondemo.helper.Fixtures.*;

@Repository
public class ContainerRepositoryImpl implements ContainerRepository{
    @Value("${mode}")
    String mode;
    @Override
    public List<Container> findAll(String month,String year) throws SQLException, ClassNotFoundException {

        Map<String, String> map = getNextMonthAndYear(mode,month,year);
        month = map.get("month");
        year = map.get("year");
        String nextMonth = map.get("nextMonth");
        String nextYear = map.get("nextYear");

        List<Container> containers = new ArrayList<>();
        Connection connection = DbConnection.createConnection();
        Statement stmt=connection.createStatement();
        String firstCondition = "(ARR_TS <= '01/"+month+"/"+year+"' AND  (DEP_TS BETWEEN '01/"+month+"/"+year+"' AND '01/"+nextMonth+"/"+nextYear+"'))";
        String secondCondition = "((ARR_TS BETWEEN '01/"+month+"/"+year+"' AND '1/"+nextMonth+"/"+nextYear+"') AND (DEP_TS >= '1/"+nextMonth+"/"+nextYear+"' OR DEP_TS IS NULL)";
        String thirdCondition = "(ARR_TS BETWEEN '01/"+month+"/"+year+"' AND '1/"+nextMonth+"/"+nextYear+"') AND (DEP_TS BETWEEN '01/"+month+"/"+year+"' AND '01/"+nextMonth+"/"+nextYear+"'))";
        String fourthCondition = "(ARR_TS <= '01/"+month+"/"+year+"' AND (DEP_TS >= '1/"+nextMonth+"/"+nextYear+"' OR DEP_TS IS NULL))";
        String query = "SELECT LINESITE_ID,CTR_ID,CTR_LENGTH,CTR_TYPE_CD,CTR_INVOICE_CATEGORY_CD,CTR_FE_IND,CTR_REEFER_FLG,CTR_DGS_FLG,CTR_OOG_FLG,CTR_DAMAGE_FLG,ARR_TS,DEP_TS from TC3_CTR_M c JOIN LINESITE_MP l ON c.LINESITE_NO = l.LINESITE_NO WHERE ARR_TS IS NOT NULL AND INIT_LINESITE_NO IS NOT NULL  AND CTR_LIFESTATUS_CD IN ('A','B') AND CTR_RESTOW_FLG='N' AND "+"("+firstCondition+" OR "+secondCondition+" OR "+thirdCondition+" OR "+fourthCondition+")";

        ResultSet resultSet=stmt.executeQuery(query);
        while(resultSet.next()) {
            Container container = new Container();
            container.setShippingLine(resultSet.getString(1));
            container.setContainerNumber(resultSet.getString(2));
            container.setLength(resultSet.getString(3));
            container.setType(resultSet.getString(4));
            container.setInvoiceCategory(resultSet.getString(5));
            container.setFullOrEmpty(resultSet.getString(6));
            container.setReef(resultSet.getString(7));
            container.setImdg(resultSet.getString(8));
            container.setOog(resultSet.getString(9));
            container.setDmg(resultSet.getString(10));
            container.setIncDate(resultSet.getTimestamp(11));
            container.setOutDate(resultSet.getTimestamp(12) == null ? new Date() :resultSet.getTimestamp(12) );
            containers.add(container);
        }
       return  containers;
    }

    @Override
    public List<DamagedContainerDTO> getAllDamagedContainers(String month,String year) throws SQLException, ClassNotFoundException {
       Map<String, String> map = getNextMonthAndYear(mode,month,year);
       month = map.get("month");
       year = map.get("year");
       String nextMonth = map.get("nextMonth");
       String nextYear = map.get("nextYear");
       List<DamagedContainerDTO> containers = new ArrayList<>();
       Connection connection = DbConnection.createConnection();
       Statement stmt=connection.createStatement();
       String firstCondition = "(ARR_TS <= '01/"+month+"/"+year+"' AND  (DEP_TS BETWEEN '01/"+month+"/"+year+"' AND '01/"+nextMonth+"/"+nextYear+"'))";
       String secondCondition = "((ARR_TS BETWEEN '01/"+month+"/"+year+"' AND '1/"+nextMonth+"/"+nextYear+"') AND (DEP_TS >= '1/"+nextMonth+"/"+nextYear+"' OR DEP_TS IS NULL)";
       String thirdCondition = "(ARR_TS BETWEEN '01/"+month+"/"+year+"' AND '1/"+nextMonth+"/"+nextYear+"') AND (DEP_TS BETWEEN '01/"+month+"/"+year+"' AND '01/"+nextMonth+"/"+nextYear+"'))";
       String fourthCondition = "(ARR_TS <= '01/"+month+"/"+year+"' AND (DEP_TS >= '1/"+nextMonth+"/"+nextYear+"' OR DEP_TS IS NULL))";
       String moveCondition = "(CTRMOVE_TS <'1/"+nextMonth+"/2021' OR CTRMOVE_TS = NULL)";
       String query = "SELECT m.CTR_NO,CTR_ID,CTR_INVOICE_CATEGORY_CD,CTR_FE_IND,ARR_TS,DEP_TS,CTRMOVE_STACK_FROM,CTRMOVE_STACK_to,CTRMOVE_TS from TC3_CTRMOVE_D m join TC3_CTR_M c on m.ctr_no = c.ctr_no where (CTRMOVE_STACK_FROM in ('SPA','SPB','SPC','N1','N2','N3','N4','N5','N6','N7','N8','N9' ,'WA') OR CTRMOVE_STACK_TO in ('SPA','SPB','SPC','N1','N2','N3','N4','N5','N6','N7','N8','N9' ,'WA')) AND CTR_DAMAGE_FLG = 'Y' AND "+moveCondition+" AND"+"("+firstCondition+" OR "+secondCondition+" OR "+thirdCondition+" OR "+fourthCondition+") ORDER BY CTRMOVE_TS DESC";
       ResultSet resultSet=stmt.executeQuery(query);
       while(resultSet.next()) {
           DamagedContainerDTO damagedContainerDTO = new DamagedContainerDTO();
           damagedContainerDTO.containerNo = resultSet.getString(1);
           damagedContainerDTO.containerId = resultSet.getString(2);
           damagedContainerDTO.invoiceCategory = getInvoiceCategory(resultSet.getString(3));
           damagedContainerDTO.fullOrEmpty = resultSet.getString(4).equals("F") ? "Full" : "Empty";
           damagedContainerDTO.incomingDate = resultSet.getTimestamp(5);
           damagedContainerDTO.outGoingDate = resultSet.getTimestamp(6);
           damagedContainerDTO.stackFrom = resultSet.getString(7);
           damagedContainerDTO.stackTo= resultSet.getString(8);
           damagedContainerDTO.moveTime= resultSet.getTimestamp(9);
           containers.add(damagedContainerDTO);
       }
       for(int i=0;i<containers.size()-1;i++)
           for(int j=i+1;j<containers.size();j++)
               if(containers.get(i).containerNo.equals(containers.get(j).containerNo) && containers.get(i).moveTime.compareTo(containers.get(j).moveTime)>=0) {
                   containers.remove(containers.get(j));
                   j--;
               }
       List<DamagedContainerDTO> damagedContainers = new ArrayList<>();
       for(DamagedContainerDTO damagedContainerDTO : containers) {
           if(stackInZones(damagedContainerDTO.stackTo)) damagedContainers.add(damagedContainerDTO);
           else if (!stackInZones(damagedContainerDTO.stackTo) && stackInZones(damagedContainerDTO.stackFrom)) damagedContainers.add(damagedContainerDTO);
       }
       return damagedContainers;
   }

    @Override
    public Container findByContainerNumber(String containerNumber) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.createConnection();
        Statement stmt=connection.createStatement();
        ResultSet resultSet=stmt.executeQuery("SELECT LINESITE_ID,LINESITE_ID,CTR_LENGTH,CTR_TYPE_CD,CTR_INVOICE_CATEGORY_CD,CTR_FE_IND,CTR_REEFER_FLG,CTR_DGS_FLG,CTR_OOG_FLG,CTR_DAMAGE_FLG,ARR_TS,DEP_TS from TC3_CTR_M c JOIN LINESITE_MP l ON c.INIT_LINESITE_NO = l.LINESITE_NO WHERE CTR_ID="+"'"+containerNumber+"'");
        Container container = new Container();
        while(resultSet.next()) {
            container.setShippingLine(resultSet.getString(1));
            container.setContainerNumber(resultSet.getString(2));
            container.setLength(resultSet.getString(3));
            container.setType(resultSet.getString(4));
            container.setInvoiceCategory(resultSet.getString(5));
            container.setFullOrEmpty(resultSet.getString(6));
            container.setReef(resultSet.getString(7));
            container.setImdg(resultSet.getString(8));
            container.setOog(resultSet.getString(9));
            container.setIncDate(resultSet.getTimestamp(11));
            container.setOutDate(resultSet.getTimestamp(12) == null ? new Date() :resultSet.getTimestamp(12) );

        }
        return  container;
    }

    @Override
    public List<String> getAllShippingLines() throws SQLException, ClassNotFoundException {
        List<String> shippingLines = new ArrayList<>();
        Connection connection = DbConnection.createConnection();
        Statement stmt=connection.createStatement();
        ResultSet resultSet=stmt.executeQuery("SELECT DISTINCT LINESITE_ID FROM LINESITE_MP ORDER BY LINESITE_ID");
        while(resultSet.next()) {
            String shippingLine = resultSet.getString("LINESITE_ID");
            if(shippingLine != null) shippingLines.add(shippingLine);
        }
        return shippingLines;
    }
}

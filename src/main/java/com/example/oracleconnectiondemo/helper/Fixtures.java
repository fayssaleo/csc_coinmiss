package com.example.oracleconnectiondemo.helper;



import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Fixtures {

    public static int calculteInvoiceStorageDuration(Date firstDate, Date secondDate) {
        int i = 0;
        for(LocalDate date= convertToLocalDate(firstDate); !date.isAfter(convertToLocalDate(secondDate)); date = date.plusDays(1)) {
            i++;
        }
        return i;
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


    public static String getInvoiceCategory(String category) {
        switch (category) {
            case "T": return "Transshipment";
            case "I": return "Import";
            case "E": return "Export";
            case "D" : return "Transshipment";
            default: return "N/A";
        }
    }
    public static Map<String,String> getNextMonthAndYear(String mode,String month,String year) {
        Map<String, String> map= new HashMap<>();
        String[] months = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        if(mode.equals("dev")) {
            map.put("month",month);
            map.put("year",year);
            map.put("nextMonth",Integer.parseInt(month) == 12 ? "1" : String.valueOf(Integer.parseInt(month)+1));
            map.put("nextYear",Integer.parseInt(month) == 12 ? String.valueOf(Integer.parseInt(year)+1) : year);
        }

        if(mode.equals("preprod")) {
            map.put("month",months[Integer.parseInt(month)-1]);
            map.put("year",year);
            map.put("nextMonth",Integer.parseInt(month) == 12 ? months[0] : months[Integer.parseInt(month)]);
            map.put("nextYear",Integer.parseInt(month) == 12 ? String.valueOf(Integer.parseInt(year)+1) : year);
        }
        return map;

    }
    public static boolean stackInZones(String stack) {
        String[] zones = {"SPA","SPB","SPC","N1","N2","N3","N4","N5","N6","N7","N8","N9","WA"};
        boolean inZones = false;
        for(String zone :zones) {
            if(zone.equals(stack)) {
                inZones = true;
                break;
            }
        }
        return inZones;
    }
}

package com.example.texnologia_logismikou_2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> to_tmima = new ArrayList<String>();
        to_tmima.add("Μήνυμα Προέδρου");
        to_tmima.add("Διακρίσεις");

        List<String> anakoinwseis = new ArrayList<String>();
        anakoinwseis.add("Νέα του Τμήματος");
        anakoinwseis.add("Διοικητικές Πράξεις");

        List<String> sxoles_child_list = new ArrayList<String>();
        sxoles_child_list.add("Προπτυχιακά");
        sxoles_child_list.add("Μεταπτυχιακά");
        sxoles_child_list.add("Διδακτορικές σπουδές");
        sxoles_child_list.add("eLearning");
        sxoles_child_list.add("Οδηγός σπουδών");


        List<String> didaskontes_child_list = new ArrayList<String>();
        didaskontes_child_list.add("Διδάσκοντες");
        didaskontes_child_list.add("Επίτιμοι Διδάκτορες");
        didaskontes_child_list.add("Μεταδιδακτορικοί ερευνητές");
        didaskontes_child_list.add("Υποψήφιοι διδάκτορες");
        didaskontes_child_list.add("Γραμματεία");

        List<String> ereuna_child_list = new ArrayList<String>();
        ereuna_child_list.add("Τομείς έρευνας");
        ereuna_child_list.add("Επιλεγμένες δημοσιεύσεις");
        ereuna_child_list.add("Ερευνητικά έργα");
        ereuna_child_list.add("Ερευνητικά εργαστήρια");

        List<String> drastiriotites_child_list = new ArrayList<String>();
        drastiriotites_child_list.add("Επιστημονικά συνέδρια");
        drastiriotites_child_list.add("Ομιλίες,διαλέξεις");
        drastiriotites_child_list.add("Ημερίδες");
        drastiriotites_child_list.add("Άλλες εκδηλώσεις");

        List<String> yphresies_child_list = new ArrayList<String>();
        yphresies_child_list.add("Υπηρεσίες τμήματος και Πανεπιστημίου");
        yphresies_child_list.add("Συνεργαζόμενες εταιρίες");

        List<String> arxiki = new ArrayList<String>();
        expandableListDetail.put("ΚΕΝΤΡΙΚΗ",  arxiki);
        List<String> contact = new ArrayList<String>();
        expandableListDetail.put("ΕΠΙΚΟΙΝΩΝΙΑ",  contact);
        expandableListDetail.put("ΤΟ ΤΜΗΜΑ", to_tmima);
        expandableListDetail.put("ΑΝΑΚΟΙΝΩΣΕΙΣ", anakoinwseis);
        expandableListDetail.put("ΣΠΟΥΔΕΣ", sxoles_child_list);
        expandableListDetail.put("ΜΕΛΗ", didaskontes_child_list);
        expandableListDetail.put("ΕΡΕΥΝΑ",  ereuna_child_list);
        expandableListDetail.put("ΔΡΑΣΤΗΡΙΟΤΗΤΕΣ", drastiriotites_child_list);
        expandableListDetail.put("ΥΠΗΡΕΣΙΕΣ", yphresies_child_list);
        return expandableListDetail;
    }
}

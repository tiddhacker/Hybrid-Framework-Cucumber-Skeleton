package com.automation.utils;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AssertUtils {
    public String compareTwoMaps(HashMap expected, HashMap actual) {
        MapDifference<String, String> diff = Maps.difference(expected, actual);

        String status = diff.toString();
        if (!status.equals("equal")) {
            String results = "";
            Map<String, MapDifference.ValueDifference<String>> entriesDiffering = diff.entriesDiffering();
            if (diff.entriesOnlyOnLeft().size() > 0) {
                results = "\nOnly found in Expected Map :" + diff.entriesOnlyOnLeft();
            }
            if (diff.entriesOnlyOnRight().size() > 0) {
                results = results + "\nOnly found in Actual Map :" + diff.entriesOnlyOnRight();
            }
            if (entriesDiffering.size() > 0) {
                results = results + "\nValues not matching [Key=(Expected_Value, Actual_Value)] : " + entriesDiffering;
            }
            status = results;
        }
        return status;
    }

    public String compareTwoListOfMaps(List<HashMap<String, String>> expectedLst, List<HashMap<String, String>> actualLst, String resultsKeyName) {
        String res = "";
        int cnt = 0;
        if (actualLst.size() == expectedLst.size()) {
            for (int i = 0; i < actualLst.size(); i++) {
                cnt++;
                String printNameReportOnly = expectedLst.get(i).get(resultsKeyName);
                res = res + "\n\nRecord # : " + cnt + " For Key [" + printNameReportOnly + "] - ";
                res = res + compareTwoMaps(expectedLst.get(i), actualLst.get(i));
            }
        } else {
            res = " - REASON : [List of Maps size are not equal.] actualLst = " + actualLst.size() + " expectedLst = " + expectedLst.size() + " \n [actualLst] || " + StringUtils.join(actualLst, "\n") + " \n [expectedLst] || " + StringUtils.join(expectedLst, "\n");
        }
        return res;
    }

    public String compareTwoLists( List<String> listExpected,List<String> listActual){
        String compareResults = "";
        List<String> diffExpected = listExpected.stream()
                .filter(element -> !listActual.contains(element))
                .collect(Collectors.toList());

        List<String> diffActual = listActual.stream()
                .filter(element -> !listExpected.contains(element))
                .collect(Collectors.toList());

        if (diffExpected.size() == 0 && diffActual.size() == 0) {
            compareResults = "EQUAL";
        } else {
            if (diffExpected.size() != 0) {
                compareResults = compareResults + " | Extra values in Expected list - " + diffExpected;
            }
            if (diffActual.size() != 0) {
                compareResults = compareResults + " | Extra values in Actual list - " + diffActual;
            }
        }
        return compareResults;
    }
}


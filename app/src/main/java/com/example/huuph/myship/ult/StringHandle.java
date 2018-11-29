package com.example.huuph.myship.ult;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHandle {
    public static String searchPhone(String inString) {
        List<String> tmps = new ArrayList<>();
        List<Integer> points = new ArrayList<>();


        String tmp;
        String outString = null;

        inString.trim();
        inString=inString.replace('\n',' ');
        points = search0(inString);
        for (int point : points) {
            if(inString.indexOf(' ',point)==-1){
                tmp = inString.substring(point, inString.length());
                tmps.add(tmp);
                continue;
            }
            tmp = inString.substring(point, inString.indexOf(' ', point));
            tmps.add(tmp);
        }
        for (String hand : tmps) {
            String pattern = "^[0-9]*$";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(hand);
            if (m.matches()) {
                if (hand.length() > 8 && hand.length() < 12) {
                    return hand;
                }
            }
        }
        return outString;
    }

    private static List<Integer> search0(String a) {
        char[] arr = a.toCharArray();
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '0') {
                res.add(i);
            }
            if (arr[i] == '8') {
                res.add(i);
            }
            if (arr[i] == '+') {
                res.add(i);
            }
        }
        return res;
    }
}

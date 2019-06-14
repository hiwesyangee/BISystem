package utils;

import java.text.DecimalFormat;

public class ttt {
    public static void main(String[] args) {
        double mm = 0d;

        DecimalFormat df = new DecimalFormat("#.00");
        String end = df.format(mm * 100d);
        System.out.println("0"+end);
    }
}

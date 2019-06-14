package biquery.payrankquery;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import properties.DataProcessProperties2;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayRankInterface {
    /**
     * 获取一天内充值排行相关数据
     */
    public static List<PayRankByChannel> getDayPayRank(String time1, String channel) {
        return getManyDayPayRank(time1, time1, channel);
    }

    public static List<PayRank> getDayPayRank(String time1) {
        return getManyDayPayRank(time1, time1);
    }

    /**
     * 获取多天内充值排行相关数据
     *
     * @param time1
     * @param time2
     * @return
     */
    public static List<PayRankByChannel> getManyDayPayRank(String time1, String time2, String channel) {
        List<PayRankByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        Map<String, Double> userPay = new HashMap<>();
        for (String day : days) {
            String start = day + "000000===0";
            String stop = day + "999999===9999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERPAYTIMETABLE, start, stop);
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERPAYTIMETABLE[0])));
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERPAYTIMETABLE[2])));
                String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERPAYTIMETABLE[4])));
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        if (userPay.containsKey(uid)) {
                            double zhiqian = userPay.get(uid);
                            double payNumber = zhiqian + Double.valueOf(amount);
                            userPay.put(uid, payNumber);
                        } else {
                            userPay.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        if (userPay.containsKey(uid)) {
                            double zhiqian = userPay.get(uid);
                            double payNumber = zhiqian + Double.valueOf(amount);
                            userPay.put(uid, payNumber);
                        } else {
                            userPay.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        if (userPay.containsKey(uid)) {
                            double zhiqian = userPay.get(uid);
                            double payNumber = zhiqian + Double.valueOf(amount);
                            userPay.put(uid, payNumber);
                        } else {
                            userPay.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    if (userPay.containsKey(uid)) {
                        double zhiqian = userPay.get(uid);
                        double payNumber = zhiqian + Double.valueOf(amount);
                        userPay.put(uid, payNumber);
                    } else {
                        userPay.put(uid, Double.valueOf(amount));
                    }
                }
            }
        }

        Map<String, Double> sortPayMap = MyUtils.sortByValue(userPay);

        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 1000) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = MyUtils.getYuan(sortPayMap.get(s));
                if (number.equals("0.0")) {
                    number = "0.1";
                }
                list.add(new PayRankByChannel(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }

    public static List<PayRank> getManyDayPayRank(String time1, String time2) {
        List<PayRank> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        Map<String, Double> userPay = new HashMap<>();
        String channel = "all";
        for (String day : days) {
            String start = day + "000000===0";
            String stop = day + "999999===9999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERPAYTIMETABLE, start, stop);
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERPAYTIMETABLE[0])));
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERPAYTIMETABLE[2])));
                String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERPAYTIMETABLE[4])));
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        if (userPay.containsKey(uid)) {
                            double zhiqian = userPay.get(uid);
                            double payNumber = zhiqian + Double.valueOf(amount);
                            userPay.put(uid, payNumber);
                        } else {
                            userPay.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        if (userPay.containsKey(uid)) {
                            double zhiqian = userPay.get(uid);
                            double payNumber = zhiqian + Double.valueOf(amount);
                            userPay.put(uid, payNumber);
                        } else {
                            userPay.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        if (userPay.containsKey(uid)) {
                            double zhiqian = userPay.get(uid);
                            double payNumber = zhiqian + Double.valueOf(amount);
                            userPay.put(uid, payNumber);
                        } else {
                            userPay.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    if (userPay.containsKey(uid)) {
                        double zhiqian = userPay.get(uid);
                        double payNumber = zhiqian + Double.valueOf(amount);
                        userPay.put(uid, payNumber);
                    } else {
                        userPay.put(uid, Double.valueOf(amount));
                    }
                }
            }
        }

        Map<String, Double> sortPayMap = MyUtils.sortByValue(userPay);

        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 1000) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = MyUtils.getYuan(sortPayMap.get(s));
                if (number.equals("0.0")) {
                    number = "0.1";
                }
                list.add(new PayRank(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }






    public static void main(String[] args) {
        List<PayRankByChannel> android = getManyDayPayRank("20180810", "20180810", "weixin");
        for (PayRankByChannel pc : android) {
            System.out.println(pc.getRank() + "===" + pc.getUid() + "===" + pc.getNumber());
        }
        System.out.println("----------------");
        List<PayRank> all = getManyDayPayRank("20180810", "20180810");
        for (PayRank pc : all) {
            System.out.println(pc.getRank() + "===" + pc.getUid() + "===" + pc.getNumber());
        }
    }

}

package biquery.cronsrankquery;

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

public class CronsRankInterface {
    /**
     * 获取一天内充值排行相关数据
     */
    public static List<CronsRankByChannel> getDayCronsRank(String time1, String channel) {
        return getManyDayCronsRank(time1, time1, channel);
    }

    public static List<CronsRank> getDayCronsRank(String time1) {
        return getManyDayCronsRank(time1, time1);
    }

    /**
     * 获取多天内充值排行相关数据
     *
     * @param time1
     * @param time2
     * @return
     */
    public static List<CronsRankByChannel> getManyDayCronsRank(String time1, String time2, String channel) {
        List<CronsRankByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        Map<String, Double> userCrons = new HashMap<>();
        for (String day : days) {
            String start = day + "000000===0";
            String stop = day + "999999===9999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERCRONSTIMETABLE, start, stop);
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERCRONSTIMETABLE[0])));
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERCRONSTIMETABLE[2])));
                String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERCRONSTIMETABLE[4])));
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        if (userCrons.containsKey(uid)) {
                            double zhiqian = userCrons.get(uid);
                            double CronsNumber = zhiqian + Double.valueOf(amount);
                            userCrons.put(uid, CronsNumber);
                        } else {
                            userCrons.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        if (userCrons.containsKey(uid)) {
                            double zhiqian = userCrons.get(uid);
                            double CronsNumber = zhiqian + Double.valueOf(amount);
                            userCrons.put(uid, CronsNumber);
                        } else {
                            userCrons.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        if (userCrons.containsKey(uid)) {
                            double zhiqian = userCrons.get(uid);
                            double CronsNumber = zhiqian + Double.valueOf(amount);
                            userCrons.put(uid, CronsNumber);
                        } else {
                            userCrons.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    if (userCrons.containsKey(uid)) {
                        double zhiqian = userCrons.get(uid);
                        double CronsNumber = zhiqian + Double.valueOf(amount);
                        userCrons.put(uid, CronsNumber);
                    } else {
                        userCrons.put(uid, Double.valueOf(amount));
                    }
                }
            }
        }

        Map<String, Double> sortCronsMap = MyUtils.sortByValue(userCrons);

        int rank = 1;
        for (String s : sortCronsMap.keySet()) {
            if (rank <= 1000) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = MyUtils.getYuan(sortCronsMap.get(s));
                if (number.equals("0.0")) {
                    number = "0.1";
                }
                list.add(new CronsRankByChannel(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }

    public static List<CronsRank> getManyDayCronsRank(String time1, String time2) {
        List<CronsRank> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        Map<String, Double> userCrons = new HashMap<>();
        String channel = "all";
        for (String day : days) {
            String start = day + "000000===0";
            String stop = day + "999999===9999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERCRONSTIMETABLE, start, stop);
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERCRONSTIMETABLE[0])));
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERCRONSTIMETABLE[2])));
                String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERCRONSTIMETABLE[4])));
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        if (userCrons.containsKey(uid)) {
                            double zhiqian = userCrons.get(uid);
                            double CronsNumber = zhiqian + Double.valueOf(amount);
                            userCrons.put(uid, CronsNumber);
                        } else {
                            userCrons.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        if (userCrons.containsKey(uid)) {
                            double zhiqian = userCrons.get(uid);
                            double CronsNumber = zhiqian + Double.valueOf(amount);
                            userCrons.put(uid, CronsNumber);
                        } else {
                            userCrons.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        if (userCrons.containsKey(uid)) {
                            double zhiqian = userCrons.get(uid);
                            double CronsNumber = zhiqian + Double.valueOf(amount);
                            userCrons.put(uid, CronsNumber);
                        } else {
                            userCrons.put(uid, Double.valueOf(amount));
                        }
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    if (userCrons.containsKey(uid)) {
                        double zhiqian = userCrons.get(uid);
                        double CronsNumber = zhiqian + Double.valueOf(amount);
                        userCrons.put(uid, CronsNumber);
                    } else {
                        userCrons.put(uid, Double.valueOf(amount));
                    }
                }
            }
        }

        Map<String, Double> sortCronsMap = MyUtils.sortByValue(userCrons);

        int rank = 1;
        for (String s : sortCronsMap.keySet()) {
            if (rank <= 1000) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = MyUtils.getYuan(sortCronsMap.get(s));
                if (number.equals("0.0")) {
                    number = "0.1";
                }
                list.add(new CronsRank(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }

    public static void main(String[] args) {
        List<CronsRankByChannel> android = getManyDayCronsRank("20180810", "20180810", "weixin");
        for (CronsRankByChannel pc : android) {
            System.out.println(pc.getRank() + "===" + pc.getUid() + "===" + pc.getNumber());
        }
        System.out.println("----------------");
        List<CronsRank> all = getManyDayCronsRank("20180810", "20180810");
        for (CronsRank pc : all) {
            System.out.println(pc.getRank() + "===" + pc.getUid() + "===" + pc.getNumber());
        }
    }

}

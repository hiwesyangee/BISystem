package engine_abandoned.payAndcrons;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import properties.DataProcessProperties;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户充值排行相关数据接口
 */
public class PayRankInterface {
    /**
     * 获取一天内充值排行相关数据
     *
     * @param time1
     * @return
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
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERPAYTIMETABLE, start, stop);
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE[0])));
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE[2])));
                String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE[4])));
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
        for (String day : days) {
            String start = day + "000000===0";
            String stop = day + "999999===9999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERPAYTIMETABLE, start, stop);
            for (Result result : scanner) {
                String rowkey = Bytes.toString(result.getRow());
                String uid = rowkey.split("===")[1];
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE[2])));
                String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE[4])));
                if (!(inChannel.equals("weixin") || inChannel.equals("app"))) {
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
//        List<PayRank> manyDayPayRank = getManyDayPayRank("20190115", "20190115");
//        for (PayRank pr : manyDayPayRank) {
//            System.out.println(pr.getRank() + "|-|-|" + pr.getUid() + "," + pr.getNumber());
//        }
        System.out.println("------------__-------------");
        List<PayRankByChannel> channels = getManyDayPayRank("20190115", "20190115", "weixin");
        for (PayRankByChannel pr : channels) {
            System.out.println(pr.getRank() + "|-|-|" + pr.getUid() + "," + pr.getNumber());
        }
    }
}

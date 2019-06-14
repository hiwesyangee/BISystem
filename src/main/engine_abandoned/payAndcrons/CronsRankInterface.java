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
 * 用户消费排行相关数据接口
 */
public class CronsRankInterface {
    /**
     * 获取一天内消费排行相关数据
     *
     * @param time1
     * @return
     */
    public static List<CronsRankByChannel> getDayCronsRank(String time1, String channel) {
        return getManyDayCronsRank(time1, time1, channel);
    }

    public static List<CronsRank> getDayCronsRank(String time1) {
        return getManyDayCronsRank(time1, time1);
    }

    /**
     * 获取多天内消费排行相关数据
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
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERCRONSTIMETABLE, start, stop);

            for (Result result : scanner) {
                String uid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE[0])));
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE[2])));
                String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE[4])));
                if (inChannel.equals(channel)) {
                    if (userCrons.containsKey(uid)) {
                        double zhiqian = userCrons.get(uid);
                        double payNumber = zhiqian + Double.valueOf(amount);
                        userCrons.put(uid, payNumber);
                    } else {
                        userCrons.put(uid, Double.valueOf(amount));
                    }
                }
            }
        }

        Map<String, Double> sortPayMap = MyUtils.sortByValue(userCrons);
        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 1000) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = MyUtils.getYuan(sortPayMap.get(s));
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
        for (String day : days) {
            String start = day + "000000===0";
            String stop = day + "999999===9999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERCRONSTIMETABLE, start, stop);

            for (Result result : scanner) {
                String rowkey = Bytes.toString(result.getRow());
                String uid = rowkey.split("===")[1];
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE[2])));
                String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE[4])));
                if (!(inChannel.equals("weixin") || inChannel.equals("app"))) {
                    if (userCrons.containsKey(uid)) {
                        double zhiqian = userCrons.get(uid);
                        double payNumber = zhiqian + Double.valueOf(amount);
                        userCrons.put(uid, payNumber);
                    } else {
                        userCrons.put(uid, Double.valueOf(amount));
                    }
                }
            }
        }

        Map<String, Double> sortPayMap = MyUtils.sortByValue(userCrons);

        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 1000) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = MyUtils.getYuan(sortPayMap.get(s));
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
        List<CronsRank> manyDayPayRank = getManyDayCronsRank("20190115", "20190115");
        for (CronsRank pr : manyDayPayRank) {
            System.out.println(pr.getRank() + "|-|-|" + pr.getUid() + "," + pr.getNumber());
        }
        System.out.println("------------__-------------");
        List<CronsRankByChannel> channels = getManyDayCronsRank("20190115", "20190115", "ios");
        for (CronsRankByChannel pr : channels) {
            System.out.println(pr.getRank() + "|-|-|" + pr.getUid() + "," + pr.getNumber());
        }
    }
}

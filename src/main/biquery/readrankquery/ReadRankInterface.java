package biquery.readrankquery;

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

public class ReadRankInterface {
    /**
     * 获取一天订阅排行相关数据
     *
     * @param time1
     * @return
     */
    public static List<ReadRankByChannel> getDayReadRank(String time1, String channel) {
        return getManyDayReadRank(time1, time1, channel);
    }

    public static List<ReadRank> getDayReadRank(String time1) {
        return getManyDayReadRank(time1, time1);
    }

    /**
     * 获取多天充值排行相关数据
     *
     * @param time1
     * @param time2
     * @return
     */
    public static List<ReadRankByChannel> getManyDayReadRank(String time1, String time2, String channel) {
        List<ReadRankByChannel> list = new ArrayList<>();
        String start = time1 + "000000===0";
        String stop = time2 + "999999===9999999";
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERREADTIMETABLE, start, stop);
        Map<String, Integer> userRead = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERREADTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERREADTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERREADTIMETABLE[5])));
            String key = bName + "-" + cid;
            if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                if (inChannel.equals(channel)) {
                    if (userRead.containsKey(key)) {
                        int zhiqian = userRead.get(key);
                        int payNumber = zhiqian + 1;
                        userRead.put(key, payNumber);
                    } else {
                        userRead.put(key, 1);
                    }
                }
            }
            if (channel.equals("weixin")) {  // 查询合并渠道weixin
                if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                    if (userRead.containsKey(key)) {
                        int zhiqian = userRead.get(key);
                        int payNumber = zhiqian + 1;
                        userRead.put(key, payNumber);
                    } else {
                        userRead.put(key, 1);
                    }
                }
            }
            if (channel.equals("app")) { // 查询合并渠道app
                if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                    if (userRead.containsKey(key)) {
                        int zhiqian = userRead.get(key);
                        int payNumber = zhiqian + 1;
                        userRead.put(key, payNumber);
                    } else {
                        userRead.put(key, 1);
                    }
                }
            }
            if (channel.equals("all")) { // 查询所有渠道
                if (userRead.containsKey(key)) {
                    int zhiqian = userRead.get(key);
                    int payNumber = zhiqian + 1;
                    userRead.put(key, payNumber);
                } else {
                    userRead.put(key, 1);
                }
            }
        }
        Map<String, Integer> sortPayMap = MyUtils.sortByValue(userRead);
        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 100) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = String.valueOf(sortPayMap.get(s));
                list.add(new ReadRankByChannel(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }

    public static List<ReadRank> getManyDayReadRank(String time1, String time2) {
        List<ReadRank> list = new ArrayList<>();
        String start = time1 + "000000===0";
        String stop = time2 + "999999===9999999";
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERREADTIMETABLE, start, stop);
        Map<String, Integer> userRead = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERREADTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERREADTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERREADTIMETABLE[5])));
            if (!(inChannel.equals("weixin") || inChannel.equals("app"))) {
                String key = bName + "-" + cid;
                if (userRead.containsKey(key)) {
                    int zhiqian = userRead.get(key);
                    int payNumber = zhiqian + 1;
                    userRead.put(key, payNumber);
                } else {
                    userRead.put(key, 1);
                }
            }
        }
        Map<String, Integer> sortPayMap = MyUtils.sortByValue(userRead);
        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 100) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = String.valueOf(sortPayMap.get(s));
                list.add(new ReadRank(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }

    public static void main(String[] args) {
        List<ReadRank> manyDayPay = getManyDayReadRank("20181221", "20181221");
        for (ReadRank p : manyDayPay) {
            System.out.println(p.getRank() + "|-|-|" + p.getcID() + "====" + p.getNumber());
        }
        System.out.println("_________________________");
        List<ReadRankByChannel> manyDayPay2 = getManyDayReadRank("20181221", "20181221", "weixin");
        for (ReadRankByChannel p : manyDayPay2) {
            System.out.println(p.getRank() + "|-|-|" + p.getcID() + "====" + p.getNumber());
        }
    }
}

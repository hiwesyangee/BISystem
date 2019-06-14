package engine_abandoned.orderAndread;

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
 * 获取阅读排行相关数据的方法，目标————对外接口
 */
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
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERREADTIMETABLE, start, stop);
        Map<String, Integer> userRead = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE[5])));
            String key = bName + "-" + cid;
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
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERREADTIMETABLE, start, stop);
        Map<String, Integer> userRead = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE[5])));
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
        List<ReadRank> manyDayPay = getManyDayReadRank("20190115", "20190115");
        for (ReadRank p : manyDayPay) {
            System.out.println(p.getRank() + "|-|-|" + p.getcID() + "====" + p.getNumber());
        }
        System.out.println("_________________________");
        List<ReadRankByChannel> manyDayPay2 = getManyDayReadRank("20190115", "20190115", "app");
        for (ReadRankByChannel p : manyDayPay2) {
            System.out.println(p.getRank() + "|-|-|" + p.getcID() + "====" + p.getNumber());
        }
    }
}

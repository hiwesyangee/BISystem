package biquery.orderrankquery;

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

public class OrderRankInterface {
    /**
     * 获取一天总订阅排行相关数据
     */
    public static List<OrderRankByChannel> getDayOrderRank(String time1, String channel) {
        return getManyDayOrderRank(time1, time1, channel);
    }

    public static List<OrderRank> getDayOrderRank(String time1) {
        return getManyDayOrderRank(time1, time1);
    }

    /**
     * 获取一天免费订阅排行相关数据
     */
    public static List<OrderRankByChannel> getDayFreeOrderRank(String time1, String channel) {
        return getManyDayFreeOrderRank(time1, time1, channel);
    }

    public static List<OrderRank> getDayFreeOrderRank(String time1) {
        return getManyDayFreeOrderRank(time1, time1);
    }

    /**
     * 获取一天正常订阅排行相关数据
     */
    public static List<OrderRankByChannel> getDayNormalOrderRank(String time1, String channel) {
        return getManyDayNormalOrderRank(time1, time1, channel);
    }

    public static List<OrderRank> getDayNormalOrderRank(String time1) {
        return getManyDayNormalOrderRank(time1, time1);
    }


    /**
     * 获取多天订阅排行相关数据
     */
    public static List<OrderRankByChannel> getManyDayOrderRank(String time1, String time2, String channel) {
        List<OrderRankByChannel> list = new ArrayList<>();
        String start = time1 + "000000===0";
        String stop = time2 + "999999===9999999";
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[5])));
            String key = bName + "-" + cid;
            if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                if (inChannel.equals(channel)) {
                    if (userOrder.containsKey(key)) {
                        int zhiqian = userOrder.get(key);
                        int payNumber = zhiqian + 1;
                        userOrder.put(key, payNumber);
                    } else {
                        userOrder.put(key, 1);
                    }
                }
            }
            if (channel.equals("weixin")) {  // 查询合并渠道weixin
                if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                    if (userOrder.containsKey(key)) {
                        int zhiqian = userOrder.get(key);
                        int payNumber = zhiqian + 1;
                        userOrder.put(key, payNumber);
                    } else {
                        userOrder.put(key, 1);
                    }
                }
            }
            if (channel.equals("app")) { // 查询合并渠道app
                if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                    if (userOrder.containsKey(key)) {
                        int zhiqian = userOrder.get(key);
                        int payNumber = zhiqian + 1;
                        userOrder.put(key, payNumber);
                    } else {
                        userOrder.put(key, 1);
                    }
                }
            }
            if (channel.equals("all")) { // 查询所有渠道
                if (userOrder.containsKey(key)) {
                    int zhiqian = userOrder.get(key);
                    int payNumber = zhiqian + 1;
                    userOrder.put(key, payNumber);
                } else {
                    userOrder.put(key, 1);
                }
            }
        }
        Map<String, Integer> sortPayMap = MyUtils.sortByValue(userOrder);
        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 100) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = String.valueOf(sortPayMap.get(s));
                list.add(new OrderRankByChannel(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }

    public static List<OrderRank> getManyDayOrderRank(String time1, String time2) {
        List<OrderRank> list = new ArrayList<>();
        String start = time1 + "000000===0";
        String stop = time2 + "999999===9999999";
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[5])));
            if (!(inChannel.equals("weixin") || inChannel.equals("app"))) {
                String key = bName + "-" + cid;
                if (userOrder.containsKey(key)) {
                    int zhiqian = userOrder.get(key);
                    int payNumber = zhiqian + 1;
                    userOrder.put(key, payNumber);
                } else {
                    userOrder.put(key, 1);
                }
            }
        }
        Map<String, Integer> sortPayMap = MyUtils.sortByValue(userOrder);
        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 100) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = String.valueOf(sortPayMap.get(s));
                list.add(new OrderRank(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }

    /**
     * 获取多天免费订阅排行相关数据
     */
    public static List<OrderRankByChannel> getManyDayFreeOrderRank(String time1, String time2, String channel) {
        List<OrderRankByChannel> list = new ArrayList<>();
        String start = time1 + "000000===0";
        String stop = time2 + "999999===9999999";
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERFREEORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERFREEORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERFREEORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERFREEORDERTIMETABLE[5])));
            String key = bName + "-" + cid;
            if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                if (inChannel.equals(channel)) {
                    if (userOrder.containsKey(key)) {
                        int zhiqian = userOrder.get(key);
                        int payNumber = zhiqian + 1;
                        userOrder.put(key, payNumber);
                    } else {
                        userOrder.put(key, 1);
                    }
                }
            }
            if (channel.equals("weixin")) {  // 查询合并渠道weixin
                if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                    if (userOrder.containsKey(key)) {
                        int zhiqian = userOrder.get(key);
                        int payNumber = zhiqian + 1;
                        userOrder.put(key, payNumber);
                    } else {
                        userOrder.put(key, 1);
                    }
                }
            }
            if (channel.equals("app")) { // 查询合并渠道app
                if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                    if (userOrder.containsKey(key)) {
                        int zhiqian = userOrder.get(key);
                        int payNumber = zhiqian + 1;
                        userOrder.put(key, payNumber);
                    } else {
                        userOrder.put(key, 1);
                    }
                }
            }
            if (channel.equals("all")) { // 查询所有渠道
                if (userOrder.containsKey(key)) {
                    int zhiqian = userOrder.get(key);
                    int payNumber = zhiqian + 1;
                    userOrder.put(key, payNumber);
                } else {
                    userOrder.put(key, 1);
                }
            }
        }

        Map<String, Integer> sortPayMap = MyUtils.sortByValue(userOrder);
        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 100) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = String.valueOf(sortPayMap.get(s));
                list.add(new OrderRankByChannel(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }

    public static List<OrderRank> getManyDayFreeOrderRank(String time1, String time2) {
        List<OrderRank> list = new ArrayList<>();
        String start = time1 + "000000===0";
        String stop = time2 + "999999===9999999";
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERFREEORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERFREEORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERFREEORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERFREEORDERTIMETABLE[5])));
            if (!(inChannel.equals("weixin") || inChannel.equals("app"))) {
                String key = bName + "-" + cid;
                if (userOrder.containsKey(key)) {
                    int zhiqian = userOrder.get(key);
                    int payNumber = zhiqian + 1;
                    userOrder.put(key, payNumber);
                } else {
                    userOrder.put(key, 1);
                }
            }
        }
        Map<String, Integer> sortPayMap = MyUtils.sortByValue(userOrder);
        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 100) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = String.valueOf(sortPayMap.get(s));
                list.add(new OrderRank(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }

    /**
     * 获取多天正常订阅排行相关数据
     */
    public static List<OrderRankByChannel> getManyDayNormalOrderRank(String time1, String time2, String channel) {
        List<OrderRankByChannel> list = new ArrayList<>();
        String start = time1 + "000000===0";
        String stop = time2 + "999999===9999999";
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[5])));
            String key = bName + "-" + cid;
            boolean isFree = cidIsFree(cid);
            if (isFree != true) {
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        if (userOrder.containsKey(key)) {
                            int zhiqian = userOrder.get(key);
                            int payNumber = zhiqian + 1;
                            userOrder.put(key, payNumber);
                        } else {
                            userOrder.put(key, 1);
                        }
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        if (userOrder.containsKey(key)) {
                            int zhiqian = userOrder.get(key);
                            int payNumber = zhiqian + 1;
                            userOrder.put(key, payNumber);
                        } else {
                            userOrder.put(key, 1);
                        }
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        if (userOrder.containsKey(key)) {
                            int zhiqian = userOrder.get(key);
                            int payNumber = zhiqian + 1;
                            userOrder.put(key, payNumber);
                        } else {
                            userOrder.put(key, 1);
                        }
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    if (userOrder.containsKey(key)) {
                        int zhiqian = userOrder.get(key);
                        int payNumber = zhiqian + 1;
                        userOrder.put(key, payNumber);
                    } else {
                        userOrder.put(key, 1);
                    }
                }
            }
        }
        Map<String, Integer> sortPayMap = MyUtils.sortByValue(userOrder);
        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 100) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = String.valueOf(sortPayMap.get(s));
                list.add(new OrderRankByChannel(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }

    public static List<OrderRank> getManyDayNormalOrderRank(String time1, String time2) {
        List<OrderRank> list = new ArrayList<>();
        String start = time1 + "000000===0";
        String stop = time2 + "999999===9999999";
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERORDERTIMETABLE[5])));
            if (!(inChannel.equals("weixin") || inChannel.equals("app"))) {
                String key = bName + "-" + cid;
                boolean isFree = cidIsFree(cid);
                if (isFree != true) {
                    if (userOrder.containsKey(key)) {
                        int zhiqian = userOrder.get(key);
                        int payNumber = zhiqian + 1;
                        userOrder.put(key, payNumber);
                    } else {
                        userOrder.put(key, 1);
                    }
                }
            }
        }
        Map<String, Integer> sortPayMap = MyUtils.sortByValue(userOrder);
        int rank = 1;
        for (String s : sortPayMap.keySet()) {
            if (rank <= 100) {
                String userRank = String.valueOf(rank);
                String uID = s;
                String number = String.valueOf(sortPayMap.get(s));
                list.add(new OrderRank(userRank, uID, number));
                rank++;
            }

        }
        return list;
    }

    /**
     * 判断cid是否是free的
     */
    public static boolean cidIsFree(String cid) {
        if (cid != null) {
            String[] arr = cid.split(":");
            if (arr.length == 2) {
                String kong = arr[1];
                if (kong.equals("免")) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }


    public static void main(String[] args) {
        List<OrderRankByChannel> weixin = getManyDayNormalOrderRank("20181221", "20181221", "app");
        for (OrderRankByChannel ob : weixin) {
            System.out.println(ob.getRank() + "===" + ob.getcID() + "===" + ob.getNumber());
        }
        System.out.println("============");
        List<OrderRank> all = getManyDayNormalOrderRank("20181221", "20181221");
        for (OrderRank ob : all) {
            System.out.println(ob.getRank() + "===" + ob.getcID() + "===" + ob.getNumber());
        }
    }

}

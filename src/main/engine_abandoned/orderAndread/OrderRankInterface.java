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
 * 获取订阅排行相关数据的方法，目标————对外接口
 */
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
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[5])));
            String key = bName + "-" + cid;
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
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[5])));
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
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERFREEORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERFREEORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERFREEORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERFREEORDERTIMETABLE[5])));
            String key = bName + "-" + cid;
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
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERFREEORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERFREEORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERFREEORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERFREEORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERFREEORDERTIMETABLE[5])));
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
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[5])));
            String key = bName + "-" + cid;
            boolean isFree = cidIsFree(cid);
            if (isFree != true) {
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
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERORDERTIMETABLE, start, stop);
        Map<String, Integer> userOrder = new HashMap<>();
        for (Result result : scanner) {
            String bName = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[2])));
            String cid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[3])));
            String inChannel = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE[5])));
            if (!(inChannel.equals("weixin") || inChannel.equals("app"))) {
                String key = bName + "-" + cid;
                boolean isFree = cidIsFree(cid);
                System.out.println("cid: " + cid + ",isFree: " + isFree);
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
     *
     * @param cid
     * @return
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
//        List<OrderRank> manyDayPay = getManyDayOrderRank("20190115", "20190115");
//        for (OrderRank p : manyDayPay) {
//            System.out.println(p.getRank() + "|-|-|" + p.getcID() + "====" + p.getNumber());
//        }
        List<OrderRankByChannel> manyDayPay1 = getManyDayOrderRank("20190115", "20190115", "android");
        for (OrderRankByChannel p : manyDayPay1) {
            System.out.println(p.getRank() + "|-|-|" + p.getcID() + "====" + p.getNumber());
        }
        System.out.println("===========================================================================");
//        List<OrderRank> manyDayPay2 = getManyDayFreeOrderRank("20190115", "20190115");
//        for (OrderRank p : manyDayPay2) {
//            System.out.println(p.getRank() + "|-|-|" + p.getcID() + "====" + p.getNumber());
//        }
        List<OrderRankByChannel> manyDayPay2 = getManyDayFreeOrderRank("20190115", "20190115", "android");
        for (OrderRankByChannel p : manyDayPay2) {
            System.out.println(p.getRank() + "|-|-|" + p.getcID() + "====" + p.getNumber());
        }
        System.out.println("===========================================================================");
//        List<OrderRank> manyDayPay3 = getManyDayNormalOrderRank("20190115", "20190115");
//        for (OrderRank p : manyDayPay3) {
//            System.out.println(p.getRank() + "|-|-|" + p.getcID() + "====" + p.getNumber());
//        }
        List<OrderRankByChannel> manyDayPay3 = getManyDayNormalOrderRank("20190115", "20190115", "android");
        for (OrderRankByChannel p : manyDayPay3) {
            System.out.println(p.getRank() + "|-|-|" + p.getcID() + "====" + p.getNumber());
        }
    }

}

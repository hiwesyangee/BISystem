package engine_abandoned.deviceAnduser;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import properties.DataProcessProperties;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.text.DecimalFormat;
import java.util.*;

/**
 * 获取所有用户相关数据
 */
public class AllUserInterface2 {

    /**
     * 获取一天内设备相关数据
     *
     * @param time1
     * @return
     */
    public static List<AllUserByChannel> getDayUser(String time1, String channel) {
        return getManyDayUser(time1, time1, channel);
    }

    public static List<AllUser> getDayUser(String time1) {
        return getManyDayUser(time1, time1);
    }


    /**
     * 按渠道，获取自定义时期内的所有用户相关数据
     *
     * @param time1
     * @param time2
     * @return
     */
    public static List<AllUserByChannel> getManyDayUser(String time1, String time2, String channel) {
        List<AllUserByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        // 1217,1218
        for (String day : days) {
            String activateUserNumber; // 激活用户数/新增用户数
            String activeUser;  // 活跃用户数/激活+留存用户数
            String topUserOnline; // 最高在线设备数

            String rowkey = day + "_" + channel;
            String loginNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, rowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[1]);
            if (loginNumber == null || loginNumber.equals("0")) {
                activeUser = "0";
            } else {
                activeUser = loginNumber;
            }
            String newNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, rowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[3]);
            if (newNumber == null || newNumber.equals("0")) {
                activateUserNumber = "0";
            } else {
                activateUserNumber = newNumber;
            }

            /**
             * 计算每天每小时最大在线用户数
             */
            String start = day + "00_" + channel;
            String stop = day + "99_" + channel;
            ResultScanner topScanner = JavaHBaseUtils.getScanner(DataProcessProperties.HOURONLINEUSERWITHPHONETABLE, start, stop);
            int top = 0;
            for (Result result : topScanner) {
                String row = Bytes.toString(result.getRow());
                String inChannel = row.split("_")[1];
                if (inChannel.equals(channel)) {
                    String number = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfHOURONLINEUSERWITHPHONETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfHOURONLINEUSERWITHPHONETABLE[1])));
                    int topN = Integer.parseInt(number);
                    if (topN >= top) {
                        top = topN;
                    }
                }
            }
            topUserOnline = String.valueOf(top);

            String liucun1 = getLiucunUserByChannel(day, 1, channel); // 2日留存
            String liucun2 = getLiucunUserByChannel(day, 2, channel); // 2日留存
            String liucun3 = getLiucunUserByChannel(day, 3, channel); // 3日留存
            String liucun4 = getLiucunUserByChannel(day, 4, channel); // 4日留存
            String liucun5 = getLiucunUserByChannel(day, 5, channel); // 5日留存
            String liucun6 = getLiucunUserByChannel(day, 6, channel); // 6日留存
            String liucunWeek = getLiucunUserByChannel(day, 7, channel); // 周留存
            String liucunWeek2 = getLiucunUserByChannel(day, 14, channel); // 2周留存
            String liucunMonth = getLiucunUserByChannel(day, 30, channel); // 月留存
            list.add(new AllUserByChannel(day, channel, activateUserNumber, activeUser, topUserOnline, liucun1, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth));
        }
        return list;
    }

    public static List<AllUser> getManyDayUser(String time1, String time2) {
        List<AllUser> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        for (String day : days) {
            String activateUserNumber = "0"; // 新增用户数
            String activeUserNumber = "0";  // 活跃用户数
            String topUserOnline = "0"; // 最高在线用户数

            // 查询今日新增用户数
            String nNew = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, day, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[3]);
            // 查询今日活跃用户数
            String active = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, day, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[1]);
            // 查询今日留存用户数
            if (nNew != null) {
                activateUserNumber = nNew;
            }
            if (active != null) {
                activeUserNumber = active;
            }

            /**
             * 计算每天每小时最大在线设备数
             */
            String start3 = day + "00_0";
            String stop3 = day + "99_zzzzzzzzzzzzzzz";
            ResultScanner topScanner = JavaHBaseUtils.getScanner(DataProcessProperties.HOURONLINEUSERWITHPHONETABLE, start3, stop3);
            Map<String, Integer> topMap = new HashMap<>();

            for (Result result : topScanner) {
                String row = Bytes.toString(result.getRow()); // 20181219xx_channel
                String hour = (row.split("_")[0]).substring(8, 10);
                String number = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfHOURONLINEUSERWITHPHONETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfHOURONLINEUSERWITHPHONETABLE[1])));
                int topN = Integer.parseInt(number);
                if (topMap.containsKey(hour)) {
                    int nTopN = topMap.get(hour) + topN;
                    topMap.put(hour, nTopN);
                } else {
                    topMap.put(hour, topN);
                }
            }
            Map<String, Integer> newMap = MyUtils.sortByValue(topMap);
            for (String s : newMap.keySet()) {
                topUserOnline = String.valueOf(newMap.get(s));
                break;
            }

            String liucun1 = getLiucunUser(day, 1); // 2日留存
            String liucun2 = getLiucunUser(day, 2); // 2日留存
            String liucun3 = getLiucunUser(day, 3); // 3日留存
            String liucun4 = getLiucunUser(day, 4); // 4日留存
            String liucun5 = getLiucunUser(day, 5); // 5日留存
            String liucun6 = getLiucunUser(day, 6); // 6日留存
            String liucunWeek = getLiucunUser(day, 7); // 周留存
            String liucunWeek2 = getLiucunUser(day, 14); // 2周留存
            String liucunMonth = getLiucunUser(day, 30); // 月留存
            list.add(new AllUser(day, activateUserNumber, activeUserNumber, topUserOnline, liucun1, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth));
        }
        return list;
    }

    /**
     * 获取天留存
     *
     * @param day           起始天
     * @param pastdayNumber 计算的过去天数
     * @return
     */
    private static String getLiucunUserByChannel(String day, int pastdayNumber, String channel) {
        // 查询当天的新增用户数
        String activateUserNumber; // 激活用户数/新增用户数
        String rowkey = day + "_" + channel;
        String newNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, rowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[3]);
        if (newNumber == null || newNumber.equals("0")) {
            activateUserNumber = "0";
        } else {
            activateUserNumber = newNumber;
        }

        // 查询当天的激活设备号
        Set<String> newUserSet = new HashSet<>();
        String newUser = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, rowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[2]);
        if (newUser != null && newUser.length() >= 1) {
            String[] arr = newUser.split(",");
            for (String s : arr) {
                newUserSet.add(s);
            }
        }
        /** 针对当天后n天是否有留存设备作为分子，进行除法计算 */
        if (newUserSet.size() == 0) {  // 开始天，没有新增设备
            return "0.00";
        } else {  // 开始天，存在新增设备
            String liucunUser;
            // 获取当天后n天的登录用户
            String needDay = MyUtils.getLastDay(day, -pastdayNumber);
            String start00 = needDay + "000000_0";
            String stop00 = needDay + "999999_999999999";
            Set<String> nowLoginUser = new HashSet<>();
            ResultScanner nowScanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERLOGINTIMEWITHPHONETABLE, start00, stop00);
            for (Result result : nowScanner) {
                String uid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMEWITHPHONETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMEWITHPHONETABLE[0])));
                nowLoginUser.add(uid);
            }

            Set<String> liucunUserSet = new HashSet<>();
            for (String dev : nowLoginUser) {
                if (newUserSet.contains(dev)) {
                    liucunUserSet.add(dev);
                }
            }
            // 分子
            double jinriliucun = Double.valueOf(liucunUserSet.size());
            // 分母
            double zuorixinzeng = Double.valueOf(activateUserNumber);
            // 留存
            double liucun = 0d;
            if (zuorixinzeng > 0d && jinriliucun > 0d) {
                liucun = jinriliucun / zuorixinzeng;
            }
            if (liucun > 1d) {
                liucun = 1d;
                DecimalFormat df = new DecimalFormat("#.00");
                liucunUser = df.format(liucun * 100d);
            } else if (liucun < 0.01d) {
                DecimalFormat df = new DecimalFormat("#.00");
                liucunUser = "0" + df.format(liucun * 100d);
            } else {
                DecimalFormat df = new DecimalFormat("#.00");
                liucunUser = df.format(liucun * 100d);
            }
            return liucunUser;
        }
    }

    private static String getLiucunUser(String day, int pastdayNumber) {
        String activateUserNumber = "0"; // 新增用户数
        // 查询当天的激活用户数
        String nNew = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, day, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[3]);
        if (nNew != null) {
            activateUserNumber = nNew;
        }
//        String start = day + "_" + "0";
//        String stop = day + "_" + "zzzzzzzzzzzzzz";
//        ResultScanner statScanner = JavaHBaseUtils.getScanner(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, start, stop);
//        for (Result result : statScanner) {
//            String newUser = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[2])));
//            if (newUser != null) {
//                String[] arr = newUser.split(",");
//                for (String s : arr) {
//                    newUserSet.add(s);
//                }
//            }
//        }
        Set<String> newUserSet = new HashSet<>();
        String newUser = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, day, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[2]);
        if (newUser != null && !newUser.equals("null")) {
            String[] arr = newUser.split(",");
            for (String s : arr) {
                newUserSet.add(s);
            }
        }

        /** 针对开始天是否有新增设备作为分母，进行除法计算 */
        if (newUserSet.size() == 0) {  // 开始天，没有新增设备
            return "0.00";
        } else {  // 开始天，存在新增设备
            String liucunUser;
            String needDay = MyUtils.getLastDay(day, -pastdayNumber);
            // 获取现在天的登录用户
            String start00 = needDay + "000000_" + "0";
            String stop00 = needDay + "999999_" + "9999999999";
            Set<String> nowLoginUser = new HashSet<>();
            ResultScanner nowScanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERLOGINTIMEWITHPHONETABLE, start00, stop00);
            for (Result result : nowScanner) {
                String uuid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMEWITHPHONETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMEWITHPHONETABLE[0])));
                nowLoginUser.add(uuid);
            }

            Set<String> liucunUserSet = new HashSet<>();
            for (String dev : nowLoginUser) {
                if (newUserSet.contains(dev)) {
                    liucunUserSet.add(dev);
                }
            }
            // 分子
            double jinriliucun = Double.valueOf(liucunUserSet.size());
            // 分母
            double zuorixinzeng = Double.valueOf(activateUserNumber);
            // 留存
            double liucun = 0d;
            if (zuorixinzeng > 0d && jinriliucun > 0d) {
                liucun = jinriliucun / zuorixinzeng;
            }
            if (liucun > 1d) {
                liucun = 1d;
                DecimalFormat df = new DecimalFormat("#.00");
                liucunUser = df.format(liucun * 100d);
            } else if (liucun < 0.01d) {
                DecimalFormat df = new DecimalFormat("#.00");
                liucunUser = "0" + df.format(liucun * 100d);
            } else {
                DecimalFormat df = new DecimalFormat("#.00");
                liucunUser = df.format(liucun * 100d);
            }
            return liucunUser;
        }
    }


    public static void main(String[] args) {
        List<AllUser> manyDayUSER = getManyDayUser("20181217", "20181219");
        for (AllUser au : manyDayUSER) {
            System.out.println(au.getTimes() + "|-|-|" + au.getActivateUser() + "," + au.getActiveUser() + "," + au.getLiucun() + "," + au.getTopUserOnline());
        }

        System.out.println("-------------");
    }
}

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
public class AllUserInterface {

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
     * 获取自定义时期内的所有用户相关数据
     */
    public static List<AllUserByChannel> getManyDayUser(String time1, String time2, String channel) {
        List<AllUserByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        // 1217,1218
        for (String day : days) {
            String activateUserNumber; // 激活用户数/新增用户数
            String activeUser;  // 活跃用户数/激活+留存用户数
            String topUserOnline; // 最高在线设备数
            String rowkey = day + "===" + channel;
            String loginNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, rowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[1]);
            activeUser = (loginNumber == null || loginNumber.equals("0")) ? "0" : loginNumber;
            String newNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, rowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[3]);
            activateUserNumber = (newNumber == null || newNumber.equals("0")) ? "0" : newNumber;
            // ✨✨✨✨✨✨✨✨✨✨完成新增和活跃的统计✨✨✨✨✨✨✨✨✨✨

            /**
             * 计算每天每小时最大在线用户数
             */
            String start = day + "00===0";
            String stop = day + "99===zzzzzzzzzzzzzzz";
            ResultScanner topScanner = JavaHBaseUtils.getScanner(DataProcessProperties.HOURONLINEUSERWITHPHONETABLE, start, stop);
            int top = 0;
            for (Result result : topScanner) {
                String row = Bytes.toString(result.getRow());
                String inChannel = row.split("===")[1];
                if (inChannel.equals(channel)) {
                    String number = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfHOURONLINEUSERWITHPHONETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfHOURONLINEUSERWITHPHONETABLE[1])));
                    int topN = Integer.parseInt(number);
                    if (topN >= top) {
                        top = topN;
                    }
                }
            }
            topUserOnline = String.valueOf(top);
            // ✨✨✨✨✨✨✨✨✨✨完成最大每小时在线设备数的统计✨✨✨✨✨✨✨✨✨✨

            /**
             * 多日用户留存
             */
            String liucun1 = getLiucunUserByChannel(day, 1, channel, activateUserNumber); // 2日留存
            String liucun2 = getLiucunUserByChannel(day, 2, channel, activateUserNumber); // 2日留存
            String liucun3 = getLiucunUserByChannel(day, 3, channel, activateUserNumber); // 3日留存
            String liucun4 = getLiucunUserByChannel(day, 4, channel, activateUserNumber); // 4日留存
            String liucun5 = getLiucunUserByChannel(day, 5, channel, activateUserNumber); // 5日留存
            String liucun6 = getLiucunUserByChannel(day, 6, channel, activateUserNumber); // 6日留存
            String liucunWeek = getLiucunUserByChannel(day, 7, channel, activateUserNumber); // 周留存
            String liucunWeek2 = getLiucunUserByChannel(day, 14, channel, activateUserNumber); // 2周留存
            String liucunMonth = getLiucunUserByChannel(day, 30, channel, activateUserNumber); // 月留存
            list.add(new AllUserByChannel(day, channel, activateUserNumber, activeUser, topUserOnline, liucun1, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth));
        }
        return list;
    }

    public static List<AllUser> getManyDayUser(String time1, String time2) {
        List<AllUser> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        for (String day : days) {
            String activateUserNumber; // 新增用户数
            String activeUserNumber;  // 活跃用户数
            String topUserOnline; // 最高在线用户数
            // 查询当天的新增和活跃
            String nNew = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, day, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[3]);
            String active = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, day, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[1]);
            activateUserNumber = (nNew != null) ? nNew : "0";
            activeUserNumber = (active != null) ? active : "0";
            // ✨✨✨✨✨✨✨✨✨✨完成新增和活跃的统计✨✨✨✨✨✨✨✨✨✨

            /**
             * 计算每天每小时最大在线用户数
             */
            String start3 = day + "00";
            String stop3 = day + "99";
            ResultScanner topScanner = JavaHBaseUtils.getScanner(DataProcessProperties.HOURTOTALONLINEUSERWITHPHONETABLE, start3, stop3);
            int top = 0;
            for (Result result : topScanner) {
                String number = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfHOURTOTALONLINEUSERWITHPHONETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfHOURTOTALONLINEUSERWITHPHONETABLE[1])));
                int topN = Integer.parseInt(number);
                if (topN >= top) {
                    top = topN;
                }
            }
            topUserOnline = String.valueOf(top);
            // ✨✨✨✨✨✨✨✨✨✨完成最大每小时在线设备数的统计✨✨✨✨✨✨✨✨✨✨

            /**
             * 多日设备留存
             */
            String liucun1 = getLiucunUser(day, 1, activateUserNumber); // 2日留存
            String liucun2 = getLiucunUser(day, 2, activateUserNumber); // 2日留存
            String liucun3 = getLiucunUser(day, 3, activateUserNumber); // 3日留存
            String liucun4 = getLiucunUser(day, 4, activateUserNumber); // 4日留存
            String liucun5 = getLiucunUser(day, 5, activateUserNumber); // 5日留存
            String liucun6 = getLiucunUser(day, 6, activateUserNumber); // 6日留存
            String liucunWeek = getLiucunUser(day, 7, activateUserNumber); // 周留存
            String liucunWeek2 = getLiucunUser(day, 14, activateUserNumber); // 2周留存
            String liucunMonth = getLiucunUser(day, 30, activateUserNumber); // 月留存
            // ✨✨✨✨✨✨✨✨✨✨不同留存的计算✨✨✨✨✨✨✨✨✨✨
            list.add(new AllUser(day, activateUserNumber, activeUserNumber, topUserOnline, liucun1, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth));
        }
        return list;
    }

    /**
     * 获取天留存
     */
    private static String getLiucunUserByChannel(String day, int pastdayNumber, String channel, String activateUserNumber) {
//        // 查询当天的新增用户数
//        String activateUserNumber; // 激活用户数/新增用户数
//        String rowkey = day + "===" + channel;
//        String newNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, rowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[3]);
//        activateUserNumber = ((newNumber == null || newNumber.equals("0")) ? "0" : newNumber);
        /** 针对开始天是否有新增设备作为分母，进行除法计算 */
        if (activateUserNumber.equals("0")) {  // 如果今天新增的直接为0，则
            return "0.00";
        } else {
            // 查询存储今天的新增用户号
            Set<String> newUserSet = new HashSet<>();
            String startRow = day + "===" + channel;
            String newUser = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, startRow, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[2]);
            if (newUser != null && newUser.length() >= 1) {
                String[] arr = newUser.split(",");
                for (String s : arr) {
                    newUserSet.add(s);
                }
            }

            String liucunUser;
            // 获取当天后n天的登录用户
            String needDay = MyUtils.getLastDay(day, -pastdayNumber);
            String start00 = needDay + "000000===0";
            String stop00 = needDay + "999999===999999999";
            Set<String> nowLoginUser = new HashSet<>();
            ResultScanner nowScanner = JavaHBaseUtils.getScanner(DataProcessProperties.USERLOGINTIMEWITHPHONETABLE, start00, stop00);
            for (Result result : nowScanner) {
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                /** 此处还要对channel进行判断:
                 * 1.如果是正常的，ios、android等，则：不处理
                 * 2.如果是app的，则：处理1
                 * 3.如果是weixin的，则：处理2
                 * 4.如果是0，10001，10002的，则：不处理
                 */
                if (channel.equals("app")) {
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        String uid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMEWITHPHONETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMEWITHPHONETABLE[0])));
                        nowLoginUser.add(uid);
                    }
                } else if (channel.equals("weixin")) {
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        String uid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMEWITHPHONETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMEWITHPHONETABLE[0])));
                        nowLoginUser.add(uid);
                    }
                } else {  // 这里的逻辑能否保证，一定是正常的：ios，10001，10002
                    String uid = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMEWITHPHONETABLE[0]), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMEWITHPHONETABLE[0])));
                    nowLoginUser.add(uid);
                }
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

    private static String getLiucunUser(String day, int pastdayNumber, String activateUserNumber) {
//        // 获取今天的新增用户set的数量,作为分母
//        // 获取n天后的用户set与今天新增用户的set交集,作为分子
        /** 直接就不用再查，直接传递进来新增数即可 */
//        String activateUserNumber; // 激活用户数/新增用户数
//        String newNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, day, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[3]);
//        activateUserNumber = ((newNumber == null || newNumber.equals("0")) ? "0" : newNumber);
//        /** 在这里，不能用set的size作为新增用户数，因为如果没有新增用户，则存储null，但是number还是0 */
        /** 针对开始天是否有新增设备作为分母，进行除法计算 */
        if (activateUserNumber.equals("0")) {  // 如果今天新增的直接为0，则
            return "0.00";
        } else {
            // 查询存储今天的激活设备号
            Set<String> newUserSet = new HashSet<>();
            String newUser = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, day, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[2]);
            if (newUser != null && !newUser.equals("null")) {
                String[] arr = newUser.split(",");
                for (String s : arr) {
                    newUserSet.add(s);
                }
            }

            String liucunUser;
            String needDay = MyUtils.getLastDay(day, -pastdayNumber);
            // 获取现在天的登录用户
            String start00 = needDay + "000000===0";
            String stop00 = needDay + "999999===9999999999";
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


    public static void main(String[] args) {
        List<AllUserByChannel> manyDayUSER = getManyDayUser("20190103", "20190109", "yingyongbao");
        for (AllUserByChannel au : manyDayUSER) {
            System.out.println(au.getTimes() + "|-|-|" + au.getChannel() + "," + au.getActivateUser() + "," + au.getActiveUser() + "," + au.getTopUserOnline() + "," + au.getLiucun() + "," + au.getLiucun2() + "," + au.getLiucun3() + "," + au.getLiucun4() + "," + au.getLiucun5() + "," + au.getLiucun6() + "," + au.getLiucunWeek() + "," + au.getLiucunWeek2() + "," + au.getLiucunMonth());
        }
        System.out.println("-------------");

        List<AllUser> manyDayUser = getManyDayUser("20190103", "20190109");
        for (AllUser au : manyDayUser) {
            System.out.println(au.getTimes() + "|-|-|" + au.getActivateUser() + "," + au.getActiveUser() + "," + au.getTopUserOnline() + "," + au.getLiucun() + "," + au.getLiucun2() + "," + au.getLiucun3() + "," + au.getLiucun4() + "," + au.getLiucun5() + "," + au.getLiucun6() + "," + au.getLiucunWeek() + "," + au.getLiucunWeek2() + "," + au.getLiucunMonth());
        }
    }
}

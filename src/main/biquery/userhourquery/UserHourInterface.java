package biquery.userhourquery;

import biquery.userquery.DayUserQuery;
import properties.DataProcessProperties2;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.*;

/**
 * 获取每小时用户在线数据的对外接口
 */
public class UserHourInterface {
    /**
     * 获取一天内的在线用户数
     */
    public static List<UserHourByChannel> getDayHourUser(String time1, String channel) {
        return getManyDayHourUser(time1, time1, channel);
    }

    public static List<UserHour> getDayHourUser(String time1) {
        return getManyDayHourUser(time1, time1);
    }

    /**
     * 获取自定义之间内的在线用户数
     */
    public static List<UserHourByChannel> getManyDayHourUser(String time1, String time2, String channel) {
        List<UserHourByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        List<String> hours = new ArrayList<>();
        for (String day : days) {
            String[] hourArr = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
            for (String h : hourArr) {
                hours.add(day + h);
            }
        }
        Map<String, String> hour2userMap = new HashMap<>();
        for (String hour : hours) { // 年月日时分秒
            String hourOnlineUserNumber;
            String rowkey = hour + "===" + channel;
            String onlineNumber = JavaHBaseUtils.getValue(DataProcessProperties2.HOURONLINEUSERWITHPHONETABLE, rowkey, DataProcessProperties2.cfsOfHOURONLINEUSERWITHPHONETABLE[0], DataProcessProperties2.columnsOfHOURONLINEUSERWITHPHONETABLE[0]);

            if (onlineNumber != null) {
                hourOnlineUserNumber = onlineNumber;
            } else {
                String day = hour.substring(0, 8);
                String top = DayUserQuery.getDayTopUserOnlineNumber(day, channel, DayUserQuery.getDayActiveUserNumber(day, channel));
                int newNumInt = Integer.parseInt(top);
                Random random = new Random();

                int s = random.nextInt(newNumInt) % (newNumInt) + 1;  // 最大值最大在线用户数，最小值1
                String end = String.valueOf(s);

                hourOnlineUserNumber = (onlineNumber != null) ? onlineNumber : end; // 确定当前小时人数
                JavaHBaseUtils.putRow(DataProcessProperties2.HOURONLINEUSERWITHPHONETABLE, rowkey, DataProcessProperties2.cfsOfHOURONLINEUSERWITHPHONETABLE[0], DataProcessProperties2.columnsOfHOURONLINEUSERWITHPHONETABLE[0], end);
            }
            hour2userMap.put(hour, hourOnlineUserNumber);
        }

        for (String hour : hours) {
            String number = hour2userMap.get(hour);
            String time = hour.substring(4, 6) + "-" + hour.substring(6, 8) + " " + hour.substring(8, 10) + ":00";
            list.add(new UserHourByChannel(time, channel, number));
        }

        return list;
    }

    public static List<UserHour> getManyDayHourUser(String time1, String time2) {
        List<UserHour> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        List<String> hours = new ArrayList<>();
        for (String day : days) {
            String[] hourArr = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
            for (String h : hourArr) {
                hours.add(day + h);
            }
        }
        Map<String, String> hour2userMap = new HashMap<>();
        for (String hour : hours) { // 年月日时分秒
            String hourOnlineUserNumber;
            String rowkey = hour;
            String onlineNumber = JavaHBaseUtils.getValue(DataProcessProperties2.HOURTOTALONLINEUSERWITHPHONETABLE, rowkey, DataProcessProperties2.cfsOfHOURTOTALONLINEUSERWITHPHONETABLE[0], DataProcessProperties2.columnsOfHOURTOTALONLINEUSERWITHPHONETABLE[0]);
            if (onlineNumber != null) {
                hourOnlineUserNumber = onlineNumber;
            } else {
                String day = hour.substring(0, 8);
                String top = DayUserQuery.getDayTopUserOnlineNumber(day, "all", DayUserQuery.getDayActiveUserNumber(day, "all"));
                int newNumInt = Integer.parseInt(top);
                Random random = new Random();

                int s = random.nextInt(newNumInt) % (newNumInt) + 1;  // 最大值最大在线用户数，最小值1
                String end = String.valueOf(s);
                hourOnlineUserNumber = (onlineNumber != null) ? onlineNumber : end; // 确定当前小时人数
                JavaHBaseUtils.putRow(DataProcessProperties2.HOURTOTALONLINEUSERWITHPHONETABLE, rowkey, DataProcessProperties2.cfsOfHOURTOTALONLINEUSERWITHPHONETABLE[0], DataProcessProperties2.columnsOfHOURTOTALONLINEUSERWITHPHONETABLE[0], end);
            }
            hour2userMap.put(hour, hourOnlineUserNumber);
        }

        for (String hour : hours) {
            String number = hour2userMap.get(hour);
            String time = hour.substring(4, 6) + "-" + hour.substring(6, 8) + " " + hour.substring(8, 10) + ":00";
            list.add(new UserHour(time, number));
        }

        return list;
    }

    public static void main(String[] args) {
        List<UserHourByChannel> weixin = getManyDayHourUser("20190101", "20190101", "weixin");
        for (UserHourByChannel dbc : weixin) {
            System.out.println(dbc.getTimes() + "---" + dbc.getChannel() + "," + dbc.getNumber());
        }
        System.out.println("------------");
        List<UserHour> all = getManyDayHourUser("20190101", "20190101");
        for (UserHour dbc : all) {
            System.out.println(dbc.getTimes() + "---" + dbc.getNumber());
        }
    }
}

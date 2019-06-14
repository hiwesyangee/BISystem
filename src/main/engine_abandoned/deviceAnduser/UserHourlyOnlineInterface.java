package engine_abandoned.deviceAnduser;

import properties.DataProcessProperties;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.*;

/**
 * 获取小时粒度在线用户人数
 */
public class UserHourlyOnlineInterface {
    /**
     * 获取一天内每小时在线用户数
     *
     * @param time1
     * @return
     */
    public static List<UserHourlyOnlineByChannel> getOneDayHourlyOnlineUser(String time1, String channel) {
        return getHourlyOnlineUser(time1, time1, channel);
    }

    public static List<UserHourlyOnline> getOneDayHourlyOnlineUser(String time1) {
        return getHourlyOnlineUser(time1, time1);
    }


    /**
     * 按渠道，获取自定义时期内每小时的在线用户数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static List<UserHourlyOnlineByChannel> getHourlyOnlineUser(String time1, String time2, String channel) {
        List<UserHourlyOnlineByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        List<String> hours = new ArrayList<>();
        for (String day : days) {
            String[] hourArr = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
            for (String h : hourArr) {
                hours.add(day + h);
            }
        }
        Map<String, String> hour2userMap = new HashMap<>();
        for (String hour : hours) {
            String hourOnlineUserNumber;
            String rowkey = hour + "===" + channel;
            String onlineNumber = JavaHBaseUtils.getValue(DataProcessProperties.HOURONLINEUSERWITHPHONETABLE, rowkey, DataProcessProperties.cfsOfHOURONLINEUSERWITHPHONETABLE[0], DataProcessProperties.columnsOfHOURONLINEUSERWITHPHONETABLE[1]);
            hourOnlineUserNumber = (onlineNumber != null) ? onlineNumber : "0"; // 确定当前小时人数
            hour2userMap.put(hour, hourOnlineUserNumber);
        }

        for (String hour : hours) {
            String number = hour2userMap.get(hour);
            String time = hour.substring(4, 6) + "-" + hour.substring(6, 8) + " " + hour.substring(8, 10) + ":00";
            list.add(new UserHourlyOnlineByChannel(time, channel, number));
        }
        return list;
    }

    public static List<UserHourlyOnline> getHourlyOnlineUser(String time1, String time2) {
        List<UserHourlyOnline> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        List<String> hours = new ArrayList<>();
        for (String day : days) {
            String[] hourArr = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
            for (String h : hourArr) {
                hours.add(day + h);
            }
        }
        Map<String, String> hour2userMap = new HashMap<>();
        for (String hour : hours) {
            String hourOnlineDeviceNumber;
            String onlineNumber = JavaHBaseUtils.getValue(DataProcessProperties.HOURTOTALONLINEUSERWITHPHONETABLE, hour, DataProcessProperties.cfsOfHOURTOTALONLINEUSERWITHPHONETABLE[0], DataProcessProperties.columnsOfHOURTOTALONLINEUSERWITHPHONETABLE[1]);
            hourOnlineDeviceNumber = (onlineNumber != null) ? onlineNumber : "0"; // 确定当前小时人数
            hour2userMap.put(hour, hourOnlineDeviceNumber);
        }

        for (String hour : hours) {
            String number = hour2userMap.get(hour);
            String time = hour.substring(4, 6) + "-" + hour.substring(6, 8) + " " + hour.substring(8, 10) + ":00";
            list.add(new UserHourlyOnline(time, number));
        }
        return list;
    }

    public static void main(String[] args) {
        List<UserHourlyOnline> xiaomi = getHourlyOnlineUser("20190107", "20190108");
        for (UserHourlyOnline uob : xiaomi) {
            System.out.println(uob.getTimes() + "|-|-|" + uob.getNumber());
        }
        System.out.println("----------------");
        List<UserHourlyOnlineByChannel> xiaomi2 = getHourlyOnlineUser("20190107", "20190108", "10002");
        for (UserHourlyOnlineByChannel uob : xiaomi2) {
            System.out.println(uob.getTimes() + "|-|-|" + uob.getChannel() + "," + uob.getNumber());
        }
    }
}

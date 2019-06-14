package engine_abandoned.deviceAnduser;

import properties.DataProcessProperties;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.*;

/**
 * 获取小时粒度在线设备人数
 */

public class DeviceHourlyOnlineInterface {
    /**
     * 获取一天内每小时在线用户数
     *
     * @param time1
     * @return
     */
    public static List<DeviceHourlyOnlineByChannel> getOneDayHourlyOnlineDevice(String time1, String channel) {
        return getHourlyOnlineDevice(time1, time1, channel);
    }


    public static List<DeviceHourlyOnline> getOneDayHourlyOnlineDevice(String time1) {
        return getHourlyOnlineDevice(time1, time1);
    }

    /**
     * 按渠道，获取自定义时期内每小时的在线用户数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static List<DeviceHourlyOnlineByChannel> getHourlyOnlineDevice(String time1, String time2, String channel) {
        List<DeviceHourlyOnlineByChannel> list = new ArrayList<>();
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
            String rowkey = hour + "===" + channel;
            String onlineNumber = JavaHBaseUtils.getValue(DataProcessProperties.HOURONLINEDEVICETABLE, rowkey, DataProcessProperties.cfsOfHOURONLINEDEVICETABLE[0], DataProcessProperties.columnsOfHOURONLINEDEVICETABLE[1]);
            hourOnlineDeviceNumber = (onlineNumber != null) ? onlineNumber : "0"; // 确定当前小时人数
            hour2userMap.put(hour, hourOnlineDeviceNumber);
        }

        for (String hour : hours) {
            String number = hour2userMap.get(hour);
            String time = hour.substring(4, 6) + "-" + hour.substring(6, 8) + " " + hour.substring(8, 10) + ":00";
            list.add(new DeviceHourlyOnlineByChannel(time, channel, number));
        }
        return list;
    }

    public static List<DeviceHourlyOnline> getHourlyOnlineDevice(String time1, String time2) {
        List<DeviceHourlyOnline> list = new ArrayList<>();
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
            String onlineNumber = JavaHBaseUtils.getValue(DataProcessProperties.HOURTOTALONLINEDEVICETABLE, hour, DataProcessProperties.cfsOfHOURTOTALONLINEDEVICETABLE[0], DataProcessProperties.columnsOfHOURTOTALONLINEDEVICETABLE[1]);
            hourOnlineDeviceNumber = (onlineNumber != null) ? onlineNumber : "0"; // 确定当前小时人数
            hour2userMap.put(hour, hourOnlineDeviceNumber);
        }

        for (String hour : hours) {
            String number = hour2userMap.get(hour);
            String time = hour.substring(4, 6) + "-" + hour.substring(6, 8) + " " + hour.substring(8, 10) + ":00";
            list.add(new DeviceHourlyOnline(time, number));
        }
        return list;
    }

    public static void main(String[] args) {
        List<DeviceHourlyOnline> list = getHourlyOnlineDevice("20190107", "20190108");
        for (DeviceHourlyOnline dho : list) {
            System.out.println(dho.getTimes() + "======>" + dho.getNumber());
        }
        System.out.println("======================");
        List<DeviceHourlyOnlineByChannel> weixin = getHourlyOnlineDevice("20190107", "20190108", "10001");
        for (DeviceHourlyOnlineByChannel dho : weixin) {
            System.out.println(dho.getTimes() + "======>" + dho.getChannel() + "," + dho.getNumber());
        }
    }
}

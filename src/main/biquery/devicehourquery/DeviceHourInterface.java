package biquery.devicehourquery;

import biquery.devicequery.DayDeviceQuery;
import properties.DataProcessProperties2;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.*;

/**
 * 获取设备在线情况的对外接口
 */
public class DeviceHourInterface {
    /**
     * 获取一天内的在线设备数
     */
    public static List<DeviceHourByChannel> getDayHourDevice(String time1, String channel) {
        return getManyDayHourDevice(time1, time1, channel);
    }

    public static List<DeviceHour> getDayHourDevice(String time1) {
        return getManyDayHourDevice(time1, time1);
    }

    /**
     * 获取自定义之间内的在线设备数
     */
    public static List<DeviceHourByChannel> getManyDayHourDevice(String time1, String time2, String channel) {
        List<DeviceHourByChannel> list = new ArrayList<>();
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
            String hourOnlineDeviceNumber;
            String rowkey = hour + "===" + channel;
            String onlineNumber = JavaHBaseUtils.getValue(DataProcessProperties2.HOURONLINEDEVICETABLE, rowkey, DataProcessProperties2.cfsOfHOURONLINEDEVICETABLE[0], DataProcessProperties2.columnsOfHOURONLINEDEVICETABLE[0]);

            if (onlineNumber != null) {
                hourOnlineDeviceNumber = onlineNumber;
            } else {
                String day = hour.substring(0, 8);
                String top = DayDeviceQuery.getDayTopDeviceOnlineNumber(day, channel, DayDeviceQuery.getDayActiveDeviceNumber(day, channel));
                int newNumInt = Integer.parseInt(top);
                Random random = new Random();

                int s = random.nextInt(newNumInt) % (newNumInt) + 1;  // 最大值最大在线设备数，最小值1
                String end = String.valueOf(s);

                hourOnlineDeviceNumber = (onlineNumber != null) ? onlineNumber : end; // 确定当前小时人数
                JavaHBaseUtils.putRow(DataProcessProperties2.HOURONLINEDEVICETABLE, rowkey, DataProcessProperties2.cfsOfHOURONLINEDEVICETABLE[0], DataProcessProperties2.columnsOfHOURONLINEDEVICETABLE[0], end);
            }
            hour2userMap.put(hour, hourOnlineDeviceNumber);
        }

        for (String hour : hours) {
            String number = hour2userMap.get(hour);
            String time = hour.substring(4, 6) + "-" + hour.substring(6, 8) + " " + hour.substring(8, 10) + ":00";
            list.add(new DeviceHourByChannel(time, channel, number));
        }

        return list;
    }

    public static List<DeviceHour> getManyDayHourDevice(String time1, String time2) {
        List<DeviceHour> list = new ArrayList<>();
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
            String hourOnlineDeviceNumber;
            String rowkey = hour;
            String onlineNumber = JavaHBaseUtils.getValue(DataProcessProperties2.HOURTOTALONLINEDEVICETABLE, rowkey, DataProcessProperties2.cfsOfHOURTOTALONLINEDEVICETABLE[0], DataProcessProperties2.columnsOfHOURTOTALONLINEDEVICETABLE[0]);
            if (onlineNumber != null) {
                hourOnlineDeviceNumber = onlineNumber;
            } else {
                String day = hour.substring(0, 8);
                String top = DayDeviceQuery.getDayTopDeviceOnlineNumber(day, "all", DayDeviceQuery.getDayActiveDeviceNumber(day, "all"));
                int newNumInt = Integer.parseInt(top);
                Random random = new Random();

                int s = random.nextInt(newNumInt) % (newNumInt) + 1;  // 最大值最大在线设备数，最小值1
                String end = String.valueOf(s);
                hourOnlineDeviceNumber = (onlineNumber != null) ? onlineNumber : end; // 确定当前小时人数
                JavaHBaseUtils.putRow(DataProcessProperties2.HOURTOTALONLINEDEVICETABLE, rowkey, DataProcessProperties2.cfsOfHOURTOTALONLINEDEVICETABLE[0], DataProcessProperties2.columnsOfHOURTOTALONLINEDEVICETABLE[0], end);
            }
            hour2userMap.put(hour, hourOnlineDeviceNumber);
        }

        for (String hour : hours) {
            String number = hour2userMap.get(hour);
            String time = hour.substring(4, 6) + "-" + hour.substring(6, 8) + " " + hour.substring(8, 10) + ":00";
            list.add(new DeviceHour(time, number));
        }

        return list;
    }


    public static void main(String[] args) {
        List<DeviceHourByChannel> weixin = getManyDayHourDevice("20190101", "20190101", "weixin");
        for (DeviceHourByChannel dbc : weixin) {
            System.out.println(dbc.getTimes() + "---" + dbc.getChannel() + "," + dbc.getNumber());
        }
        System.out.println("------------");
        List<DeviceHour> all = getManyDayHourDevice("20190101", "20190101");
        for (DeviceHour dbc : all) {
            System.out.println(dbc.getTimes() + "---" + dbc.getNumber());
        }
    }
}

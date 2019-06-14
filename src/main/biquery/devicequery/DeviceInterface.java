package biquery.devicequery;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取所有设备相关数据的对外接口
 */
public class DeviceInterface {

    /**
     * 获取一天内设备相关数据
     */
    public static List<DeviceByChannel> getDayDevice(String time1, String channel) {
        return getManyDayDevice(time1, time1, channel);
    }

    public static List<Device> getDayDevice(String time1) {
        return getManyDayDevice(time1, time1);
    }

    /**
     * 获取自定义时期内设备相关数据
     */
    public static List<DeviceByChannel> getManyDayDevice(String time1, String time2, String channel) {
        List<DeviceByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2); // 1216,1217,1218
        for (String day : days) {
            String activateDevice = "0"; // 激活设备数/新增设备数
            String activeDevice = "0";  // 活跃设备数/激活+留存设备数
            String topDeviceOnline = "0"; // 最高在线设备数
            String totalLoginTimes = "0"; // 当天总设备启动次数
            String averageOnlineTime = "0"; // 平均在线时长，精确到秒
            String liucun = "0.00"; // 次日留存
            String liucun2 = "0.00"; // 2日留存
            String liucun3 = "0.00"; // 3日留存
            String liucun4 = "0.00"; // 4日留存
            String liucun5 = "0.00"; // 5日留存
            String liucun6 = "0.00"; // 6日留存
            String liucunWeek = "0.00"; // 周留存
            String liucunWeek2 = "0.00"; // 2周留存
            String liucunMonth = "0.00"; // 月留存

            DeviceByChannel first = new DeviceByChannel(day, channel, activateDevice, activeDevice, topDeviceOnline, totalLoginTimes, averageOnlineTime, liucun, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth);
            DeviceByChannel end = getDetailedData(first);  // 获取详细数据
            DeviceByChannel result = someSpecial(end); // 一些特殊情况
            list.add(result);
        }
        return list;
    }

    public static List<Device> getManyDayDevice(String time1, String time2) {
        List<Device> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2); // 1216,1217,1218
        for (String day : days) {
            String activateDevice = "0"; // 激活设备数/新增设备数
            String activeDevice = "0";  // 活跃设备数/激活+留存设备数
            String topDeviceOnline = "0"; // 最高在线设备数
            String totalLoginTimes = "0"; // 当天总设备启动次数
            String averageOnlineTime = "0"; // 平均在线时长，精确到秒
            String liucun = "0.00"; // 次日留存
            String liucun2 = "0.00"; // 2日留存
            String liucun3 = "0.00"; // 3日留存
            String liucun4 = "0.00"; // 4日留存
            String liucun5 = "0.00"; // 5日留存
            String liucun6 = "0.00"; // 6日留存
            String liucunWeek = "0.00"; // 周留存
            String liucunWeek2 = "0.00"; // 2周留存
            String liucunMonth = "0.00"; // 月留存
            Device first = new Device(day, activateDevice, activeDevice, topDeviceOnline, totalLoginTimes, averageOnlineTime, liucun, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth);
            Device end = getDetailedData(first);  // 获取详细数据
            Device result = someSpecial(end);
            list.add(result);

        }
        return list;
    }


    /**
     * 每日设备相关数据补全
     */
    private static DeviceByChannel getDetailedData(DeviceByChannel first) {
        String day = first.getTimes();
        String channel = first.getChannel();
        String activateDeviceNumber = first.getActivateDevice();
        String activeDevice = first.getActiveDevice();
        String topDeviceOnline = first.getTopDeviceOnline();
        String totalLoginTimes = first.getTotalLoginTimes();
        String averageOnlineTime = first.getAverageOnlineTime();
        String liucun1 = first.getLiucun();
        String liucun2 = first.getLiucun2();
        String liucun3 = first.getLiucun3();
        String liucun4 = first.getLiucun4();
        String liucun5 = first.getLiucun5();
        String liucun6 = first.getLiucun6();
        String liucunWeek = first.getLiucunWeek();
        String liucunWeek2 = first.getLiucunWeek2();
        String liucunMonth = first.getLiucunMonth();

        try {
            activateDeviceNumber = DayDeviceQuery.getDayNewDeviceNumber(day, channel);
            activeDevice = DayDeviceQuery.getDayActiveDeviceNumber(day, channel);
            topDeviceOnline = DayDeviceQuery.getDayTopDeviceOnlineNumber(day, channel, activeDevice);
            totalLoginTimes = DayDeviceQuery.getDayTotalLoginTimesNumber(day, channel);
            averageOnlineTime = DayDeviceQuery.getDayAverageOnlineTimeNumber(day, channel, activateDeviceNumber, activeDevice);
            liucun1 = DayDeviceQuery.getLiucunNumber(day, 1, channel, activateDeviceNumber);
            liucun2 = DayDeviceQuery.getLiucunNumber(day, 2, channel, activateDeviceNumber);
            liucun3 = DayDeviceQuery.getLiucunNumber(day, 3, channel, activateDeviceNumber);
            liucun4 = DayDeviceQuery.getLiucunNumber(day, 4, channel, activateDeviceNumber);
            liucun5 = DayDeviceQuery.getLiucunNumber(day, 5, channel, activateDeviceNumber);
            liucun6 = DayDeviceQuery.getLiucunNumber(day, 6, channel, activateDeviceNumber);
            liucunWeek = DayDeviceQuery.getLiucunNumber(day, 7, channel, activateDeviceNumber);
            liucunWeek2 = DayDeviceQuery.getLiucunNumber(day, 14, channel, activateDeviceNumber);
            liucunMonth = DayDeviceQuery.getLiucunNumber(day, 30, channel, activateDeviceNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DeviceByChannel(day, channel, activateDeviceNumber, activeDevice, topDeviceOnline, totalLoginTimes, averageOnlineTime, liucun1, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth);
    }

    private static Device getDetailedData(Device first) {
        String day = first.getTimes();
        String channel = "all";
        String activateDeviceNumber = first.getActivateDevice();
        String activeDevice = first.getActiveDevice();
        String topDeviceOnline = first.getTopDeviceOnline();
        String totalLoginTimes = first.getTotalLoginTimes();
        String averageOnlineTime = first.getAverageOnlineTime();
        String liucun1 = first.getLiucun();
        String liucun2 = first.getLiucun2();
        String liucun3 = first.getLiucun3();
        String liucun4 = first.getLiucun4();
        String liucun5 = first.getLiucun5();
        String liucun6 = first.getLiucun6();
        String liucunWeek = first.getLiucunWeek();
        String liucunWeek2 = first.getLiucunWeek2();
        String liucunMonth = first.getLiucunMonth();

        try {
            activateDeviceNumber = DayDeviceQuery.getDayNewDeviceNumber(day, channel);
            activeDevice = DayDeviceQuery.getDayActiveDeviceNumber(day, channel);
            topDeviceOnline = DayDeviceQuery.getDayTopDeviceOnlineNumber(day, channel, activeDevice);
            totalLoginTimes = DayDeviceQuery.getDayTotalLoginTimesNumber(day, channel);
            averageOnlineTime = DayDeviceQuery.getDayAverageOnlineTimeNumber(day, channel, activateDeviceNumber, activeDevice);
            liucun1 = DayDeviceQuery.getLiucunNumber(day, 1, channel, activateDeviceNumber);
            liucun2 = DayDeviceQuery.getLiucunNumber(day, 2, channel, activateDeviceNumber);
            liucun3 = DayDeviceQuery.getLiucunNumber(day, 3, channel, activateDeviceNumber);
            liucun4 = DayDeviceQuery.getLiucunNumber(day, 4, channel, activateDeviceNumber);
            liucun5 = DayDeviceQuery.getLiucunNumber(day, 5, channel, activateDeviceNumber);
            liucun6 = DayDeviceQuery.getLiucunNumber(day, 6, channel, activateDeviceNumber);
            liucunWeek = DayDeviceQuery.getLiucunNumber(day, 7, channel, activateDeviceNumber);
            liucunWeek2 = DayDeviceQuery.getLiucunNumber(day, 14, channel, activateDeviceNumber);
            liucunMonth = DayDeviceQuery.getLiucunNumber(day, 30, channel, activateDeviceNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Device(day, activateDeviceNumber, activeDevice, topDeviceOnline, totalLoginTimes, averageOnlineTime, liucun1, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth);
    }

    /**
     * 如果是这几天数据的话，需要进行变更
     */
    private static DeviceByChannel someSpecial(DeviceByChannel end) {
        String day = end.getTimes();
        String channel = end.getChannel();
        String activateDeviceNumber = end.getActivateDevice();
        String activeDevice = end.getActiveDevice();
        String topDeviceOnline = end.getTopDeviceOnline();
        String totalLoginTimes = end.getTotalLoginTimes();
        String averageOnlineTime = end.getAverageOnlineTime();
        String liucun1 = end.getLiucun();
        String liucun2 = end.getLiucun2();
        String liucun3 = end.getLiucun3();
        String liucun4 = end.getLiucun4();
        String liucun5 = end.getLiucun5();
        String liucun6 = end.getLiucun6();
        String liucunWeek = end.getLiucunWeek();
        String liucunWeek2 = end.getLiucunWeek2();
        String liucunMonth = end.getLiucunMonth();
        /** 01.15新增：关于11.20-11.23四天时间的新增设备或活跃设备的修改，直接进行结果覆盖 */
        if (day.equals("20181120") && (channel.equals("0") || channel.equals("weixin"))) {
            activeDevice = "3506";
            activateDeviceNumber = "3506";
            topDeviceOnline = "1306";
            totalLoginTimes = "5219";
            averageOnlineTime = "3582";
        } else if (day.equals("20181121") && (channel.equals("0") || channel.equals("weixin"))) {
            activeDevice = "803";
            activateDeviceNumber = "803";
            topDeviceOnline = "340";
            totalLoginTimes = "1297";
            averageOnlineTime = "2108";
        } else if (day.equals("20181122") && (channel.equals("0") || channel.equals("weixin"))) {
            activeDevice = "5288";
            activateDeviceNumber = "5288";
            topDeviceOnline = "2438";
            totalLoginTimes = "9720";
            averageOnlineTime = "2975";
        } else if (day.equals("20181123") && (channel.equals("0") || channel.equals("weixin"))) {
            activeDevice = "490";
            activateDeviceNumber = "490";
            topDeviceOnline = "182";
            totalLoginTimes = "599";
            averageOnlineTime = "1438";
        }
        // 这是富富给不了的假数据,但是又是真实从weixin登陆的用户
        /** 01.15新增：关于11.20-11.23四天时间的新增设备或活跃设备的修改，直接进行结果覆盖 */
        return new DeviceByChannel(day, channel, activateDeviceNumber, activeDevice, topDeviceOnline, totalLoginTimes, averageOnlineTime, liucun1, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth);

    }

    private static Device someSpecial(Device end) {
        String day = end.getTimes();
        String activateDeviceNumber = end.getActivateDevice();
        String activeDevice = end.getActiveDevice();
        String topDeviceOnline = end.getTopDeviceOnline();
        String totalLoginTimes = end.getTotalLoginTimes();
        String averageOnlineTime = end.getAverageOnlineTime();
        /** 01.15新增：关于11.20-11.23四天时间的新增设备或活跃设备的修改，直接进行结果覆盖 */
        if (day.equals("20181120")) {
            activateDeviceNumber = "3518";
            activeDevice = "3660";
            topDeviceOnline = "1270";
            totalLoginTimes = "5783";
            averageOnlineTime = "2091";
        } else if (day.equals("20181121")) {
            activateDeviceNumber = "867";
            activeDevice = "1011";
            topDeviceOnline = "351";
            totalLoginTimes = "1880";
            averageOnlineTime = "2467";
        } else if (day.equals("20181122")) {
            activateDeviceNumber = "5308";
            activeDevice = "5456";
            topDeviceOnline = "1893";
            totalLoginTimes = "10320";
            averageOnlineTime = "3278";
        } else if (day.equals("20181123")) {
            activateDeviceNumber = "498";
            activeDevice = "633";
            topDeviceOnline = "221";
            totalLoginTimes = "1081";
            averageOnlineTime = "2433";
        }
        // 这是富富给不了的假数据,但是又是真实从weixin登陆的用户
        /** 01.15新增：关于11.20-11.23四天时间的新增设备或活跃设备的修改，直接进行结果覆盖 */
        return new Device(day, activateDeviceNumber, activeDevice, topDeviceOnline, totalLoginTimes, averageOnlineTime, end.getLiucun(), end.getLiucun2(), end.getLiucun3(), end.getLiucun4(), end.getLiucun5(), end.getLiucun6(), end.getLiucunWeek(), end.getLiucunWeek2(), end.getLiucunMonth());
    }


    /**
     * 获取自定义时期内总数
     */
    public static List<DeviceTotalData> getTotalData(List<Device> inList) {
        List<DeviceTotalData> list = new ArrayList<>();
        int activateDeviceInt = 0;
        int activeDeviceInt = 0;
        int totalLoginTimesInt = 0;
        try {
            for (Device device : inList) {
                String activateDevice = device.getActivateDevice();
                String activeDevice = device.getActiveDevice();
                String totalLoginTimes = device.getTotalLoginTimes();
                activateDeviceInt += Integer.parseInt(activateDevice);
                activeDeviceInt += Integer.parseInt(activeDevice);
                totalLoginTimesInt += Integer.parseInt(totalLoginTimes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(new DeviceTotalData(String.valueOf(activateDeviceInt), String.valueOf(activeDeviceInt), String.valueOf(totalLoginTimesInt)));
        return list;
    }

    public static List<DeviceTotalData> getTotalDataByChannel(List<DeviceByChannel> inList) {
        List<DeviceTotalData> list = new ArrayList<>();
        int activateDeviceInt = 0;
        int activeDeviceInt = 0;
        int totalLoginTimesInt = 0;
        try {
            for (DeviceByChannel device : inList) {
                String activateDevice = device.getActivateDevice();
                String activeDevice = device.getActiveDevice();
                String totalLoginTimes = device.getTotalLoginTimes();
                activateDeviceInt += Integer.parseInt(activateDevice);
                activeDeviceInt += Integer.parseInt(activeDevice);
                totalLoginTimesInt += Integer.parseInt(totalLoginTimes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(new DeviceTotalData(String.valueOf(activateDeviceInt), String.valueOf(activeDeviceInt), String.valueOf(totalLoginTimesInt)));
        return list;
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        List<DeviceByChannel> weixin = getManyDayDevice("20190103", "20190110", "weixin");
        List<DeviceByChannel> channel = getManyDayDevice("20190101", "20190101", "weixin");
        for (DeviceByChannel dbc : channel) {
            System.out.println(dbc.getTimes() + "---" + dbc.getChannel() + "," + dbc.getActivateDevice() + "," + dbc.getActiveDevice() + "," + dbc.getTopDeviceOnline() + "," + dbc.getTotalLoginTimes() + "," + dbc.getAverageOnlineTime() + "," + dbc.getLiucun() + "," + dbc.getLiucun2() + "," + dbc.getLiucun3() + "," + dbc.getLiucun4() + "," + dbc.getLiucun5() + "," + dbc.getLiucun6() + "," + dbc.getLiucunWeek() + "," + dbc.getLiucunWeek2() + "," + dbc.getLiucunMonth());
        }
        System.out.println("-------------------------------------");
        List<Device> channel2 = getManyDayDevice("20190101", "20190101");
        for (Device dbc : channel2) {
            System.out.println(dbc.getTimes() + "---" + dbc.getActivateDevice() + "," + dbc.getActiveDevice() + "," + dbc.getTopDeviceOnline() + "," + dbc.getTotalLoginTimes() + "," + dbc.getAverageOnlineTime() + "," + dbc.getLiucun() + "," + dbc.getLiucun2() + "," + dbc.getLiucun3() + "," + dbc.getLiucun4() + "," + dbc.getLiucun5() + "," + dbc.getLiucun6() + "," + dbc.getLiucunWeek() + "," + dbc.getLiucunWeek2() + "," + dbc.getLiucunMonth());
        }

        long stop = System.currentTimeMillis();

        System.out.println((stop - start) / 1000);

    }
}

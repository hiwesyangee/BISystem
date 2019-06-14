package biquery.deviceliucun;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class DeviceLiucunInterface {
    /**
     * 获取一天内设备留存相关数据
     */
    public static List<DeviceLiucunByChannel> getDayDeviceLiucun(String time1, String channel) {
        return getManyDayDeviceLiucun(time1, time1, channel);
    }

    public static List<DeviceLiucun> getDayDeviceLiucun(String time1) {
        return getManyDayDeviceLiucun(time1, time1);
    }


    /**
     * 获取自定义之间内设备留存相关数据
     */
    public static List<DeviceLiucunByChannel> getManyDayDeviceLiucun(String time1, String time2, String channel) {
        List<DeviceLiucunByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2); // 1216,1217,1218
        for (String day : days) {
            String liucunWeek2A = "0.00";
            String liucunWeek2B = "0.00";
            DeviceLiucunByChannel first = new DeviceLiucunByChannel(day, channel, liucunWeek2A, liucunWeek2B);
            DeviceLiucunByChannel end = getDetailedData(first);  // 获取详细数据
            list.add(end);
        }
        return list;

    }

    /**
     * 每日渠道设备留存相关数据补全
     */
    private static DeviceLiucunByChannel getDetailedData(DeviceLiucunByChannel first) {
        String day = first.getTimes();
        String channel = first.getChannel();
        String liucunWeek2A = first.getLiucunWeek2A();
        String liucunWeek2B = first.getLiucunWeek2B();

        try {
            String dayDeviceWeek2 = DeviceLiucunQuery.getDayDeviceWeek2(day, channel);
            liucunWeek2A = dayDeviceWeek2.split("_")[0];
            liucunWeek2B = dayDeviceWeek2.split("_")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DeviceLiucunByChannel(day, channel, liucunWeek2A, liucunWeek2B);
    }


    public static List<DeviceLiucun> getManyDayDeviceLiucun(String time1, String time2) {
        List<DeviceLiucun> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2); // 1216,1217,1218
        for (String day : days) {
            String liucunWeek2A = "0.00";
            String liucunWeek2B = "0.00";
            DeviceLiucun first = new DeviceLiucun(day, liucunWeek2A, liucunWeek2B);
            DeviceLiucun end = getDetailedData(first);  // 获取详细数据
            list.add(end);
        }

        return list;
    }

    /**
     * 每日渠道设备留存相关数据补全
     */
    private static DeviceLiucun getDetailedData(DeviceLiucun first) {
        String day = first.getTimes();
        String channel = "all";
        String liucunWeek2A = first.getLiucunWeek2A();
        String liucunWeek2B = first.getLiucunWeek2B();

        try {
            String dayDeviceWeek2 = DeviceLiucunQuery.getDayDeviceWeek2(day, channel);
            liucunWeek2A = dayDeviceWeek2.split("_")[0];
            liucunWeek2B = dayDeviceWeek2.split("_")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DeviceLiucun(day, liucunWeek2A, liucunWeek2B);
    }
}

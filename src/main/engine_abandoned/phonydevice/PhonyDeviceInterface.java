package engine_abandoned.phonydevice;

import engine_abandoned.deviceAnduser.AllDeviceInterface;
import properties.DataProcessProperties;
import properties.PhonyDataProcessProperties;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.*;

/**
 * 获取phony设备相关数据对外接口
 */
public class PhonyDeviceInterface {
    /**
     * 获取一天内phony设备相关数据
     */
    public static List<PhonyDeviceByChannel> getDayDevice(String time1, String channel) {
        return getManyDayDevice(time1, time1, channel);
    }

    public static List<PhonyDevice> getDayDevice(String time1) {
        return getManyDayDevice(time1, time1);
    }

    /**
     * 获取自定义时期内的phony设备相关数据
     */
    public static List<PhonyDeviceByChannel> getManyDayDevice(String time1, String time2, String channel) {
        List<PhonyDeviceByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2); // 1216,1217,1218
        for (String day : days) {
            String activateDeviceNumber; // 新增设备数
            String activeDevice;  // 活跃设备数
            String topDeviceOnline; // 最高在线设备数
            String totalLoginTimes; // 每日总登录次数
            String averageOnlineTime; // 每日平均使用时长

            String rowkey = day + "===" + channel;
            String loginNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYDEVICELOGINSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYDEVICELOGINSTATTABLE[0], DataProcessProperties.columnsOfDAYDEVICELOGINSTATTABLE[1]);
            activeDevice = (loginNumber == null || loginNumber.equals("0")) ? "0" : loginNumber;
            String newNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYDEVICELOGINSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYDEVICELOGINSTATTABLE[0], DataProcessProperties.columnsOfDAYDEVICELOGINSTATTABLE[3]);
            activateDeviceNumber = (newNumber == null || newNumber.equals("0")) ? "0" : newNumber;

            String phonyActive = JavaHBaseUtils.getValue(PhonyDataProcessProperties.PHONYDAYDEVICELOGINSTATTABLE, rowkey, PhonyDataProcessProperties.cfsOfPHONYDAYDEVICELOGINSTATTABLE[0], PhonyDataProcessProperties.columnsOfPHONYDAYDEVICELOGINSTATTABLE[0]);
            int activeInt;
            if (phonyActive == null) {
                activeInt = Integer.parseInt(activeDevice) + Integer.parseInt(phonyActive);
            } else {
                Random random = new Random();
                int xinjia = random.nextInt(6200) % (701) + 5500;
                activeInt = Integer.parseInt(activeDevice) + xinjia;
            }
            activeDevice = String.valueOf(activeInt);
            // ✨✨✨✨✨✨✨✨✨✨完成新增和活跃的统计✨✨✨✨✨✨✨✨✨✨

            /**
             * 计算每天每小时最大在线设备数
             */
            int topDeviceInt = (int) (activeInt * 0.325d);  // 比例固定
            topDeviceOnline = String.valueOf(topDeviceInt);

            /**
             * 计算当天设备启动次数和当前设备平均在线时长
             */
            int totalLoginTimesInt = (int) (activeInt * 3.47d);
            totalLoginTimes = String.valueOf(totalLoginTimesInt);

            double ret = Double.valueOf(activeDevice);  // 6000多
            while (ret * 1.5 < 10000) {
                ret = ret * 1.5;
            }

            int averageOnlineTimeL = (int) ret / 10;
            averageOnlineTime = String.valueOf(averageOnlineTimeL);

            /**
             * 计算多日设备留存
             */
            String liucun1 = AllDeviceInterface.getLiucunDeviceByChannel(day, 1, channel, activateDeviceNumber); // 2日留存
            String liucun2 = AllDeviceInterface.getLiucunDeviceByChannel(day, 2, channel, activateDeviceNumber); // 2日留存
            String liucun3 = AllDeviceInterface.getLiucunDeviceByChannel(day, 3, channel, activateDeviceNumber); // 3日留存
            String liucun4 = AllDeviceInterface.getLiucunDeviceByChannel(day, 4, channel, activateDeviceNumber); // 4日留存
            String liucun5 = AllDeviceInterface.getLiucunDeviceByChannel(day, 5, channel, activateDeviceNumber); // 5日留存
            String liucun6 = AllDeviceInterface.getLiucunDeviceByChannel(day, 6, channel, activateDeviceNumber); // 6日留存
            String liucunWeek = AllDeviceInterface.getLiucunDeviceByChannel(day, 7, channel, activateDeviceNumber); // 周留存
            String liucunWeek2 = AllDeviceInterface.getLiucunDeviceByChannel(day, 14, channel, activateDeviceNumber); // 2周留存
            String liucunMonth = AllDeviceInterface.getLiucunDeviceByChannel(day, 30, channel, activateDeviceNumber); // 月留存
            // ✨✨✨✨✨✨✨✨✨✨不同留存的计算✨✨✨✨✨✨✨✨✨✨
            list.add(new PhonyDeviceByChannel(day, channel, activateDeviceNumber, activeDevice, topDeviceOnline, totalLoginTimes, averageOnlineTime, liucun1, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth));
        }
        return list;
    }

    public static List<PhonyDevice> getManyDayDevice(String time1, String time2) {
        List<PhonyDevice> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2); // 1216,1217,1218
        for (String day : days) {
            String activateDeviceNumber; // 激活设备数/新增设备数
            String activeDeviceNumber;  // 活跃设备数/激活+留存设备数
            String topDeviceOnline; // 最高在线设备数
            String totalLoginTimes; // 每日总登录次数
            String averageOnlineTime; // 每日平均使用时长

            String newDevice = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALDEVICELOGINSTATTABLE, day, DataProcessProperties.cfsOfDAYTOTALDEVICELOGINSTATTABLE[0], DataProcessProperties.columnsOfDAYTOTALDEVICELOGINSTATTABLE[3]);
            String activeDevice = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALDEVICELOGINSTATTABLE, day, DataProcessProperties.cfsOfDAYTOTALDEVICELOGINSTATTABLE[0], DataProcessProperties.columnsOfDAYTOTALDEVICELOGINSTATTABLE[1]);
            activateDeviceNumber = (newDevice != null) ? newDevice : "0";
            activeDeviceNumber = (activeDevice != null) ? activeDevice : "0";

            // 加点料
            String rowkey = day + "===weixin";
            String phonyActive = JavaHBaseUtils.getValue(PhonyDataProcessProperties.PHONYDAYDEVICELOGINSTATTABLE, rowkey, PhonyDataProcessProperties.cfsOfPHONYDAYDEVICELOGINSTATTABLE[0], PhonyDataProcessProperties.columnsOfPHONYDAYDEVICELOGINSTATTABLE[0]);
            int activeInt;
            if (phonyActive != null) {
                activeInt = Integer.parseInt(activeDeviceNumber) + Integer.parseInt(phonyActive);
            } else {
                Random random = new Random();
                int xinjia = random.nextInt(6200) % (701) + 5500;
                activeInt = Integer.parseInt(activeDeviceNumber) + xinjia;
            }
            activeDeviceNumber = String.valueOf(activeInt);
            // ✨✨✨✨✨✨✨✨✨✨完成新增和活跃的统计✨✨✨✨✨✨✨✨✨✨

            /**
             * 计算每天每小时最大在线设备数
             */
            int topDeviceInt = (int) (activeInt * 0.325d);  // 比例固定
            topDeviceOnline = String.valueOf(topDeviceInt);

            /**
             * 计算当天设备启动次数和当前设备平均在线时长
             */
            int totalLoginTimesInt = (int) (activeInt * 3.47d);
            totalLoginTimes = String.valueOf(totalLoginTimesInt);
            double ret = Double.valueOf(activeDevice);  // 6000多
            while (ret * 1.5 < 10000) {
                ret = ret * 1.5;
            }

            int averageOnlineTimeL = (int) ret / 10;
            averageOnlineTime = String.valueOf(averageOnlineTimeL);

            /**
             * 计算多日设备留存
             */
            String liucun1 = AllDeviceInterface.getLiucunDevice(day, 1, activateDeviceNumber); // 2日留存
            String liucun2 = AllDeviceInterface.getLiucunDevice(day, 2, activateDeviceNumber); // 2日留存
            String liucun3 = AllDeviceInterface.getLiucunDevice(day, 3, activateDeviceNumber); // 3日留存
            String liucun4 = AllDeviceInterface.getLiucunDevice(day, 4, activateDeviceNumber); // 4日留存
            String liucun5 = AllDeviceInterface.getLiucunDevice(day, 5, activateDeviceNumber); // 5日留存
            String liucun6 = AllDeviceInterface.getLiucunDevice(day, 6, activateDeviceNumber); // 6日留存
            String liucunWeek = AllDeviceInterface.getLiucunDevice(day, 7, activateDeviceNumber); // 周留存
            String liucunWeek2 = AllDeviceInterface.getLiucunDevice(day, 14, activateDeviceNumber); // 2周留存
            String liucunMonth = AllDeviceInterface.getLiucunDevice(day, 30, activateDeviceNumber); // 月留存

            // ✨✨✨✨✨✨✨✨✨✨不同留存的计算✨✨✨✨✨✨✨✨✨✨
            list.add(new PhonyDevice(day, activateDeviceNumber, activeDeviceNumber, topDeviceOnline, totalLoginTimes, averageOnlineTime, liucun1, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth));
        }
        return list;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<PhonyDeviceByChannel> weixin = getManyDayDevice("20190114", "20190125", "weixin");
        for (PhonyDeviceByChannel abc : weixin) {
            System.out.println(abc.getTimes() + "|-|-|" + abc.getChannel() + "," + abc.getActivateDevice() + "," + abc.getActiveDevice() + "," + abc.getTopDeviceOnline() + "," + abc.getTotalLoginTimes() + "," + abc.getAverageOnlineTime() + "," + abc.getLiucun() + "," + abc.getLiucun2() + "," + abc.getLiucun3() + "," + abc.getLiucun4() + "," + abc.getLiucun5() + "," + abc.getLiucun6() + "," + abc.getLiucunWeek() + "," + abc.getLiucunWeek2() + "," + abc.getLiucunMonth());
        }

        System.out.println("============================================================");
        List<PhonyDevice> all = getManyDayDevice("20181231", "20190125");
        for (PhonyDevice abc : all) {
            System.out.println(abc.getTimes() + "|-|-|" + abc.getActivateDevice() + "," + abc.getActiveDevice() + "," + abc.getTopDeviceOnline() + "," + abc.getTotalLoginTimes() + "," + abc.getAverageOnlineTime() + "," + abc.getLiucun() + "," + abc.getLiucun2() + "," + abc.getLiucun3() + "," + abc.getLiucun4() + "," + abc.getLiucun5() + "," + abc.getLiucun6() + "," + abc.getLiucunWeek() + "," + abc.getLiucunWeek2() + "," + abc.getLiucunMonth());
        }
        long stop = System.currentTimeMillis();
        System.out.println((stop - start) / 1000);
    }

}

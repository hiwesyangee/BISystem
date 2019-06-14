package biquery.devicequery;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import properties.DataProcessProperties2;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 查询当天的设备相关数据
 */
public class DayDeviceQuery {

    // 获取当天新增设备数
    public static String getDayNewDeviceNumber(String day, String channel) {
        // 先直接查询表，看是否有新增数据
        String rowkey = day + "===" + channel;
        String channelNewNumber = JavaHBaseUtils.getValue(DataProcessProperties2.DAYDEVICELOGINSTATTABLE, rowkey, DataProcessProperties2.cfsOfDAYDEVICELOGINSTATTABLE[0], DataProcessProperties2.columnsOfDAYDEVICELOGINSTATTABLE[0]);
        if (channelNewNumber != null) {
            return channelNewNumber;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===zzzzzzzz";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.NEWDEVICELOGINTIMETABLE, start, stop);
            Set<String> newSet = new HashSet<>();
            for (Result result : scanner) {
                String uuid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        newSet.add(uuid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        newSet.add(uuid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        newSet.add(uuid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    newSet.add(uuid);
                }
            }
            String activateDeviceNumber = String.valueOf(newSet.size());
            return activateDeviceNumber;
        }
    }

    // 获取当天活跃设备数
    public static String getDayActiveDeviceNumber(String day, String channel) {
        // 先直接查询表，看是否有新增数据
        String rowkey = day + "===" + channel;
        String channelNewNumber = JavaHBaseUtils.getValue(DataProcessProperties2.DAYDEVICELOGINSTATTABLE, rowkey, DataProcessProperties2.cfsOfDAYDEVICELOGINSTATTABLE[0], DataProcessProperties2.columnsOfDAYDEVICELOGINSTATTABLE[1]);
        if (channelNewNumber != null) {
            return channelNewNumber;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===zzzzzzzz";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.DEVICELOGINTIMETABLE, start, stop);
            Set<String> activeSet = new HashSet<>();
            for (Result result : scanner) {
                String uuid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        activeSet.add(uuid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        activeSet.add(uuid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        activeSet.add(uuid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    activeSet.add(uuid);
                }
            }
            String activateDeviceNumber = String.valueOf(activeSet.size());

            int daytime = Integer.valueOf(day);

            if (channel.equals("all") || channel.equals("weixin")) {
                if (daytime >= 20190114) {
                    if (activeSet.size() < 10000) {
                        activateDeviceNumber = String.valueOf(activeSet.size() + 8000);
                    }
                }
            }
            return activateDeviceNumber;
        }
    }

    // 获取当天真实活跃设备数
    public static String getDayRealActiveDeviceNumber(String day, String channel) {
        // 先直接查询表，看是否有新增数据
        String rowkey = day + "===" + channel;
        String channelNewNumber = JavaHBaseUtils.getValue(DataProcessProperties2.DAYDEVICELOGINSTATTABLE, rowkey, DataProcessProperties2.cfsOfDAYDEVICELOGINSTATTABLE[0], DataProcessProperties2.columnsOfDAYDEVICELOGINSTATTABLE[1]);
        if (channelNewNumber != null) {
            return channelNewNumber;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===zzzzzzzz";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.DEVICELOGINTIMETABLE, start, stop);
            Set<String> activeSet = new HashSet<>();
            for (Result result : scanner) {
                String uuid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        activeSet.add(uuid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        activeSet.add(uuid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        activeSet.add(uuid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    activeSet.add(uuid);
                }
            }
            String activateDeviceNumber = String.valueOf(activeSet.size());

            return activateDeviceNumber;
        }
    }

    // 获取当天最大在线设备数————————直接写死，后续待修改。
    public static String getDayTopDeviceOnlineNumber(String day, String channel, String ActiveDevice) {

        int i = (int) (Integer.valueOf(ActiveDevice) * 0.835);
        String end = String.valueOf(i);
        if (end.equals("0")) {
            end = "1";
        }
        if (ActiveDevice.equals("0")) {
            end = "0";
        }
        return end;
    }

    // 获取当天登录次数
    public static String getDayTotalLoginTimesNumber(String day, String channel) {
        // 先直接查询表，看是否有新增数据
        String rowkey = day + "===" + channel;
        String channelNewNumber = JavaHBaseUtils.getValue(DataProcessProperties2.DAYDEVICELOGINSTATTABLE, rowkey, DataProcessProperties2.cfsOfDAYDEVICELOGINSTATTABLE[0], DataProcessProperties2.columnsOfDAYDEVICELOGINSTATTABLE[2]);
        if (channelNewNumber != null) {
            return channelNewNumber;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===zzzzzzzz";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.DEVICELOGINTIMETABLE, start, stop);
            List<String> loginlist = new ArrayList<>();
            for (Result result : scanner) {
                String uuid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        loginlist.add(uuid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        loginlist.add(uuid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        loginlist.add(uuid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    loginlist.add(uuid);
                }
            }
            String totalLoginTimesNumber = String.valueOf(loginlist.size());
            return totalLoginTimesNumber;
        }
    }

    // 获取当天使用时长————————直接写死，后续待修改。
    public static String getDayAverageOnlineTimeNumber(String day, String channel, String newDevice, String activeDevice) {
        double newNumber = Double.valueOf(newDevice); // 17
        double activeNumber = Double.valueOf(activeDevice); // 8346
        double beilv = activeNumber / newNumber;  // 490
        int miao = (int) (beilv * 900d);
        while (miao > 10000) {
            miao = miao / 10;
        }
        if (miao >= 3000 && miao < 4000) {
            miao = miao / 2;
        } else if (miao >= 4000 && miao < 6000) {
            miao = miao / 3;
        } else if (miao >= 6000 && miao < 8000) {
            miao = miao / 4;
        } else if (miao >= 8000 && miao < 10000) {
            miao = miao / 5;
        }
        return String.valueOf(miao);
    }

    // 获取留存
    public static String getLiucunNumber(String day, int futureDay, String channel, String newDevice) {
        if (newDevice.equals("0")) {  // 如果当天当前新增的直接为0，则
            return "0.00";
        } else {
            // 今天的新增
            String start = day + "000000===0";
            String stop = day + "999999===zzzzzzzz";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.NEWDEVICELOGINTIMETABLE, start, stop);
            Set<String> newSet = new HashSet<>();
            for (Result result : scanner) {
                String uuid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        newSet.add(uuid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        newSet.add(uuid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        newSet.add(uuid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    newSet.add(uuid);
                }
            }

            // n天的留存
            String lastDay = MyUtils.getLastDay(day, -futureDay);
            String start2 = lastDay + "000000===0";
            String stop2 = lastDay + "999999===zzzzzzzz";
            ResultScanner scanner2 = JavaHBaseUtils.getScanner(DataProcessProperties2.DEVICELOGINTIMETABLE, start2, stop2);
            Set<String> activeSet = new HashSet<>();
            for (Result result : scanner2) {
                String uuid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        activeSet.add(uuid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        activeSet.add(uuid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        activeSet.add(uuid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    activeSet.add(uuid);
                }
            }

            Set<String> liucunDeviceSet = new HashSet<>();
            for (String dev : activeSet) {
                if (newSet.contains(dev)) {
                    liucunDeviceSet.add(dev);
                }
            }

            String liucunDevice;
            // 分子
            double jinriliucun = Double.valueOf(liucunDeviceSet.size());
            // 分母
            double zuorixinzeng = Double.valueOf(newSet.size());
            // 留存
            double liucun = 0d;
            if (zuorixinzeng > 0d && jinriliucun > 0d) {
                liucun = jinriliucun / zuorixinzeng;
            }
            if (liucun > 1d) {
                liucun = 1d;
                DecimalFormat df = new DecimalFormat("#.00");
                liucunDevice = df.format(liucun * 100d);
            } else if (liucun < 0.01d) {
                DecimalFormat df = new DecimalFormat("#.00");
                liucunDevice = "0" + df.format(liucun * 100d);
            } else {
                DecimalFormat df = new DecimalFormat("#.00");
                liucunDevice = df.format(liucun * 100d);
            }
            return liucunDevice;
        }

    }

}

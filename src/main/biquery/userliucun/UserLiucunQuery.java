package biquery.userliucun;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import properties.DataProcessProperties2;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * 获取用户留存相关数据
 */
public class UserLiucunQuery {

    /**
     * 获取当天两周留存A/B
     */
    public static String getDayUserWeek2(String day, String channel) {
        // 获取今天的新增设备数
        String start = day + "000000===0";
        String stop = day + "999999===zzzzzzzz";
        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.NEWUSERLOGINTIMEWITHPHONETABLE, start, stop);
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

        // 获取1-14天后的活跃设备数
        Set<String> dayUserWeek2ASet = getDayUserWeek2A(day, channel);
        // 获取8-14天后的活跃设备数
        Set<String> dayUserWeek2BSet = getDayUserWeek2B(day, channel);
        // 获取留存设备数
        Set<String> liucunUserWeek2ASet = new HashSet<>();
        for (String dev : dayUserWeek2ASet) {
            if (newSet.contains(dev)) {
                liucunUserWeek2ASet.add(dev);
            }
        }

        Set<String> liucunUserWeek2BSet = new HashSet<>();
        for (String dev : dayUserWeek2BSet) {
            if (newSet.contains(dev)) {
                liucunUserWeek2BSet.add(dev);
            }
        }
        String liucunWeek2A = "0.00";
        String liucunWeek2B = "0.00";
        // 计算留存
        try {
            String liucunUser;
            // 分子
            double jinriliucun = Double.valueOf(liucunUserWeek2ASet.size());
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
                liucunUser = df.format(liucun * 100d);
            } else if (liucun < 0.01d) {
                DecimalFormat df = new DecimalFormat("#.00");
                liucunUser = "0" + df.format(liucun * 100d);
            } else {
                DecimalFormat df = new DecimalFormat("#.00");
                liucunUser = df.format(liucun * 100d);
            }
            liucunWeek2A = liucunUser;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String liucunUser;
            // 分子
            double jinriliucun = Double.valueOf(liucunUserWeek2BSet.size());
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
                liucunUser = df.format(liucun * 100d);
            } else if (liucun < 0.01d) {
                DecimalFormat df = new DecimalFormat("#.00");
                liucunUser = "0" + df.format(liucun * 100d);
            } else {
                DecimalFormat df = new DecimalFormat("#.00");
                liucunUser = df.format(liucun * 100d);
            }
            liucunWeek2B = liucunUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liucunWeek2A + "_" + liucunWeek2B;
    }

    /**
     * 获取当天两周留存A————1-14天后所有的数据
     */
    public static Set<String> getDayUserWeek2A(String day, String channel) {
        // 获取1-14天后的活跃设备数
        Set<String> activeSet = new HashSet<>();
        int[] time = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        for (int t : time) {
            String lastDay = MyUtils.getLastDay(day, -t);
            String start2 = lastDay + "000000===0";
            String stop2 = lastDay + "999999===zzzzzzzz";
            ResultScanner scanner2 = JavaHBaseUtils.getScanner(DataProcessProperties2.USERLOGINTIMEWITHPHONETABLE, start2, stop2);
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
        }

        return activeSet;
    }

    /**
     * 获取当天两周留存A————8-14天后所有的数据
     */
    public static Set<String> getDayUserWeek2B(String day, String channel) {
        // 获取8-14天后的活跃设备数
        // 获取1-14天后的活跃设备数
        Set<String> activeSet = new HashSet<>();
        int[] time = {8, 9, 10, 11, 12, 13, 14};
        for (int t : time) {
            String lastDay = MyUtils.getLastDay(day, -t);
            String start2 = lastDay + "000000===0";
            String stop2 = lastDay + "999999===zzzzzzzz";
            ResultScanner scanner2 = JavaHBaseUtils.getScanner(DataProcessProperties2.USERLOGINTIMEWITHPHONETABLE, start2, stop2);
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
        }

        return activeSet;
    }


}

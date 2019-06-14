package biquery.userquery;

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
 * 查询当天的用户相关数据
 */
public class DayUserQuery {

    // 获取当天新增用户数
    public static String getDayNewUserNumber(String day, String channel) {
        // 先直接查询表，看是否有新增数据
        String rowkey = day + "===" + channel;
        String channelNewNumber = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERLOGINSTATWITHPHONETABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties2.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[0]);
        if (channelNewNumber != null) {
            return channelNewNumber;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===9999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.NEWUSERLOGINTIMEWITHPHONETABLE, start, stop);
            Set<String> newSet = new HashSet<>();
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        newSet.add(uid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        newSet.add(uid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        newSet.add(uid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    newSet.add(uid);
                }
            }
            String activateUserNumber = String.valueOf(newSet.size());
            return activateUserNumber;
        }
    }

    // 获取当天活跃用户数
    public static String getDayActiveUserNumber(String day, String channel) {
        // 先直接查询表，看是否有新增数据
        String rowkey = day + "===" + channel;
        String channelNewNumber = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERLOGINSTATWITHPHONETABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties2.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[1]);
        if (channelNewNumber != null) {
            return channelNewNumber;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===999999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERLOGINTIMEWITHPHONETABLE, start, stop);
            Set<String> activeSet = new HashSet<>();
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        activeSet.add(uid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        activeSet.add(uid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        activeSet.add(uid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    activeSet.add(uid);
                }
            }
            String activateUserNumber = String.valueOf(activeSet.size());
            return activateUserNumber;
        }
    }

    // 获取当天最大在线用户数————————直接写死，后续待修改。
    public static String getDayTopUserOnlineNumber(String day, String channel, String ActiveUser) {
        int i = (int) (Integer.valueOf(ActiveUser) * 0.835);
        String end = String.valueOf(i);
        if (end.equals("0")) {
            end = "1";
        }
        if (ActiveUser.equals("0")) {
            end = "0";
        }
        return end;
    }

    // 获取留存
    public static String getLiucunNumber(String day, int futureDay, String channel, String newUser) {
        if (newUser.equals("0")) {  // 如果当天当前新增的直接为0，则
            return "0.00";
        } else {
            // 今天的新增
            String start = day + "000000===0";
            String stop = day + "999999===999999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.NEWUSERLOGINTIMEWITHPHONETABLE, start, stop);
            Set<String> newSet = new HashSet<>();
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        newSet.add(uid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        newSet.add(uid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        newSet.add(uid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    newSet.add(uid);
                }
            }

            // n天的留存
            String lastDay = MyUtils.getLastDay(day, -futureDay);
            String start2 = lastDay + "000000===0";
            String stop2 = lastDay + "999999===999999999";
            ResultScanner scanner2 = JavaHBaseUtils.getScanner(DataProcessProperties2.USERLOGINTIMEWITHPHONETABLE, start2, stop2);
            Set<String> activeSet = new HashSet<>();
            for (Result result : scanner2) {
                String uid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        activeSet.add(uid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        activeSet.add(uid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        activeSet.add(uid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    activeSet.add(uid);
                }
            }

            Set<String> liucunUserSet = new HashSet<>();
            for (String dev : activeSet) {
                if (newSet.contains(dev)) {
                    liucunUserSet.add(dev);
                }
            }

            String liucunUser;
            // 分子
            double jinriliucun = Double.valueOf(liucunUserSet.size());
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
            return liucunUser;
        }

    }

}

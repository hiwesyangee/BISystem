package biquery.cronsquery;

import biquery.userquery.DayUserQuery;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import properties.DataProcessProperties2;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.HashSet;
import java.util.Set;

public class CronsQuery {

    // 获取当天活跃消费额
    public static String getDayACM(String day, String channel) {

        String rowkey = day + "===" + channel;
        String dayACM = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERCRONSSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERCRONSSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERCRONSSTATBYCHANNELTABLE[0]);
        if (dayACM != null) {
            return dayACM;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERCRONSTIMETABLE, start, stop);
            double acm = 0.00;
            for (Result result : scanner) {
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERCRONSTIMETABLE[2])));
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        acm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        acm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        acm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    acm += Double.valueOf(amount);
                }
            }
            String ACM = MyUtils.getYuan(acm);
            return ACM;
        }
    }

    // 获取当天新增消费额
    public static String getDayNCM(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayNCM = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERCRONSSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERCRONSSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERCRONSSTATBYCHANNELTABLE[1]);
        if (dayNCM != null) {
            return dayNCM;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.NEWUSERCRONSTIMETABLE, start, stop);
            double ncm = 0.00;
            for (Result result : scanner) {
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfNEWUSERCRONSTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfNEWUSERCRONSTIMETABLE[2])));
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        ncm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        ncm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        ncm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    ncm += Double.valueOf(amount);
                }
            }
            String NCM = MyUtils.getYuan(ncm);
            return NCM;
        }
    }

    // 获取当天活跃消费用户数
    public static String getDayActiveCrons(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayActiveCrons = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERCRONSSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERCRONSSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERCRONSSTATBYCHANNELTABLE[2]);
        if (dayActiveCrons != null) {
            return dayActiveCrons;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERCRONSTIMETABLE, start, stop);
            Set<String> activeCronsSet = new HashSet<>();
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        activeCronsSet.add(uid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        activeCronsSet.add(uid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        activeCronsSet.add(uid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    activeCronsSet.add(uid);
                }
            }
            return String.valueOf(activeCronsSet.size());
        }
    }

    // 获取当天新增消费用户数
    public static String getDayNewCrons(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayNewCrons = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERCRONSSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERCRONSSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERCRONSSTATBYCHANNELTABLE[3]);
        if (dayNewCrons != null) {
            return dayNewCrons;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.NEWUSERCRONSTIMETABLE, start, stop);
            Set<String> newCronsSet = new HashSet<>();
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        newCronsSet.add(uid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        newCronsSet.add(uid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        newCronsSet.add(uid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    newCronsSet.add(uid);
                }
            }
            return String.valueOf(newCronsSet.size());
        }
    }

    // 获取当天活跃消费率===活跃消费用户数/活跃用户数
    public static String getDayACR(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayACR = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERCRONSSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERCRONSSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERCRONSSTATBYCHANNELTABLE[4]);
        if (dayACR != null) {
            return dayACR;
        } else {
            String activeCrons = getDayActiveCrons(day, channel);
            String activeNumber = DayUserQuery.getDayActiveUserNumber(day, channel);

            double activeCronsDou = Double.valueOf(activeCrons); // 分子
            double activeNumberDou = Double.valueOf(activeNumber); // 分母

            if (activeNumberDou == 0d) {
                return "0.00";
            }
            String ACR = MyUtils.getYuan((activeCronsDou / activeNumberDou));
            return ACR;
        }
    }

    // 获取当天新增消费率===新增消费用户数/新增用户数
    public static String getDayNCR(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayNCR = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERCRONSSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERCRONSSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERCRONSSTATBYCHANNELTABLE[5]);
        if (dayNCR != null) {
            return dayNCR;
        } else {
            String newCrons = getDayNewCrons(day, channel);
            String newNumber = DayUserQuery.getDayNewUserNumber(day, channel);

            double newCronsDou = Double.valueOf(newCrons); // 分子
            double newNumberDou = Double.valueOf(newNumber); // 分母

            if (newNumberDou == 0d) {
                return "0.00";
            }
            String NCR = MyUtils.getYuan((newCronsDou / newNumberDou));
            return NCR;
        }
    }

    // 获取当天活跃人均消费===活跃消费额/活跃用户数
    public static String getDayAPCC(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayAPCC = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERCRONSSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERCRONSSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERCRONSSTATBYCHANNELTABLE[6]);
        if (dayAPCC != null) {
            return dayAPCC;
        } else {
            String acm = getDayACM(day, channel);
            String activeNumber = DayUserQuery.getDayActiveUserNumber(day, channel);

            double acmDou = Double.valueOf(acm); // 分子
            double activeNumberDou = Double.valueOf(activeNumber); // 分母

            if (activeNumberDou == 0d) {
                return "0.00";
            }
            String APCC = MyUtils.getYuan((acmDou / activeNumberDou));
            return APCC;
        }
    }

    // 获取当天新增人均消费===新增消费额/新增用户数
    public static String getDayNPCC(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayNPCC = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERCRONSSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERCRONSSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERCRONSSTATBYCHANNELTABLE[7]);
        if (dayNPCC != null) {
            return dayNPCC;
        } else {
            String ncm = getDayNCM(day, channel);
            String newNumber = DayUserQuery.getDayNewUserNumber(day, channel);

            double ncmDou = Double.valueOf(ncm); // 分子
            double newNumberDou = Double.valueOf(newNumber); // 分母

            if (newNumberDou == 0d) {
                return "0.00";
            }
            String NPCC = MyUtils.getYuan((ncmDou / newNumberDou));
            return NPCC;
        }
    }

    /**
     * ============================测试============================
     */
    @Test
    public static void main(String[] args) {
        System.out.println(getDayACM("20180810", "app"));
        System.out.println(getDayNCM("20180810", "app"));
        System.out.println(getDayActiveCrons("20180810", "app"));
        System.out.println(getDayNewCrons("20180810", "app"));
        System.out.println(getDayACR("20180810", "app"));
        System.out.println(getDayNCR("20180810", "app"));
        System.out.println(getDayAPCC("20180810", "app"));
        System.out.println(getDayNPCC("20180810", "app"));
    }
}

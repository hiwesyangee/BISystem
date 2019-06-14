package biquery.payquery;

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

public class PayQuery {
    // 获取当天活跃充值额
    public static String getDayAPM(String day, String channel) {
        // 先直接查询表，看是否有新增数据
        String rowkey = day + "===" + channel;
        String dayAPM = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERPAYSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERPAYSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERPAYSTATBYCHANNELTABLE[0]);
        if (dayAPM != null) {
            return dayAPM;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERPAYTIMETABLE, start, stop);
            double apm = 0.00;
            for (Result result : scanner) {
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfUSERPAYTIMETABLE[2])));
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        apm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        apm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        apm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    apm += Double.valueOf(amount);
                }
            }
            String APM = MyUtils.getYuan(apm);
            return APM;
        }
    }

    // 获取当天新增充值额
    public static String getDayNPM(String day, String channel) {
        // 先直接查询表，看是否有新增数据
        String rowkey = day + "===" + channel;
        String dayNPM = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERPAYSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERPAYSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERPAYSTATBYCHANNELTABLE[1]);
        if (dayNPM != null) {
            return dayNPM;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.NEWUSERPAYTIMETABLE, start, stop);
            double npm = 0.00;
            for (Result result : scanner) {
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                String amount = Bytes.toString(result.getValue(Bytes.toBytes(DataProcessProperties2.cfsOfNEWUSERPAYTIMETABLE[0]), Bytes.toBytes(DataProcessProperties2.columnsOfNEWUSERPAYTIMETABLE[2])));
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        npm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        npm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        npm += Double.valueOf(amount);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    npm += Double.valueOf(amount);
                }
            }
            String APM = MyUtils.getYuan(npm);
            return APM;
        }
    }

    // 获取当天活跃充值用户数
    public static String getDayActivePay(String day, String channel) {
        // 先直接查询表，看是否有新增数据
        String rowkey = day + "===" + channel;
        String dayActivePay = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERPAYSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERPAYSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERPAYSTATBYCHANNELTABLE[2]);
        if (dayActivePay != null) {
            return dayActivePay;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.USERPAYTIMETABLE, start, stop);
            Set<String> activePaySet = new HashSet<>();
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        activePaySet.add(uid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        activePaySet.add(uid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        activePaySet.add(uid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    activePaySet.add(uid);
                }
            }
            return String.valueOf(activePaySet.size());
        }
    }

    // 获取当天新增充值用户数
    public static String getDayNewPay(String day, String channel) {
        // 先直接查询表，看是否有新增数据
        String rowkey = day + "===" + channel;
        String dayNewPay = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERPAYSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERPAYSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERPAYSTATBYCHANNELTABLE[3]);
        if (dayNewPay != null) {
            return dayNewPay;
        } else {
            String start = day + "000000===0";
            String stop = day + "999999===999999";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties2.NEWUSERPAYTIMETABLE, start, stop);
            Set<String> activePaySet = new HashSet<>();
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        activePaySet.add(uid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        activePaySet.add(uid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        activePaySet.add(uid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    activePaySet.add(uid);
                }
            }
            return String.valueOf(activePaySet.size());
        }
    }

    // 获取当天活跃充值率===活跃充值人数/活跃用户数
    public static String getDayAPR(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayAPR = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERPAYSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERPAYSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERPAYSTATBYCHANNELTABLE[4]);
        if (dayAPR != null) {
            return dayAPR;
        } else {
            String activePay = getDayActivePay(day, channel);
            String activeNumber = DayUserQuery.getDayActiveUserNumber(day, channel);

            double activePayDou = Double.valueOf(activePay); // 分子
            double activeNumberDou = Double.valueOf(activeNumber); // 分母

            if (activeNumberDou == 0d) {
                return "0.00";
            }
            String APR = MyUtils.getYuan((activePayDou / activeNumberDou));
            return APR;
        }
    }

    // 获取当天新增充值率===新增充值人数/新增用户数
    public static String getDayNPR(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayNPR = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERPAYSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERPAYSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERPAYSTATBYCHANNELTABLE[5]);
        if (dayNPR != null) {
            return dayNPR;
        } else {
            String newPay = getDayNewPay(day, channel);
            String newNumber = DayUserQuery.getDayNewUserNumber(day, channel);

            double newPayDou = Double.valueOf(newPay); // 分子
            double newNumberDou = Double.valueOf(newNumber); // 分母
            if (newNumberDou == 0d) {
                return "0.00";
            }
            String APR = MyUtils.getYuan((newPayDou / newNumberDou));
            return APR;
        }
    }

    // 获取当天活跃人均充值===活跃充值额/活跃用户数
    public static String getDayAPPC(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayAPPC = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERPAYSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERPAYSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERPAYSTATBYCHANNELTABLE[6]);
        if (dayAPPC != null) {
            return dayAPPC;
        } else {
            String APM = getDayAPM(day, channel);
            String activeNumber = DayUserQuery.getDayActiveUserNumber(day, channel);

            double APMDou = Double.valueOf(APM); // 分子
            double activeNumberDou = Double.valueOf(activeNumber); // 分母

            if (activeNumberDou == 0d) {
                return "0.00";
            }
            String APPC = MyUtils.getYuan((APMDou / activeNumberDou));
            return APPC;
        }
    }

    // 获取当天新增人均充值===新增充值额/新增用户数
    public static String getDayNPPC(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayNPPC = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERPAYSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERPAYSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERPAYSTATBYCHANNELTABLE[7]);
        if (dayNPPC != null) {
            return dayNPPC;
        } else {
            String NPM = getDayNPM(day, channel);
            String newNumber = DayUserQuery.getDayNewUserNumber(day, channel);

            double NPMDou = Double.valueOf(NPM); // 分子
            double newNumberDou = Double.valueOf(newNumber); // 分母

            if (newNumberDou == 0d) {
                return "0.00";
            }
            String APPC = MyUtils.getYuan((NPMDou / newNumberDou));
            return APPC;
        }
    }

    // 获取当天活跃ARPU===活跃充值额/活跃用户人数
    public static String getDayaARPU(String day, String channel) {
        return getDayAPPC(day, channel);
    }

    // 获取当天活跃ARPPU===活跃充值额/活跃充值人数
    public static String getDayaARPPU(String day, String channel) {
        String rowkey = day + "===" + channel;
        String dayaARPPU = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERPAYSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERPAYSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERPAYSTATBYCHANNELTABLE[9]);
        if (dayaARPPU != null) {
            return dayaARPPU;
        } else {
            String APM = getDayAPM(day, channel);
            String activePay = getDayActivePay(day, channel);

            double APMDou = Double.valueOf(APM); // 分子
            double activePayDou = Double.valueOf(activePay); // 分母

            if (activePayDou == 0d) {
                return "0.00";
            }
            String aARPPU = MyUtils.getYuan((APMDou / activePayDou));
            return aARPPU;
        }
    }

    // 活跃当天新增ARPU===新增充值额/新增用户数
    public static String getDaynARPU(String day, String channel) {
        return getDayNPPC(day, channel);
    }

    // 获取当天新增ARPPU===新增充值额/新增充值人数
    public static String getDaynARPPU(String day, String channel) {
        String rowkey = day + "===" + channel;
        String daynARPPU = JavaHBaseUtils.getValue(DataProcessProperties2.DAYUSERPAYSTATBYCHANNELTABLE, rowkey, DataProcessProperties2.cfsOfDAYUSERPAYSTATBYCHANNELTABLE[0], DataProcessProperties2.columnsOfDAYUSERPAYSTATBYCHANNELTABLE[11]);
        if (daynARPPU != null) {
            return daynARPPU;
        } else {
            String NPM = getDayNPM(day, channel);
            String newPay = getDayNewPay(day, channel);

            double NPMDou = Double.valueOf(NPM); // 分子
            double newPayDou = Double.valueOf(newPay); // 分母

            if (newPayDou == 0d) {
                return "0.00";
            }
            String aARPPU = MyUtils.getYuan((NPMDou / newPayDou));
            return aARPPU;
        }
    }

    /**
     * ============================测试============================
     */
    @Test
    public static void main(String[] args) {
        System.out.println(getDayAPM("20180810", "all"));
        System.out.println(getDayNPM("20180810", "all"));
        System.out.println(getDayActivePay("20180810", "all"));
        System.out.println(getDayNewPay("20180810", "all"));
        System.out.println(getDayAPR("20180810", "all"));
        System.out.println(getDayNPR("20180810", "all"));
        System.out.println(getDayAPPC("20180810", "all"));
        System.out.println(getDayNPPC("20180810", "all"));
        System.out.println(getDayaARPU("20180810", "all"));
        System.out.println(getDayaARPPU("20180810", "all"));
        System.out.println(getDaynARPU("20180810", "all"));
        System.out.println(getDaynARPPU("20180810", "all"));
    }
}

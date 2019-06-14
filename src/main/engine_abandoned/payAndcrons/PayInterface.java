package engine_abandoned.payAndcrons;

import properties.DataProcessProperties;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取所有用户充值相关数据
 */
public class PayInterface {
    /**
     * 获取一天内充值相关数据
     *
     * @param time1
     * @return
     */
    public static List<PayByChannel> getDayPay(String time1, String channel) {
        return getManyDayPay(time1, time1, channel);
    }

    public static List<Pay> getDayPay(String time1) {
        return getManyDayPay(time1, time1);
    }

    /**
     * 按渠道，获取自定义时期内的所有用户充值相关数据
     * apm;  // 活跃充值额
     * npm;  // 新增充值额
     * activePay;    // 活跃充值用户数
     * newPay;   // 新增充值用户数
     * apr;   // 活跃充值率
     * npr;   // 新增充值率
     * appc;   // 活跃人均充值
     * nppc;   // 新增人均充值
     * aARPU;   // 活跃ARPU
     * aARPPU;   // 活跃ARPPU
     * nARPU;   // 新增ARPU
     * nARPPU;   // 新增ARPPU
     *
     * @param time1
     * @param time2
     * @return
     */
    public static List<PayByChannel> getManyDayPay(String time1, String time2, String channel) {
        List<PayByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        for (String day : days) {
            /** 金额相关 */
            String rowkey1 = day + "===" + channel;
            String apm = "0.00"; // 活跃充值额
            String nowAPM = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATBYCHANNELTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE[0]);
            if (nowAPM != null) {
                apm = nowAPM;
            }
            String npm = "0.00";  // 新增充值额
            String nowNPM = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATBYCHANNELTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE[1]);
            if (nowNPM != null) {
                npm = nowNPM;
            }
            /** 人数相关 */
            String activePay = "0";    // 活跃充值用户数
            String nowActivePay = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATBYCHANNELTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE[1]);
            if (nowActivePay != null) {
                activePay = nowActivePay;
            }
            String newPay = "0";    // 新增充值用户数
            String nowNewPay = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATBYCHANNELTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE[3]);
            if (nowNewPay != null) {
                newPay = nowNewPay;
            }
            // 完成统计活跃充值额、新增充值额、活跃充值人数、新增充值人数

            /** 统计相关,从这，需要遍历整个数据库，才能获取到已经有的用户数据 */
            String apr;   // 活跃充值率——————活跃充值用户数/活跃用户人数
            String nowTotalActiveNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[1]);
            double apnDouble = Double.valueOf(activePay);
            double aprDouble = 0d;
            double alnDouble = 0d;
            if (nowTotalActiveNumber != null) {
                alnDouble = Double.valueOf(nowTotalActiveNumber);  // "设备和用户"中的活跃用户==总登录用户
                if (alnDouble != 0d) {
                    aprDouble = (apnDouble / alnDouble) * 100;
                }
            }
            apr = MyUtils.getYuan(aprDouble);

            String npr;   // 新增充值率——————新增充值用户数/新增用户人数
            double newPayDouble = Double.valueOf(newPay);
            String nowNewLoginNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[3]);
            double nprDouble = 0d;
            double nlnDouble = 0d;
            if (nowNewLoginNumber != null) {
                nlnDouble = Double.valueOf(nowNewLoginNumber);  // "设备和用户"中的活跃用户==总登录用户
                if (nlnDouble != 0d) {
                    nprDouble = (newPayDouble / nlnDouble) * 100;
                }
            }
            npr = MyUtils.getYuan(nprDouble);

            String appc;   // 活跃人均充值——————活跃充值额/活跃用户人数
            double appcDouble = 0d;
            if (alnDouble != 0d) {  // 活跃用户数不等于0
                appcDouble = (Double.valueOf(apm) / alnDouble);
            }
            appc = MyUtils.getYuan(appcDouble);

            String nppc;   // 新增人均充值——————新增充值额/新增用户人数
            double nppcDouble = 0d;
            if (nlnDouble != 0d) {
                nppcDouble = (Double.valueOf(npm) / nlnDouble);
            }
            nppc = MyUtils.getYuan(nppcDouble);

            /** ARPU和ARPPU相关 */
            String aARPU;   // 活跃ARPU——————活跃充值额/活跃用户人数
            double aARPUDouble = 0d;
            if (alnDouble != 0d) {
                aARPUDouble = (Double.valueOf(apm) / alnDouble);
            }
            aARPU = MyUtils.getYuan(aARPUDouble);
            String aARPPU;   // 活跃ARPPU——————活跃充值额/总充值人数
            double aARPPUDouble = 0d;
            if (Double.valueOf(activePay) != 0d) {
                aARPPUDouble = (Double.valueOf(apm) / Double.valueOf(activePay));
            }
            aARPPU = MyUtils.getYuan(aARPPUDouble);
            String nARPU;   // 新增ARPU——————新增充值额/新增用户数
            double nARPUDouble = 0d;
            if (nlnDouble != 0d) {
                nARPUDouble = (Double.valueOf(npm) / nlnDouble);
            }
            nARPU = MyUtils.getYuan(nARPUDouble);
            String nARPPU;   // 新增ARPPU——————新增充值额/新增充值人数
            double nARPPUDouble = 0d;
            if (Double.valueOf(newPay) != 0d) {
                nARPPUDouble = (Double.valueOf(npm) / Double.valueOf(newPay));
            }
            nARPPU = MyUtils.getYuan(nARPPUDouble);
            list.add(new PayByChannel(day, channel, apm, npm, activePay, newPay, apr, npr, appc, nppc, aARPU, aARPPU, nARPU, nARPPU));
        }
        return list;
    }

    /**
     * 非渠道的总共统计结果
     *
     * @param time1
     * @param time2
     * @return
     */
    public static List<Pay> getManyDayPay(String time1, String time2) {
        List<Pay> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        for (String day : days) {
            /** 金额相关 */
            String rowkey1 = day;
            String apm = "0"; // 活跃充值额
            String nowAPM = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATTABLE[0]);
            if (nowAPM != null) {
                apm = nowAPM;
            }
            String npm = "0";  // 新增充值额
            String nowNPM = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATTABLE[1]);
            if (nowNPM != null) {
                npm = nowNPM;
            }
            /** 人数相关 */
            String activePay = "0";    // 活跃充值用户数
            String nowActivePay = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATTABLE[1]);
            if (nowActivePay != null) {
                activePay = nowActivePay;
            }
            String newPay = "0";    // 新增充值用户数
            String nowNewPay = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATTABLE[3]);
            if (nowNewPay != null) {
                newPay = nowNewPay;
            }

            /** 统计相关,从这，需要遍历整个数据库，才能获取到已经有的用户数据 */
            String apr;   // 活跃充值率——————活跃充值用户数/活跃用户人数
            String nowTotalActiveNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, rowkey1, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[1]);
            double apnDouble = Double.valueOf(activePay);
            double aprDouble = 0d;
            double alnDouble = 0d;
            if (nowTotalActiveNumber != null) {
                alnDouble = Double.valueOf(nowTotalActiveNumber);  // "设备和用户"中的活跃用户==总登录用户
                if (alnDouble != 0d) {
                    aprDouble = (apnDouble / alnDouble) * 100;
                }
            }
            apr = MyUtils.getYuan(aprDouble);

            String npr;   // 新增充值率——————新增充值用户数/新增用户人数
            double newPayDouble = Double.valueOf(newPay);
            String nowNewLoginNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, rowkey1, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[3]);
            double nprDouble = 0d;
            double nlnDouble = 0d;
            if (nowNewLoginNumber != null) {
                nlnDouble = Double.valueOf(nowNewLoginNumber);  // "设备和用户"中的活跃用户==总登录用户
                if (nlnDouble != 0d) {
                    nprDouble = (newPayDouble / nlnDouble) * 100;
                }
            }
            npr = MyUtils.getYuan(nprDouble);

            String appc;   // 活跃人均充值——————活跃充值额/活跃用户人数
            double appcDouble = 0d;
            if (alnDouble != 0d) {  // 活跃用户数不等于0
                appcDouble = (Double.valueOf(apm) / alnDouble);
            }
            appc = MyUtils.getYuan(appcDouble);

            String nppc;   // 新增人均充值——————新增充值额/新增用户人数
            double nppcDouble = 0d;
            if (nlnDouble != 0d) {
                nppcDouble = (Double.valueOf(npm) / nlnDouble);
            }
            nppc = MyUtils.getYuan(nppcDouble);

            /** ARPU和ARPPU相关 */
            String aARPU;   // 活跃ARPU——————活跃充值额/活跃用户人数
            double aARPUDouble = 0d;
            if (alnDouble != 0d) {
                aARPUDouble = (Double.valueOf(apm) / alnDouble);
            }
            aARPU = MyUtils.getYuan(aARPUDouble);
            String aARPPU;   // 活跃ARPPU——————活跃充值额/总充值人数
            double aARPPUDouble = 0d;
            if (Double.valueOf(activePay) != 0d) {
                aARPPUDouble = (Double.valueOf(apm) / Double.valueOf(activePay));
            }
            aARPPU = MyUtils.getYuan(aARPPUDouble);
            String nARPU;   // 新增ARPU——————新增充值额/新增用户数
            double nARPUDouble = 0d;
            if (nlnDouble != 0d) {
                nARPUDouble = (Double.valueOf(npm) / nlnDouble);
            }
            nARPU = MyUtils.getYuan(nARPUDouble);
            String nARPPU;   // 新增ARPPU——————新增充值额/新增充值人数
            double nARPPUDouble = 0d;
            if (Double.valueOf(newPay) != 0d) {
                nARPPUDouble = (Double.valueOf(npm) / Double.valueOf(newPay));
            }
            nARPPU = MyUtils.getYuan(nARPPUDouble);
            list.add(new Pay(day, apm, npm, activePay, newPay, apr, npr, appc, nppc, aARPU, aARPPU, nARPU, nARPPU));
        }
        return list;
    }

    /**
     * private String times; // 时间
     * apm;  // 活跃充值额
     * npm;  // 新增充值额
     * activePay;    // 活跃充值用户数
     * newPay;   // 新增充值用户数
     * apr;   // 活跃充值率
     * npr;   // 新增充值率
     * appc;   // 活跃人均充值
     * nppc;   // 新增人均充值
     * aARPU;   // 活跃ARPU
     * aARPPU;   // 活跃ARPPU
     * nARPU;   // 新增ARPU
     * nARPPU;   // 新增ARPPU
     */
    public static void main(String[] args) {
        List<Pay> manyDayPay = getManyDayPay("20190115", "20190115");
        for (Pay p : manyDayPay) {
            System.out.println(p.getTimes() + "|-|-|" +
                    p.getApm() + "," + p.getNpm() + "," + p.getActivePay() + "," + p.getNewPay() + "," + p.getApr() + "," + p.getNpr() + "," + p.getAppc() + "," + p.getNppc() + "," +
                    p.getaARPU() + "," + p.getaARPPU() + "," + p.getnARPU() + "," + p.getnARPPU());
        }

        System.out.println("_________________________");
        List<PayByChannel> ios = getManyDayPay("20190115", "20190115", "android");
        for (PayByChannel p : ios) {
            System.out.println(p.getTimes() + "|-|-|" + p.getChannel() + "," +
                    p.getApm() + "," + p.getNpm() + "," + p.getActivePay() + "," + p.getNewPay() + "," + p.getApr() + "," + p.getNpr() + "," + p.getAppc() + "," + p.getNppc() + "," +
                    p.getaARPU() + "," + p.getaARPPU() + "," + p.getnARPU() + "," + p.getnARPPU());
        }
    }
}

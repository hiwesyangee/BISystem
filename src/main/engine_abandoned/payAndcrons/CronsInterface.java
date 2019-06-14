package engine_abandoned.payAndcrons;

import properties.DataProcessProperties;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class CronsInterface {
    /**
     * 获取一天内消费相关数据
     *
     * @param time1
     * @return
     */
    public static List<CronsByChannel> getDayCrons(String time1, String channel) {
        return getManyDayCrons(time1, time1, channel);
    }

    public static List<Crons> getDayCrons(String time1) {
        return getManyDayCrons(time1, time1);
    }

    /**
     * 按渠道，获取自定义时期内的所有用户消费相关数据
     * apm;  // 活跃消费额
     * npm;  // 新增消费额
     * activePay;    // 活跃消费用户数
     * newPay;   // 新增消费用户数
     * apr;   // 活跃消费率
     * npr;   // 新增消费率
     * appc;   // 活跃人均消费
     * nppc;   // 新增人均消费
     * aARPU;   // 活跃ARPU
     * aARPPU;   // 活跃ARPPU
     * nARPU;   // 新增ARPU
     * nARPPU;   // 新增ARPPU
     *
     * @param time1
     * @param time2
     * @return
     */
    public static List<CronsByChannel> getManyDayCrons(String time1, String time2, String channel) {
        List<CronsByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        for (String day : days) {
            /** 金额相关 */
            String rowkey1 = day + "===" + channel;
            String acm = "0"; // 活跃消费额
            String nowACM = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATBYCHANNELTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE[0]);
            if (nowACM != null) {
                acm = nowACM;
            }
            String ncm = "0";  // 新增消费额
            String nowNCM = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATBYCHANNELTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE[1]);
            if (nowNCM != null) {
                ncm = nowNCM;
            }
            /** 人数相关 */
            String activeCrons = "0";    // 活跃消费用户数
            String nowActiveCrons = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATBYCHANNELTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE[1]);
            if (nowActiveCrons != null) {
                activeCrons = nowActiveCrons;
            }
            String newCrons = "0";    // 新增消费用户数
            String nowNewCrons = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATBYCHANNELTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE[3]);
            if (nowNewCrons != null) {
                newCrons = nowNewCrons;
            }

            /** 统计相关,从这，需要遍历整个数据库，才能获取到已经有的用户数据 */
            String acr;   // 活跃消费率——————活跃消费用户数/活跃用户人数
            String nowTotalActiveNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[1]);
            double acnDouble = Double.valueOf(activeCrons);
            double acrDouble = 0d;
            double alnDouble = 0d;
            if (nowTotalActiveNumber != null) {
                alnDouble = Double.valueOf(nowTotalActiveNumber);  // "设备和用户"中的活跃用户==总登录用户
                if (alnDouble != 0d) {
                    acrDouble = (acnDouble / alnDouble) * 100;
                }
            }
            acr = MyUtils.getYuan(acrDouble);

            String ncr;   // 新增消费率——————新增消费用户数/新增用户人数
            double newCronsDouble = Double.valueOf(newCrons);
            String nowNewLoginNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE[3]);
            double ncrDouble = 0d;
            double nlnDouble = 0d;
            if (nowNewLoginNumber != null) {
                nlnDouble = Double.valueOf(nowNewLoginNumber);  // "设备和用户"中的活跃用户==总登录用户
                if (nlnDouble != 0d) {
                    ncrDouble = (newCronsDouble / nlnDouble) * 100;
                }
            }
            ncr = MyUtils.getYuan(ncrDouble);

            String apcc;   // 活跃人均消费——————活跃消费额/活跃用户人数
            double appcDouble = 0d;
            if (alnDouble != 0d) {  // 活跃用户数不等于0
                appcDouble = (Double.valueOf(acm) / alnDouble);
            }
            apcc = MyUtils.getYuan(appcDouble);

            String npcc;   // 新增人均消费——————新增消费额/新增用户人数
            double nppcDouble = 0d;
            if (nlnDouble != 0d) {
                nppcDouble = (Double.valueOf(ncm) / nlnDouble);
            }
            npcc = MyUtils.getYuan(nppcDouble);

            list.add(new CronsByChannel(day, channel, acm, ncm, activeCrons, newCrons, acr, ncr, apcc, npcc));
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
    public static List<Crons> getManyDayCrons(String time1, String time2) {
        List<Crons> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        for (String day : days) {
            /** 金额相关 */
            String rowkey1 = day;
            String acm = "0"; // 活跃消费额
            String nowACM = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATTABLE[0]);
            if (nowACM != null) {
                acm = nowACM;
            }
            String ncm = "0";  // 新增消费额
            String nowNCM = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATTABLE[1]);
            if (nowNCM != null) {
                ncm = nowNCM;
            }
            /** 人数相关 */
            String activeCrons = "0";    // 活跃消费用户数
            String nowActiveCrons = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATTABLE[1]);
            if (nowActiveCrons != null) {
                activeCrons = nowActiveCrons;
            }
            String newCrons = "0";    // 新增消费用户数
            String nowNewCrons = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATTABLE, rowkey1, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATTABLE[3]);
            if (nowNewCrons != null) {
                newCrons = nowNewCrons;
            }

            /** 统计相关,从这，需要遍历整个数据库，才能获取到已经有的用户数据 */
            String acr;   // 活跃消费率——————活跃消费用户数/活跃用户人数
            String nowTotalActiveNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, rowkey1, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[1]);
            double acnDouble = Double.valueOf(activeCrons);
            double acrDouble = 0d;
            double alnDouble = 0d;
            if (nowTotalActiveNumber != null) {
                alnDouble = Double.valueOf(nowTotalActiveNumber);  // "设备和用户"中的活跃用户==总登录用户
                if (alnDouble != 0d) {
                    acrDouble = (acnDouble / alnDouble) * 100;
                }
            }
            acr = MyUtils.getYuan(acrDouble);

            String ncr;   // 新增消费率——————新增消费用户数/新增用户人数
            double newPayDouble = Double.valueOf(newCrons);
            String nowNewLoginNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, rowkey1, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE[3]);
            double ncrDouble = 0d;
            double nlnDouble = 0d;
            if (nowNewLoginNumber != null) {
                nlnDouble = Double.valueOf(nowNewLoginNumber);  // "设备和用户"中的活跃用户==总登录用户
                if (nlnDouble != 0d) {
                    ncrDouble = (newPayDouble / nlnDouble) * 100;
                }
            }
            ncr = MyUtils.getYuan(ncrDouble);

            String apcc;   // 活跃人均消费——————活跃消费额/活跃用户人数
            double apccDouble = 0d;
            if (alnDouble != 0d) {  // 活跃用户数不等于0
                apccDouble = (Double.valueOf(acm) / alnDouble);
            }
            apcc = MyUtils.getYuan(apccDouble);

            String npcc;   // 新增人均消费——————新增消费额/新增用户人数
            double npccDouble = 0d;
            if (nlnDouble != 0d) {
                npccDouble = (Double.valueOf(ncm) / nlnDouble);
            }
            npcc = MyUtils.getYuan(npccDouble);

            list.add(new Crons(day, acm, ncm, activeCrons, newCrons, acr, ncr, apcc, npcc));
        }
        return list;
    }

    /**
     * private String times; // 时间
     * apm;  // 活跃消费额
     * npm;  // 新增消费额
     * activePay;    // 活跃消费用户数
     * newPay;   // 新增消费用户数
     * apr;   // 活跃消费率
     * npr;   // 新增消费率
     * appc;   // 活跃人均消费
     * nppc;   // 新增人均消费
     * aARPU;   // 活跃ARPU
     * aARPPU;   // 活跃ARPPU
     * nARPU;   // 新增ARPU
     * nARPPU;   // 新增ARPPU
     */
    public static void main(String[] args) {
        List<Crons> manyDayPay = getManyDayCrons("20190115", "20190115");
        for (Crons p : manyDayPay) {
            System.out.println(p.getTimes() + "|-|-|" +
                    p.getAcm() + "," + p.getNcm() + "," + p.getActiveCrons() + "," + p.getNewCrons() + "," + p.getAcr() + "," + p.getNcr() + "," + p.getApcc() + "," + p.getNpcc());
        }

        System.out.println("_________________________");
        List<CronsByChannel> ios = getManyDayCrons("20190115", "20190115", "android");
        for (CronsByChannel p : ios) {
            System.out.println(p.getTimes() + "|-|-|" + p.getChannel() + "," +
                    p.getAcm() + "," + p.getNcm() + "," + p.getActiveCrons() + "," + p.getNewCrons() + "," + p.getAcr() + "," + p.getNcr() + "," + p.getApcc() + "," + p.getNpcc());
        }
    }
}


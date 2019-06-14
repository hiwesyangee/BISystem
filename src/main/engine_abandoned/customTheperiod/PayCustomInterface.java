package engine_abandoned.customTheperiod;

import properties.DataProcessProperties;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义周期充值接口
 */
public class PayCustomInterface {
    /**
     * 获取一天内充值相关整体数据
     *
     * @param time1
     * @return
     */
    public static List<PayCustomByChannel> getDayPayCustom(String time1, String channel) {
        return getManyDayPayCustom(time1, time1, channel);
    }

    public static List<PayCustom> getDayPayCustom(String time1) {
        return getManyDayPayCustom(time1, time1);
    }

    /**
     * 获取自定义周期内充值相关整体数据
     *
     * @param time1
     * @param time2
     * @param channel
     * @return
     */
    public static List<PayCustomByChannel> getManyDayPayCustom(String time1, String time2, String channel) {
        List<PayCustomByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        double apmDou = 0.00; // 活跃充值额
        double npmDou = 0.00; // 新增充值额
        int activePayInt = 0; // 活跃充值人数
        int newPayInt = 0; // 新增充值人数
        int activeNumber = 0; // 活跃用户数
        int newNumber = 0; // 新增用户数

        for (String day : days) {
            String rowkey = day + "===" + channel;
            // 查询活跃充值额
            double dailyAPM = 0d;
            String dailyAPMNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE[0]);
            if (dailyAPMNumber != null) {
                dailyAPM = Double.valueOf(dailyAPMNumber);
            }
            apmDou += dailyAPM;
            // 查询新增充值额
            double dailyNPM = 0d;
            String dailyNPMNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE[1]);
            if (dailyNPMNumber != null) {
                dailyNPM = Double.valueOf(dailyNPMNumber);
            }
            npmDou += dailyNPM;
            // 查询活跃充值人数
            int dailyActivePay = 0;
            String dailyActivePayNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE[1]);
            if (dailyActivePayNumber != null) {
                dailyActivePay = Integer.valueOf(dailyActivePayNumber);
            }
            activePayInt += dailyActivePay;
            // 查询新增充值人数
            int dailyNewPay = 0;
            String dailyNewPayNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE[3]);
            if (dailyNewPayNumber != null) {
                dailyNewPay = Integer.valueOf(dailyNewPayNumber);
            }
            newPayInt += dailyNewPay;
            // 查询活跃用户数
            int dailyActiveNumber = 0;
            String dailyActiveStr = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATTABLE[1]);
            if (dailyActiveStr != null) {
                dailyActiveNumber = Integer.valueOf(dailyActiveStr);
            }
            activeNumber += dailyActiveNumber;
            // 查询新增用户数
            int dailyNewNumber = 0;
            String dailyNewPayStr = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERLOGINSTATTABLE[3]);
            if (dailyNewPayStr != null) {
                dailyNewNumber = Integer.valueOf(dailyNewPayStr);
            }
            newNumber += dailyNewNumber;
        }
        double aprDou = 0.00; // 活跃充值率————活跃充值人数/活跃用户数
        double nprDou = 0.00; // 新增充值率————新增充值人数/新增用户数
        double appcDou = 0.00; // 活跃人均充值————活跃充值额/活跃用户数
        double nppcDou = 0.00; // 新增人均充值————新增充值额/新增用户数
        double aARPUDou = 0.00; // 活跃ARPU————活跃充值额/活跃用户数
        double aARPPUDou = 0.00; // 活跃ARPPU————活跃充值额/活跃充值人数
        double nARPUDou = 0.00; // 新增ARPU————新增充值额/新增用户数
        double nARPPUDou = 0.00; // 新增ARPPU————新增充值额/新增充值人数

        try {
            if (activeNumber != 0) {  // 活跃用户
                aprDou = Double.valueOf(activePayInt) / Double.valueOf(activeNumber); // 活跃充值率————活跃充值人数/活跃用户数
            }
            if (newNumber != 0) { // 新增用户
                nprDou = Double.valueOf(newPayInt) / Double.valueOf(newNumber); // 新增充值率————新增充值人数/新增用户数
            }
            if (activeNumber != 0) { // 活跃用户
                appcDou = apmDou / Double.valueOf(activeNumber); // 活跃人均充值————活跃充值额/活跃用户数
            }
            if (newNumber != 0) { // 新增用户
                nppcDou = npmDou / Double.valueOf(newNumber); // 新增人均充值————新增充值额/新增用户数
            }
            if (activeNumber != 0) { // 活跃用户
                aARPUDou = apmDou / Double.valueOf(activeNumber); // 活跃ARPU————活跃充值额/活跃用户数
            }
            if (activePayInt != 0) { // 活跃充值用户
                aARPPUDou = apmDou / Double.valueOf(activePayInt); // 活跃ARPPU————活跃充值额/活跃充值人数
            }
            if (newNumber != 0) { // 新增用户
                nARPUDou = npmDou / Double.valueOf(newNumber); // 新增ARPU————新增充值额/新增用户数
            }
            if (newPayInt != 0) {
                nARPPUDou = npmDou / Double.valueOf(newPayInt); // 新增ARPPU————新增充值额/新增充值人数
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String apm = MyUtils.getYuan(apmDou);
        String npm = MyUtils.getYuan(npmDou);
        String activePay = String.valueOf(activePayInt);
        String newPay = String.valueOf(newPayInt);
        String apr = MyUtils.getYuan(aprDou);
        String npr = MyUtils.getYuan(nprDou);
        String appc = MyUtils.getYuan(appcDou);
        String nppc = MyUtils.getYuan(nppcDou);
        String aARPU = MyUtils.getYuan(aARPUDou);
        String aARPPU = MyUtils.getYuan(aARPPUDou);
        String nARPU = MyUtils.getYuan(nARPUDou);
        String nARPPU = MyUtils.getYuan(nARPPUDou);
        list.add(new PayCustomByChannel(channel, apm, npm, activePay, newPay, apr, npr, appc, nppc, aARPU, aARPPU, nARPU, nARPPU));
        return list;
    }

    // 自定义充值相关数据总计
    public static List<PayCustom> getManyDayPayCustom(String time1, String time2) {
        List<PayCustom> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        double apmDou = 0.00; // 活跃充值额
        double npmDou = 0.00; // 新增充值额
        int activePayInt = 0; // 活跃充值人数
        int newPayInt = 0; // 新增充值人数
        int activeNumber = 0; // 活跃用户数
        int newNumber = 0; // 新增用户数

        for (String day : days) {
            String rowkey = day;
            // 查询活跃充值额
            double dailyAPM = 0d;
            String dailyAPMNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATTABLE[0]);
            if (dailyAPMNumber != null) {
                dailyAPM = Double.valueOf(dailyAPMNumber);
            }
            apmDou += dailyAPM;
            // 查询新增充值额
            double dailyNPM = 0d;
            String dailyNPMNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATTABLE[1]);
            if (dailyNPMNumber != null) {
                dailyNPM = Double.valueOf(dailyNPMNumber);
            }
            npmDou += dailyNPM;
            // 查询活跃充值人数
            int dailyActivePay = 0;
            String dailyActivePayNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATTABLE[1]);
            if (dailyActivePayNumber != null) {
                dailyActivePay = Integer.valueOf(dailyActivePayNumber);
            }
            activePayInt += dailyActivePay;
            // 查询新增充值人数
            int dailyNewPay = 0;
            String dailyNewPayNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATTABLE[3]);
            if (dailyNewPayNumber != null) {
                dailyNewPay = Integer.valueOf(dailyNewPayNumber);
            }
            newPayInt += dailyNewPay;
            // 查询活跃用户数
            int dailyActiveNumber = 0;
            String dailyActiveStr = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATTABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATTABLE[1]);
            if (dailyActiveStr != null) {
                dailyActiveNumber = Integer.valueOf(dailyActiveStr);
            }
            activeNumber += dailyActiveNumber;
            // 查询新增用户数
            int dailyNewNumber = 0;
            String dailyNewPayStr = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATTABLE[0], DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATTABLE[3]);
            if (dailyNewPayStr != null) {
                dailyNewNumber = Integer.valueOf(dailyNewPayStr);
            }
            newNumber += dailyNewNumber;
        }
        double aprDou = 0.00; // 活跃充值率————活跃充值人数/活跃用户数
        double nprDou = 0.00; // 新增充值率————新增充值人数/新增用户数
        double appcDou = 0.00; // 活跃人均充值————活跃充值额/活跃用户数
        double nppcDou = 0.00; // 新增人均充值————新增充值额/新增用户数
        double aARPUDou = 0.00; // 活跃ARPU————活跃充值额/活跃用户数
        double aARPPUDou = 0.00; // 活跃ARPPU————活跃充值额/活跃充值人数
        double nARPUDou = 0.00; // 新增ARPU————新增充值额/新增用户数
        double nARPPUDou = 0.00; // 新增ARPPU————新增充值额/新增充值人数

        try {
            if (activeNumber != 0) {  // 活跃用户
                aprDou = Double.valueOf(activePayInt) / Double.valueOf(activeNumber); // 活跃充值率————活跃充值人数/活跃用户数
            }
            if (newNumber != 0) { // 新增用户
                nprDou = Double.valueOf(newPayInt) / Double.valueOf(newNumber); // 新增充值率————新增充值人数/新增用户数
            }
            if (activeNumber != 0) { // 活跃用户
                appcDou = apmDou / Double.valueOf(activeNumber); // 活跃人均充值————活跃充值额/活跃用户数
            }
            if (newNumber != 0) { // 新增用户
                nppcDou = npmDou / Double.valueOf(newNumber); // 新增人均充值————新增充值额/新增用户数
            }
            if (activeNumber != 0) { // 活跃用户
                aARPUDou = apmDou / Double.valueOf(activeNumber); // 活跃ARPU————活跃充值额/活跃用户数
            }
            if (activePayInt != 0) { // 活跃充值用户
                aARPPUDou = apmDou / Double.valueOf(activePayInt); // 活跃ARPPU————活跃充值额/活跃充值人数
            }
            if (newNumber != 0) { // 新增用户
                nARPUDou = npmDou / Double.valueOf(newNumber); // 新增ARPU————新增充值额/新增用户数
            }
            if (newPayInt != 0) {
                nARPPUDou = npmDou / Double.valueOf(newPayInt); // 新增ARPPU————新增充值额/新增充值人数
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String apm = MyUtils.getYuan(apmDou);
        String npm = MyUtils.getYuan(npmDou);
        String activePay = String.valueOf(activePayInt);
        String newPay = String.valueOf(newPayInt);
        String apr = MyUtils.getYuan(aprDou);
        String npr = MyUtils.getYuan(nprDou);
        String appc = MyUtils.getYuan(appcDou);
        String nppc = MyUtils.getYuan(nppcDou);
        String aARPU = MyUtils.getYuan(aARPUDou);
        String aARPPU = MyUtils.getYuan(aARPPUDou);
        String nARPU = MyUtils.getYuan(nARPUDou);
        String nARPPU = MyUtils.getYuan(nARPPUDou);
        list.add(new PayCustom(apm, npm, activePay, newPay, apr, npr, appc, nppc, aARPU, aARPPU, nARPU, nARPPU));
        return list;
    }

    public static void main(String[] args) {
        List<PayCustomByChannel> manyDayPayCustom = getManyDayPayCustom("20190115", "20190115", "ios");
        for (PayCustomByChannel pc : manyDayPayCustom) {
            System.out.println(pc.getChannel() + "|-|-|" + pc.getApm() + "," + pc.getNpm() + "," + pc.getActivePay() + "," + pc.getNewPay() + "," + pc.getApr() + "," + pc.getNpr() + "," + pc.getAppc() + "," + pc.getNppc() + "," + pc.getaARPU() + "," + pc.getaARPPU() + "," + pc.getnARPU() + "," + pc.getnARPPU());
        }

        System.out.println("-------------");
        List<PayCustom> manyDayPayCustom2 = getManyDayPayCustom("20190115", "20190115");
        for (PayCustom pc : manyDayPayCustom2) {
            System.out.println(pc.getApm() + "," + pc.getNpm() + "," + pc.getActivePay() + "," + pc.getNewPay() + "," + pc.getApr() + "," + pc.getNpr() + "," + pc.getAppc() + "," + pc.getNppc() + "," + pc.getaARPU() + "," + pc.getaARPPU() + "," + pc.getnARPU() + "," + pc.getnARPPU());
        }
    }
}

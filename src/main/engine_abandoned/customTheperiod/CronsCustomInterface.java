package engine_abandoned.customTheperiod;

import properties.DataProcessProperties;
import utils.JavaHBaseUtils;
import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义周期消费接口
 */
public class CronsCustomInterface {
    /**
     * 获取一天内消费相关数据
     *
     * @param time1
     * @return
     */
    public static List<CronsCustomByChannel> getDayCronsCustom(String time1, String channel) {
        return getManyDayCronsCustom(time1, time1, channel);
    }

    public static List<CronsCustom> getDayCronsCustom(String time1) {
        return getManyDayCronsCustom(time1, time1);
    }

    /**
     * 获取自定义周期内消费相关数据
     *
     * @param time1
     * @param time2
     * @param channel
     * @return
     */
    public static List<CronsCustomByChannel> getManyDayCronsCustom(String time1, String time2, String channel) {
        List<CronsCustomByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        double acmDou = 0.00; // 活跃消费额
        double ncmDou = 0.00; // 新增消费额
        int activeCronsInt = 0; // 活跃消费人数
        int newCronsInt = 0; // 新增消费人数
        int activeNumber = 0; // 活跃用户数
        int newNumber = 0; // 新增用户数

        for (String day : days) {
            String rowkey = day + "===" + channel;
            // 查询活跃消费额
            double dailyACM = 0d;
            String dailyACMNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE[0]);
            if (dailyACMNumber != null) {
                dailyACM = Double.valueOf(dailyACMNumber);
            }
            acmDou += dailyACM;
            // 查询新增消费额
            double dailyNCM = 0d;
            String dailyNCMNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE[1]);
            if (dailyNCMNumber != null) {
                dailyNCM = Double.valueOf(dailyNCMNumber);
            }
            ncmDou += dailyNCM;
            // 查询活跃消费人数
            int dailyActiveCrons = 0;
            String dailyActiveCronsNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE[1]);
            if (dailyActiveCronsNumber != null) {
                dailyActiveCrons = Integer.valueOf(dailyActiveCronsNumber);
            }
            activeCronsInt += dailyActiveCrons;
            // 查询新增消费人数
            int dailyNewPay = 0;
            String dailyNewPayNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE[3]);
            if (dailyNewPayNumber != null) {
                dailyNewPay = Integer.valueOf(dailyNewPayNumber);
            }
            newCronsInt += dailyNewPay;
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
        double acrDou = 0.00; // 活跃消费率————活跃消费人数/活跃用户数
        double ncrDou = 0.00; // 新增消费率————新增消费人数/新增用户数
        double apccDou = 0.00; // 活跃人均消费————活跃消费额/活跃用户数
        double npccDou = 0.00; // 新增人均消费————新增消费额/新增用户数

        try {
            if (activeNumber != 0) {  // 活跃用户
                acrDou = Double.valueOf(activeCronsInt) / Double.valueOf(activeNumber); // 活跃消费率————活跃消费人数/活跃用户数
            }
            if (newNumber != 0) { // 新增用户
                ncrDou = Double.valueOf(newCronsInt) / Double.valueOf(newNumber); // 新增消费率————新增消费人数/新增用户数
            }
            if (activeNumber != 0) { // 活跃用户
                apccDou = acmDou / Double.valueOf(activeNumber); // 活跃人均消费————活跃消费额/活跃用户数
            }
            if (newNumber != 0) { // 新增用户
                npccDou = ncmDou / Double.valueOf(newNumber); // 新增人均消费————新增消费额/新增用户数
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String acm = MyUtils.getYuan(acmDou);
        String ncm = MyUtils.getYuan(ncmDou);
        String activeCrons = String.valueOf(activeCronsInt);
        String newCrons = String.valueOf(newCronsInt);
        String acr = MyUtils.getYuan(acrDou);
        String ncr = MyUtils.getYuan(ncrDou);
        String apcc = MyUtils.getYuan(apccDou);
        String npcc = MyUtils.getYuan(npccDou);
        list.add(new CronsCustomByChannel(channel, acm, ncm, activeCrons, newCrons, acr, ncr, apcc, npcc));
        return list;
    }

    public static List<CronsCustom> getManyDayCronsCustom(String time1, String time2) {
        List<CronsCustom> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        double acmDou = 0.00; // 活跃消费额
        double ncmDou = 0.00; // 新增消费额
        int activeCronsInt = 0; // 活跃消费人数
        int newCronsInt = 0; // 新增消费人数
        int activeNumber = 0; // 活跃用户数
        int newNumber = 0; // 新增用户数
        int totalNumber = 0; // 存储总计统计数据

        for (String day : days) {
            String rowkey = day;
            // 查询活跃消费额
            double dailyACM = 0d;
            String dailyACMNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATTABLE[0]);
            if (dailyACMNumber != null) {
                dailyACM = Double.valueOf(dailyACMNumber);
            }
            acmDou += dailyACM;
            // 查询新增消费额
            double dailyNCM = 0d;
            String dailyNCMNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATTABLE[1]);
            if (dailyNCMNumber != null) {
                dailyNCM = Double.valueOf(dailyNCMNumber);
            }
            ncmDou += dailyNCM;
            // 查询活跃消费人数
            int dailyActiveCrons = 0;
            String dailyActiveCronsNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATTABLE[1]);
            if (dailyActiveCronsNumber != null) {
                dailyActiveCrons = Integer.valueOf(dailyActiveCronsNumber);
            }
            activeCronsInt += dailyActiveCrons;
            // 查询新增消费人数
            int dailyNewPay = 0;
            String dailyNewPayNumber = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATTABLE[0], DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATTABLE[3]);
            if (dailyNewPayNumber != null) {
                dailyNewPay = Integer.valueOf(dailyNewPayNumber);
            }
            newCronsInt += dailyNewPay;
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
        double acrDou = 0.00; // 活跃消费率————活跃消费人数/活跃用户数
        double ncrDou = 0.00; // 新增消费率————新增消费人数/新增用户数
        double apccDou = 0.00; // 活跃人均消费————活跃消费额/活跃用户数
        double npccDou = 0.00; // 新增人均消费————新增消费额/新增用户数

        try {
            if (activeNumber != 0) {  // 活跃用户
                acrDou = Double.valueOf(activeCronsInt) / Double.valueOf(activeNumber); // 活跃消费率————活跃消费人数/活跃用户数
            }
            if (newNumber != 0) { // 新增用户
                ncrDou = Double.valueOf(newCronsInt) / Double.valueOf(newNumber); // 新增消费率————新增消费人数/新增用户数
            }
            if (activeNumber != 0) { // 活跃用户
                apccDou = acmDou / Double.valueOf(activeNumber); // 活跃人均消费————活跃消费额/活跃用户数
            }
            if (newNumber != 0) { // 新增用户
                npccDou = ncmDou / Double.valueOf(newNumber); // 新增人均消费————新增消费额/新增用户数
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String acm = MyUtils.getYuan(acmDou);
        String ncm = MyUtils.getYuan(ncmDou);
        String activeCrons = String.valueOf(activeCronsInt);
        String newCrons = String.valueOf(newCronsInt);
        String acr = MyUtils.getYuan(acrDou * 100d);
        String ncr = MyUtils.getYuan(ncrDou * 100d);
        String apcc = MyUtils.getYuan(apccDou);
        String npcc = MyUtils.getYuan(npccDou);
        list.add(new CronsCustom(acm, ncm, activeCrons, newCrons, acr, ncr, apcc, npcc));
        return list;
    }

    public static void main(String[] args) {
        List<CronsCustomByChannel> manyDayCronsCustom = getManyDayCronsCustom("20181217", "20181225", "ios");
        for (CronsCustomByChannel pc : manyDayCronsCustom) {
            System.out.println(pc.getChannel() + "|-|-|" + pc.getAcm() + "," + pc.getNcm() + "," + pc.getActiveCrons() + "," + pc.getNewCrons() + "," + pc.getAcr() + "," + pc.getNcr() + "," + pc.getApcc() + "," + pc.getNpcc());
        }

        System.out.println("-------------");
        List<CronsCustom> manyDayCronsCustom2 = getManyDayCronsCustom("20181218", "20181218");
        for (CronsCustom pc : manyDayCronsCustom2) {
            System.out.println(pc.getAcm() + "," + pc.getNcm() + "," + pc.getActiveCrons() + "," + pc.getNewCrons() + "," + pc.getAcr() + "," + pc.getNcr() + "," + pc.getApcc() + "," + pc.getNpcc());
        }
    }
}

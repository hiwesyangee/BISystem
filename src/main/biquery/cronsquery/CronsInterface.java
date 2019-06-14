package biquery.cronsquery;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class CronsInterface {
    /**
     * 获取一天内消费相关数据
     */
    public static List<CronsByChannel> getDayCrons(String time1, String channel) {
        return getManyDayCrons(time1, time1, channel);
    }

    public static List<Crons> getDayCrons(String time1) {
        return getManyDayCrons(time1, time1);
    }

    /**
     * 获取自定义时期内的消费相关数据
     */
    public static List<CronsByChannel> getManyDayCrons(String time1, String time2, String channel) {
        List<CronsByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        for (String day : days) {
            String acm = "0.00";  // 活跃消费额
            String ncm = "0.00";  // 新增消费额
            String activeCrons = "0";    // 活跃消费用户数
            String newCrons = "0";   // 新增消费用户数
            String acr = "0.00";   // 活跃消费率
            String ncr = "0.00";   // 新增消费率
            String apcc = "0.00";   // 活跃人均消费
            String npcc = "0.00";   // 新增人均消费
            try {
                acm = CronsQuery.getDayACM(day, channel);  // 活跃消费额
                ncm = CronsQuery.getDayNCM(day, channel);  // 新增消费额
                activeCrons = CronsQuery.getDayActiveCrons(day, channel);    // 活跃消费用户数
                newCrons = CronsQuery.getDayNewCrons(day, channel);   // 新增消费用户数
                acr = CronsQuery.getDayACR(day, channel);   // 活跃消费率
                ncr = CronsQuery.getDayNCR(day, channel);   // 新增消费率
                apcc = CronsQuery.getDayAPCC(day, channel);   // 活跃人均消费
                npcc = CronsQuery.getDayNPCC(day, channel);   // 新增人均消费
            } catch (Exception e) {
                e.printStackTrace();
            }
            list.add(new CronsByChannel(day, channel, acm, ncm, activeCrons, newCrons, acr, ncr, apcc, npcc));
        }
        return list;
    }

    public static List<Crons> getManyDayCrons(String time1, String time2) {
        List<Crons> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        for (String day : days) {
            String acm = "0.00";  // 活跃消费额
            String ncm = "0.00";  // 新增消费额
            String activeCrons = "0";    // 活跃消费用户数
            String newCrons = "0";   // 新增消费用户数
            String acr = "0.00";   // 活跃消费率
            String ncr = "0.00";   // 新增消费率
            String apcc = "0.00";   // 活跃人均消费
            String npcc = "0.00";   // 新增人均消费
            String channel = "all";
            try {
                acm = CronsQuery.getDayACM(day, channel);  // 活跃消费额
                ncm = CronsQuery.getDayNCM(day, channel);  // 新增消费额
                activeCrons = CronsQuery.getDayActiveCrons(day, channel);    // 活跃消费用户数
                newCrons = CronsQuery.getDayNewCrons(day, channel);   // 新增消费用户数
                acr = CronsQuery.getDayACR(day, channel);   // 活跃消费率
                ncr = CronsQuery.getDayNCR(day, channel);   // 新增消费率
                apcc = CronsQuery.getDayAPCC(day, channel);   // 活跃人均消费
                npcc = CronsQuery.getDayNPCC(day, channel);   // 新增人均消费
            } catch (Exception e) {
                e.printStackTrace();
            }
            list.add(new Crons(day, acm, ncm, activeCrons, newCrons, acr, ncr, apcc, npcc));
        }
        return list;
    }

    /**
     * 获取自定义时期内总数
     */
    public static List<CronsTotalData> getTotalData(List<Crons> inList) {
        List<CronsTotalData> list = new ArrayList<>();
        double acmDou = 0.00;
        double ncmDou = 0.00;
        int activeCronsInt = 0;
        int newCronsInt = 0;
        try {
            for (Crons Crons : inList) {
                String acm = Crons.getAcm();
                String ncm = Crons.getNcm();
                String activeCrons = Crons.getActiveCrons();
                String newCrons = Crons.getNewCrons();
                acmDou += Double.valueOf(acm);
                ncmDou += Double.valueOf(ncm);
                activeCronsInt += Integer.parseInt(activeCrons);
                newCronsInt += Integer.parseInt(newCrons);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(new CronsTotalData(MyUtils.getYuan(acmDou), MyUtils.getYuan(ncmDou), String.valueOf(activeCronsInt), String.valueOf(newCronsInt)));
        return list;
    }

    public static List<CronsTotalData> getTotalDataByChannel(List<CronsByChannel> inList) {
        List<CronsTotalData> list = new ArrayList<>();
        double acmDou = 0.00;
        double ncmDou = 0.00;
        int activeCronsInt = 0;
        int newCronsInt = 0;
        try {
            for (CronsByChannel Crons : inList) {
                String acm = Crons.getAcm();
                String ncm = Crons.getNcm();
                String activeCrons = Crons.getActiveCrons();
                String newCrons = Crons.getNewCrons();
                acmDou += Double.valueOf(acm);
                ncmDou += Double.valueOf(ncm);
                activeCronsInt += Integer.parseInt(activeCrons);
                newCronsInt += Integer.parseInt(newCrons);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(new CronsTotalData(MyUtils.getYuan(acmDou), MyUtils.getYuan(ncmDou), String.valueOf(activeCronsInt), String.valueOf(newCronsInt)));
        return list;
    }


    public static void main(String[] args) {
        List<CronsByChannel> android = getManyDayCrons("20180810", "20180810", "android");
        for (CronsByChannel pbc : android) {
            System.out.println(pbc.getTimes() + "---" + pbc.getChannel() + "," + pbc.getAcm() + "," + pbc.getNcm() + "," + pbc.getActiveCrons() + "," + pbc.getNewCrons() + "," + pbc.getAcr() + "," + pbc.getNcr() + "," + pbc.getApcc() + "," + pbc.getNpcc());
        }
        List<Crons> all = getManyDayCrons("20180810", "20180810");
        for (Crons pbc : all) {
            System.out.println(pbc.getTimes() + "---" + pbc.getAcm() + "," + pbc.getNcm() + "," + pbc.getActiveCrons() + "," + pbc.getNewCrons() + "," + pbc.getAcr() + "," + pbc.getNcr() + "," + pbc.getApcc() + "," + pbc.getNpcc());
        }
    }
}

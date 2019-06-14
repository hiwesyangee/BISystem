package biquery.payquery;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class PayInterface {
    /**
     * 获取一天内充值相关数据
     */
    public static List<PayByChannel> getDayPay(String time1, String channel) {
        return getManyDayPay(time1, time1, channel);
    }

    public static List<Pay> getDayPay(String time1) {
        return getManyDayPay(time1, time1);
    }

    /**
     * 获取自定义时期内的充值相关数据
     */
    public static List<PayByChannel> getManyDayPay(String time1, String time2, String channel) {
        List<PayByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        for (String day : days) {
            String apm = "0.00";  // 活跃充值额
            String npm = "0.00";  // 新增充值额
            String activePay = "0";    // 活跃充值用户数
            String newPay = "0";   // 新增充值用户数
            String apr = "0.00";   // 活跃充值率
            String npr = "0.00";   // 新增充值率
            String appc = "0.00";   // 活跃人均充值
            String nppc = "0.00";   // 新增人均充值
            String aARPU = "0.00";   // 活跃ARPU
            String aARPPU = "0.00";   // 活跃ARPPU
            String nARPU = "0.00";   // 新增ARPU
            String nARPPU = "0.00";   // 新增ARPPU
            try {
                apm = PayQuery.getDayAPM(day, channel);  // 活跃充值额
                npm = PayQuery.getDayNPM(day, channel);  // 新增充值额
                activePay = PayQuery.getDayActivePay(day, channel);    // 活跃充值用户数
                newPay = PayQuery.getDayNewPay(day, channel);   // 新增充值用户数
                apr = PayQuery.getDayAPR(day, channel);   // 活跃充值率
                npr = PayQuery.getDayNPR(day, channel);   // 新增充值率
                appc = PayQuery.getDayAPPC(day, channel);   // 活跃人均充值
                nppc = PayQuery.getDayNPPC(day, channel);   // 新增人均充值
                aARPU = PayQuery.getDayaARPU(day, channel);   // 活跃ARPU
                aARPPU = PayQuery.getDayaARPPU(day, channel);   // 活跃ARPPU
                nARPU = PayQuery.getDaynARPU(day, channel);   // 新增ARPU
                nARPPU = PayQuery.getDaynARPPU(day, channel);   // 新增ARPPU
            } catch (Exception e) {
                e.printStackTrace();
            }
            list.add(new PayByChannel(day, channel, apm, npm, activePay, newPay, apr, npr, appc, nppc, aARPU, aARPPU, nARPU, nARPPU));
        }
        return list;
    }

    public static List<Pay> getManyDayPay(String time1, String time2) {
        List<Pay> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2);
        for (String day : days) {
            String apm = "0.00";  // 活跃充值额
            String npm = "0.00";  // 新增充值额
            String activePay = "0";    // 活跃充值用户数
            String newPay = "0";   // 新增充值用户数
            String apr = "0.00";   // 活跃充值率
            String npr = "0.00";   // 新增充值率
            String appc = "0.00";   // 活跃人均充值
            String nppc = "0.00";   // 新增人均充值
            String aARPU = "0.00";   // 活跃ARPU
            String aARPPU = "0.00";   // 活跃ARPPU
            String nARPU = "0.00";   // 新增ARPU
            String nARPPU = "0.00";   // 新增ARPPU
            String channel = "all";
            try {
                apm = PayQuery.getDayAPM(day, channel);  // 活跃充值额
                npm = PayQuery.getDayNPM(day, channel);  // 新增充值额
                activePay = PayQuery.getDayActivePay(day, channel);    // 活跃充值用户数
                newPay = PayQuery.getDayNewPay(day, channel);   // 新增充值用户数
                apr = PayQuery.getDayAPR(day, channel);   // 活跃充值率
                npr = PayQuery.getDayNPR(day, channel);   // 新增充值率
                appc = PayQuery.getDayAPPC(day, channel);   // 活跃人均充值
                nppc = PayQuery.getDayNPPC(day, channel);   // 新增人均充值
                aARPU = PayQuery.getDayaARPU(day, channel);   // 活跃ARPU
                aARPPU = PayQuery.getDayaARPPU(day, channel);   // 活跃ARPPU
                nARPU = PayQuery.getDaynARPU(day, channel);   // 新增ARPU
                nARPPU = PayQuery.getDaynARPPU(day, channel);   // 新增ARPPU
            } catch (Exception e) {
                e.printStackTrace();
            }
            list.add(new Pay(day, apm, npm, activePay, newPay, apr, npr, appc, nppc, aARPU, aARPPU, nARPU, nARPPU));
        }
        return list;
    }

    /**
     * 获取自定义时期内总数
     */
    public static List<PayTotalData> getTotalData(List<Pay> inList) {
        List<PayTotalData> list = new ArrayList<>();
        double apmDou = 0.00;
        double npmDou = 0.00;
        int activePayInt = 0;
        int newPayInt = 0;
        try {
            for (Pay Pay : inList) {
                String apm = Pay.getApm();
                String npm = Pay.getNpm();
                String activePay = Pay.getActivePay();
                String newPay = Pay.getNewPay();
                apmDou += Double.valueOf(apm);
                npmDou += Double.valueOf(npm);
                activePayInt += Integer.parseInt(activePay);
                newPayInt += Integer.parseInt(newPay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(new PayTotalData(MyUtils.getYuan(apmDou), MyUtils.getYuan(npmDou), String.valueOf(activePayInt), String.valueOf(newPayInt)));
        return list;
    }

    public static List<PayTotalData> getTotalDataByChannel(List<PayByChannel> inList) {
        List<PayTotalData> list = new ArrayList<>();
        double apmDou = 0.00;
        double npmDou = 0.00;
        int activePayInt = 0;
        int newPayInt = 0;
        try {
            for (PayByChannel Pay : inList) {
                String apm = Pay.getApm();
                String npm = Pay.getNpm();
                String activePay = Pay.getActivePay();
                String newPay = Pay.getNewPay();
                apmDou += Double.valueOf(apm);
                npmDou += Double.valueOf(npm);
                activePayInt += Integer.parseInt(activePay);
                newPayInt += Integer.parseInt(newPay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(new PayTotalData(MyUtils.getYuan(apmDou), MyUtils.getYuan(npmDou), String.valueOf(activePayInt), String.valueOf(newPayInt)));
        return list;
    }


    public static void main(String[] args) {
        List<PayByChannel> android = getManyDayPay("20180810", "20180810", "android");
        for (PayByChannel pbc : android) {
            System.out.println(pbc.getTimes() + "---" + pbc.getChannel() + "," + pbc.getApm() + "," + pbc.getNpm() + "," + pbc.getActivePay() + "," + pbc.getNewPay() + "," + pbc.getApr() + "," + pbc.getNpr() + "," + pbc.getAppc() + "," + pbc.getNppc() + "," + pbc.getaARPU() + "," + pbc.getaARPPU() + "," + pbc.getnARPU() + "," + pbc.getnARPPU());
        }
        List<Pay> all = getManyDayPay("20180810", "20180810");
        for (Pay pbc : all) {
            System.out.println(pbc.getTimes() + "---" + pbc.getApm() + "," + pbc.getNpm() + "," + pbc.getActivePay() + "," + pbc.getNewPay() + "," + pbc.getApr() + "," + pbc.getNpr() + "," + pbc.getAppc() + "," + pbc.getNppc() + "," + pbc.getaARPU() + "," + pbc.getaARPPU() + "," + pbc.getnARPU() + "," + pbc.getnARPPU());
        }
    }

}

package biquery.userliucun;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class UserLiucunInterface {
    /**
     * 获取一天内用户留存相关数据
     */
    public static List<UserLiucunByChannel> getDayUserLiucun(String time1, String channel) {
        return getManyDayUserLiucun(time1, time1, channel);
    }

    public static List<UserLiucun> getDayUserLiucun(String time1) {
        return getManyDayUserLiucun(time1, time1);
    }

    /**
     * 获取自定义之间内用户留存相关数据
     */
    public static List<UserLiucunByChannel> getManyDayUserLiucun(String time1, String time2, String channel) {
        List<UserLiucunByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2); // 1216,1217,1218
        for (String day : days) {
            String liucunWeek2A = "0.00";
            String liucunWeek2B = "0.00";
            UserLiucunByChannel first = new UserLiucunByChannel(day, channel, liucunWeek2A, liucunWeek2B);
            UserLiucunByChannel end = getDetailedData(first);  // 获取详细数据
            list.add(end);
        }
        return list;
    }

    /**
     * 每日渠道用户留存相关数据补全
     */
    private static UserLiucunByChannel getDetailedData(UserLiucunByChannel first) {
        String day = first.getTimes();
        String channel = first.getChannel();
        String liucunWeek2A = first.getLiucunWeek2A();
        String liucunWeek2B = first.getLiucunWeek2B();

        try {
            String dayUserWeek2 = UserLiucunQuery.getDayUserWeek2(day, channel);
            liucunWeek2A = dayUserWeek2.split("_")[0];
            liucunWeek2B = dayUserWeek2.split("_")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UserLiucunByChannel(day, channel, liucunWeek2A, liucunWeek2B);
    }

    public static List<UserLiucun> getManyDayUserLiucun(String time1, String time2) {
        List<UserLiucun> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2); // 1216,1217,1218
        for (String day : days) {
            String liucunWeek2A = "0.00";
            String liucunWeek2B = "0.00";
            UserLiucun first = new UserLiucun(day, liucunWeek2A, liucunWeek2B);
            UserLiucun end = getDetailedData(first);  // 获取详细数据
            list.add(end);
        }

        return list;
    }

    /**
     * 每日渠道设备留存相关数据补全
     */
    private static UserLiucun getDetailedData(UserLiucun first) {
        String day = first.getTimes();
        String channel = "all";
        String liucunWeek2A = first.getLiucunWeek2A();
        String liucunWeek2B = first.getLiucunWeek2B();

        try {
            String dayUserWeek2 = UserLiucunQuery.getDayUserWeek2(day, channel);
            liucunWeek2A = dayUserWeek2.split("_")[0];
            liucunWeek2B = dayUserWeek2.split("_")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UserLiucun(day, liucunWeek2A, liucunWeek2B);
    }

}

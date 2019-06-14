package biquery.userquery;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取所有设备相关数据的对外接口
 */
public class UserInterface {
    /**
     * 获取一天内设备相关数据
     */
    public static List<UserByChannel> getDayUser(String time1, String channel) {
        return getManyDayUser(time1, time1, channel);
    }

    public static List<User> getDayUser(String time1) {
        return getManyDayUser(time1, time1);
    }


    /**
     * 获取自定义时期内设备相关数据
     */
    public static List<UserByChannel> getManyDayUser(String time1, String time2, String channel) {
        List<UserByChannel> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2); // 1216,1217,1218
        for (String day : days) {
            String activateUser = "0"; // 新增用户数
            String activeUser = "0";  // 活跃用户数
            String topUserOnline = "0"; // 最高在线用户数
            String liucun = "0.00"; // 次日留存
            String liucun2 = "0.00"; // 2日留存
            String liucun3 = "0.00"; // 3日留存
            String liucun4 = "0.00"; // 4日留存
            String liucun5 = "0.00"; // 5日留存
            String liucun6 = "0.00"; // 6日留存
            String liucunWeek = "0.00"; // 周留存
            String liucunWeek2 = "0.00"; // 2周留存
            String liucunMonth = "0.00"; // 月留存

            UserByChannel first = new UserByChannel(day, channel, activateUser, activeUser, topUserOnline, liucun, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth);
            UserByChannel result = getDetailedData(first);  // 获取详细数据
            list.add(result);
        }
        return list;
    }

    public static List<User> getManyDayUser(String time1, String time2) {
        List<User> list = new ArrayList<>();
        List<String> days = MyUtils.checkTime(time1, time2); // 1216,1217,1218
        for (String day : days) {
            String activateUser = "0"; // 新增用户数
            String activeUser = "0";  // 活跃用户数
            String topUserOnline = "0"; // 最高在线用户数
            String liucun = "0.00"; // 次日留存
            String liucun2 = "0.00"; // 2日留存
            String liucun3 = "0.00"; // 3日留存
            String liucun4 = "0.00"; // 4日留存
            String liucun5 = "0.00"; // 5日留存
            String liucun6 = "0.00"; // 6日留存
            String liucunWeek = "0.00"; // 周留存
            String liucunWeek2 = "0.00"; // 2周留存
            String liucunMonth = "0.00"; // 月留存
            User first = new User(day, activateUser, activeUser, topUserOnline, liucun, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth);
            User result = getDetailedData(first);  // 获取详细数据
            list.add(result);
        }
        return list;
    }


    /**
     * 每日设备相关数据补全
     */
    private static UserByChannel getDetailedData(UserByChannel first) {
        String day = first.getTimes();
        String channel = first.getChannel();
        String activateUserNumber = first.getActivateUser();
        String activeUser = first.getActiveUser();
        String topUserOnline = first.getTopUserOnline();
        String liucun1 = first.getLiucun();
        String liucun2 = first.getLiucun2();
        String liucun3 = first.getLiucun3();
        String liucun4 = first.getLiucun4();
        String liucun5 = first.getLiucun5();
        String liucun6 = first.getLiucun6();
        String liucunWeek = first.getLiucunWeek();
        String liucunWeek2 = first.getLiucunWeek2();
        String liucunMonth = first.getLiucunMonth();

        try {
            activateUserNumber = DayUserQuery.getDayNewUserNumber(day, channel);
            activeUser = DayUserQuery.getDayActiveUserNumber(day, channel);
            topUserOnline = DayUserQuery.getDayTopUserOnlineNumber(day, channel, activeUser);
            liucun1 = DayUserQuery.getLiucunNumber(day, 1, channel, activateUserNumber);
            liucun2 = DayUserQuery.getLiucunNumber(day, 2, channel, activateUserNumber);
            liucun3 = DayUserQuery.getLiucunNumber(day, 3, channel, activateUserNumber);
            liucun4 = DayUserQuery.getLiucunNumber(day, 4, channel, activateUserNumber);
            liucun5 = DayUserQuery.getLiucunNumber(day, 5, channel, activateUserNumber);
            liucun6 = DayUserQuery.getLiucunNumber(day, 6, channel, activateUserNumber);
            liucunWeek = DayUserQuery.getLiucunNumber(day, 7, channel, activateUserNumber);
            liucunWeek2 = DayUserQuery.getLiucunNumber(day, 14, channel, activateUserNumber);
            liucunMonth = DayUserQuery.getLiucunNumber(day, 30, channel, activateUserNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UserByChannel(day, channel, activateUserNumber, activeUser, topUserOnline, liucun1, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth);
    }

    private static User getDetailedData(User first) {
        String day = first.getTimes();
        String channel = "all";
        String activateUserNumber = first.getActivateUser();
        String activeUser = first.getActiveUser();
        String topUserOnline = first.getTopUserOnline();
        String liucun1 = first.getLiucun();
        String liucun2 = first.getLiucun2();
        String liucun3 = first.getLiucun3();
        String liucun4 = first.getLiucun4();
        String liucun5 = first.getLiucun5();
        String liucun6 = first.getLiucun6();
        String liucunWeek = first.getLiucunWeek();
        String liucunWeek2 = first.getLiucunWeek2();
        String liucunMonth = first.getLiucunMonth();

        try {
            activateUserNumber = DayUserQuery.getDayNewUserNumber(day, channel);
            activeUser = DayUserQuery.getDayActiveUserNumber(day, channel);
            topUserOnline = DayUserQuery.getDayTopUserOnlineNumber(day, channel, activeUser);
            liucun1 = DayUserQuery.getLiucunNumber(day, 1, channel, activateUserNumber);
            liucun2 = DayUserQuery.getLiucunNumber(day, 2, channel, activateUserNumber);
            liucun3 = DayUserQuery.getLiucunNumber(day, 3, channel, activateUserNumber);
            liucun4 = DayUserQuery.getLiucunNumber(day, 4, channel, activateUserNumber);
            liucun5 = DayUserQuery.getLiucunNumber(day, 5, channel, activateUserNumber);
            liucun6 = DayUserQuery.getLiucunNumber(day, 6, channel, activateUserNumber);
            liucunWeek = DayUserQuery.getLiucunNumber(day, 7, channel, activateUserNumber);
            liucunWeek2 = DayUserQuery.getLiucunNumber(day, 14, channel, activateUserNumber);
            liucunMonth = DayUserQuery.getLiucunNumber(day, 30, channel, activateUserNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User(day, activateUserNumber, activeUser, topUserOnline, liucun1, liucun2, liucun3, liucun4, liucun5, liucun6, liucunWeek, liucunWeek2, liucunMonth);
    }


    /**
     * 获取自定义时期内总数
     */
    public static List<UserTotalData> getTotalData(List<User> inList) {
        List<UserTotalData> list = new ArrayList<>();
        int activateUserInt = 0;
        int activeUserInt = 0;
        try {
            for (User User : inList) {
                String activateUser = User.getActivateUser();
                String activeUser = User.getActiveUser();
                activateUserInt += Integer.parseInt(activateUser);
                activeUserInt += Integer.parseInt(activeUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(new UserTotalData(String.valueOf(activateUserInt), String.valueOf(activeUserInt)));
        return list;
    }

    public static List<UserTotalData> getTotalDataByChannel(List<UserByChannel> inList) {
        List<UserTotalData> list = new ArrayList<>();
        int activateUserInt = 0;
        int activeUserInt = 0;
        try {
            for (UserByChannel User : inList) {
                String activateUser = User.getActivateUser();
                String activeUser = User.getActiveUser();
                activateUserInt += Integer.parseInt(activateUser);
                activeUserInt += Integer.parseInt(activeUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(new UserTotalData(String.valueOf(activateUserInt), String.valueOf(activeUserInt)));
        return list;
    }


    public static void main(String[] args) {
        List<UserByChannel> weixin = getManyDayUser("20180810", "20190110", "weixin");
        for (UserByChannel ubc : weixin) {
            System.out.println(ubc.getTimes() + "-----" + ubc.getChannel() + "---" + ubc.getActivateUser() + "," + ubc.getActiveUser() + "," + ubc.getTopUserOnline() + "," + ubc.getLiucun() + "," + ubc.getLiucun2() + "," + ubc.getLiucun3() + "," + ubc.getLiucun4() + "," + ubc.getLiucun5() + "," + ubc.getLiucun6() + "," + ubc.getLiucunWeek() + "," + ubc.getLiucunWeek2() + "," + ubc.getLiucunMonth());
        }
        System.out.println("======================");
        List<User> all = getManyDayUser("20180810", "20190110");
        for (User ubc : all) {
            System.out.println(ubc.getTimes() + "-----" + ubc.getActivateUser() + "," + ubc.getActiveUser() + "," + ubc.getTopUserOnline() + "," + ubc.getLiucun() + "," + ubc.getLiucun2() + "," + ubc.getLiucun3() + "," + ubc.getLiucun4() + "," + ubc.getLiucun5() + "," + ubc.getLiucun6() + "," + ubc.getLiucunWeek() + "," + ubc.getLiucunWeek2() + "," + ubc.getLiucunMonth());
        }
    }

}

package biquery.userquery;

/**
 * 用户测试类
 */
public class UserTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        String[] arr = {"20190103", "20190104", "20190105", "20190106", "20190107", "20190108", "20190109", "20190110"};
        String[] arr = {"20180810"};
        for (String day : arr) {
            String activateUserNumber = DayUserQuery.getDayNewUserNumber(day, "app");
            String activeUserNumber = DayUserQuery.getDayActiveUserNumber(day, "app");
            String topUserOnline = DayUserQuery.getDayTopUserOnlineNumber(day, "app", activeUserNumber);

            String liucun1 = DayUserQuery.getLiucunNumber(day, 1, "app", activateUserNumber);
            String liucun2 = DayUserQuery.getLiucunNumber(day, 2, "app", activateUserNumber);
            String liucun3 = DayUserQuery.getLiucunNumber(day, 3, "app", activateUserNumber);
            String liucun4 = DayUserQuery.getLiucunNumber(day, 4, "app", activateUserNumber);
            String liucun5 = DayUserQuery.getLiucunNumber(day, 5, "app", activateUserNumber);
            String liucun6 = DayUserQuery.getLiucunNumber(day, 6, "app", activateUserNumber);
            String liucunWeek = DayUserQuery.getLiucunNumber(day, 7, "app", activateUserNumber);
            String liucunWeek2 = DayUserQuery.getLiucunNumber(day, 14, "app", activateUserNumber);
            String liucunMonth = DayUserQuery.getLiucunNumber(day, 30, "app", activateUserNumber);
            System.out.println(day + "===" + activateUserNumber + "," + activeUserNumber + "," + topUserOnline + "," + liucun1 + "," + liucun2 + "," + liucun3 + "," + liucun4 + "," + liucun5 + "," + liucun6 + "," + liucunWeek + "," + liucunWeek2 + "," + liucunMonth);
        }

        long stop = System.currentTimeMillis();

        System.out.println((stop - start) / 1000);
    }
}

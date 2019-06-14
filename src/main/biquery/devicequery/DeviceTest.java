package biquery.devicequery;

/**
 * 设备测试类
 */
public class DeviceTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        String[] arr = {"20190103", "20190104", "20190105", "20190106", "20190107", "20190108", "20190109", "20190110"};
        String[] arr = {"20180810"};
        for (String day : arr) {
            String activateDeviceNumber = DayDeviceQuery.getDayNewDeviceNumber(day, "app");
            String activeDeviceNumber = DayDeviceQuery.getDayActiveDeviceNumber(day, "app");
            String topDeviceOnline = DayDeviceQuery.getDayTopDeviceOnlineNumber(day, "app", activeDeviceNumber);
            String totappoginTimes = DayDeviceQuery.getDayTotalLoginTimesNumber(day, "app");
            String averageOnlineTime = DayDeviceQuery.getDayAverageOnlineTimeNumber(day, "app", activateDeviceNumber, activeDeviceNumber);

            String liucun1 = DayDeviceQuery.getLiucunNumber(day, 1, "app", activateDeviceNumber);
            String liucun2 = DayDeviceQuery.getLiucunNumber(day, 2, "app", activateDeviceNumber);
            String liucun3 = DayDeviceQuery.getLiucunNumber(day, 3, "app", activateDeviceNumber);
            String liucun4 = DayDeviceQuery.getLiucunNumber(day, 4, "app", activateDeviceNumber);
            String liucun5 = DayDeviceQuery.getLiucunNumber(day, 5, "app", activateDeviceNumber);
            String liucun6 = DayDeviceQuery.getLiucunNumber(day, 6, "app", activateDeviceNumber);
            String liucunWeek = DayDeviceQuery.getLiucunNumber(day, 7, "app", activateDeviceNumber);
            String liucunWeek2 = DayDeviceQuery.getLiucunNumber(day, 14, "app", activateDeviceNumber);
            String liucunMonth = DayDeviceQuery.getLiucunNumber(day, 30, "app", activateDeviceNumber);
            System.out.println(day + "===" + activateDeviceNumber + "," + activeDeviceNumber + "," + topDeviceOnline + "," + totappoginTimes + "," + averageOnlineTime + "," + liucun1 + "," + liucun2 + "," + liucun3 + "," + liucun4 + "," + liucun5 + "," + liucun6 + "," + liucunWeek + "," + liucunWeek2 + "," + liucunMonth);
        }

        long stop = System.currentTimeMillis();

        System.out.println((stop - start) / 1000);
    }


}

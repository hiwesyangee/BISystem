package engine_abandoned.deviceAnduser;

/**
 * 每小时在线用户数量统计
 */
public class UserHourlyOnline {
    private String times; // 具体时间
    private String number; // 在线人数

    public UserHourlyOnline(String times, String number) {
        this.times = times;
        this.number = number;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

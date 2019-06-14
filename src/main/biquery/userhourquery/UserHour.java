package biquery.userhourquery;

/**
 * 获取每小时用户在线数据
 */
public class UserHour {
    private String times; // 具体时间
    private String number; // 在线人数

    public UserHour(String times, String number) {
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

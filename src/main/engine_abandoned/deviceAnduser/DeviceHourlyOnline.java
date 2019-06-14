package engine_abandoned.deviceAnduser;

/**
 * 每小时在线设备数量统计
 */
public class DeviceHourlyOnline {
    private String times; // 具体时间
    private String number; // 在线人数

    public DeviceHourlyOnline(String times, String number) {
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

package biquery.userhourquery;

/**
 * 根据渠道获取每小时用户在线数据
 */
public class UserHourByChannel {
    private String times; // 具体时间
    private String channel; // 渠道
    private String number; // 在线人数

    public UserHourByChannel(String times, String channel, String number) {
        this.times = times;
        this.channel = channel;
        this.number = number;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

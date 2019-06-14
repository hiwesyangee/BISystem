package biquery.deviceliucun;

public class DeviceLiucunByChannel {
    private String times; // 具体天
    private String channel; // 渠道
    private String liucunWeek2A; // 两周留存，之后1-14天都登录的
    private String liucunWeek2B; // 两周留存，之后8-14天都登陆的

    public DeviceLiucunByChannel(String times, String channel, String liucunWeek2A, String liucunWeek2B) {
        this.times = times;
        this.channel = channel;
        this.liucunWeek2A = liucunWeek2A;
        this.liucunWeek2B = liucunWeek2B;
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

    public String getLiucunWeek2A() {
        return liucunWeek2A;
    }

    public void setLiucunWeek2A(String liucunWeek2A) {
        this.liucunWeek2A = liucunWeek2A;
    }

    public String getLiucunWeek2B() {
        return liucunWeek2B;
    }

    public void setLiucunWeek2B(String liucunWeek2B) {
        this.liucunWeek2B = liucunWeek2B;
    }
}

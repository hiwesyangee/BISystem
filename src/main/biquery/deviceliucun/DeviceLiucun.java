package biquery.deviceliucun;

public class DeviceLiucun {
    private String times; // 具体天
    private String liucunWeek2A; // 两周留存，之后1-14天都登录的
    private String liucunWeek2B; // 两周留存，之后8-14天都登陆的

    public DeviceLiucun(String times, String liucunWeek2A, String liucunWeek2B) {
        this.times = times;
        this.liucunWeek2A = liucunWeek2A;
        this.liucunWeek2B = liucunWeek2B;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
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

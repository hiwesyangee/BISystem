package engine_abandoned.deviceAnduser;

/**
 * 根据渠道获取相关数据
 */
public class AllDeviceByChannel {
    private String times; // 具体天
    private String channel;
    private String activateDevice; // 新增设备
    private String activeDevice; // 登陆设备
    private String topDeviceOnline; // 最大每小时在线设备数
    private String totalLoginTimes; // 当天总设备启动次数
    private String averageOnlineTime; // 平均在线时长，精确到秒
    private String liucun; // 次日留存
    private String liucun2; // 2日留存
    private String liucun3; // 3日留存
    private String liucun4; // 4日留存
    private String liucun5; // 5日留存
    private String liucun6; // 6日留存
    private String liucunWeek; // 周留存
    private String liucunWeek2; // 2周留存
    private String liucunMonth; // 月留存

    public AllDeviceByChannel(String times, String channel, String activateDevice, String activeDevice, String topDeviceOnline, String totalLoginTimes, String averageOnlineTime, String liucun, String liucun2, String liucun3, String liucun4, String liucun5, String liucun6, String liucunWeek, String liucunWeek2, String liucunMonth) {
        this.times = times;
        this.channel = channel;
        this.activateDevice = activateDevice;
        this.activeDevice = activeDevice;
        this.topDeviceOnline = topDeviceOnline;
        this.totalLoginTimes = totalLoginTimes;
        this.averageOnlineTime = averageOnlineTime;
        this.liucun = liucun;
        this.liucun2 = liucun2;
        this.liucun3 = liucun3;
        this.liucun4 = liucun4;
        this.liucun5 = liucun5;
        this.liucun6 = liucun6;
        this.liucunWeek = liucunWeek;
        this.liucunWeek2 = liucunWeek2;
        this.liucunMonth = liucunMonth;
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

    public String getActivateDevice() {
        return activateDevice;
    }

    public void setActivateDevice(String activateDevice) {
        this.activateDevice = activateDevice;
    }

    public String getActiveDevice() {
        return activeDevice;
    }

    public void setActiveDevice(String activeDevice) {
        this.activeDevice = activeDevice;
    }

    public String getTopDeviceOnline() {
        return topDeviceOnline;
    }

    public void setTopDeviceOnline(String topDeviceOnline) {
        this.topDeviceOnline = topDeviceOnline;
    }

    public String getTotalLoginTimes() {
        return totalLoginTimes;
    }

    public void setTotalLoginTimes(String totalLoginTimes) {
        this.totalLoginTimes = totalLoginTimes;
    }

    public String getAverageOnlineTime() {
        return averageOnlineTime;
    }

    public void setAverageOnlineTime(String averageOnlineTime) {
        this.averageOnlineTime = averageOnlineTime;
    }

    public String getLiucun() {
        return liucun;
    }

    public void setLiucun(String liucun) {
        this.liucun = liucun;
    }

    public String getLiucun2() {
        return liucun2;
    }

    public void setLiucun2(String liucun2) {
        this.liucun2 = liucun2;
    }

    public String getLiucun3() {
        return liucun3;
    }

    public void setLiucun3(String liucun3) {
        this.liucun3 = liucun3;
    }

    public String getLiucun4() {
        return liucun4;
    }

    public void setLiucun4(String liucun4) {
        this.liucun4 = liucun4;
    }

    public String getLiucun5() {
        return liucun5;
    }

    public void setLiucun5(String liucun5) {
        this.liucun5 = liucun5;
    }

    public String getLiucun6() {
        return liucun6;
    }

    public void setLiucun6(String liucun6) {
        this.liucun6 = liucun6;
    }

    public String getLiucunWeek() {
        return liucunWeek;
    }

    public void setLiucunWeek(String liucunWeek) {
        this.liucunWeek = liucunWeek;
    }

    public String getLiucunWeek2() {
        return liucunWeek2;
    }

    public void setLiucunWeek2(String liucunWeek2) {
        this.liucunWeek2 = liucunWeek2;
    }

    public String getLiucunMonth() {
        return liucunMonth;
    }

    public void setLiucunMonth(String liucunMonth) {
        this.liucunMonth = liucunMonth;
    }
}

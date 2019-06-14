package engine_abandoned.deviceAnduser;

/**
 * 按渠道获取用户相关数据
 */
public class AllUserByChannel {
    private String times; // 具体天
    private String channel;
    private String activateUser; // 新增用户
    private String activeUser; // 活跃用户
    private String topUserOnline; // 最高在线用户（先按小时计算）
    private String liucun; // 次日留存
    private String liucun2; // 2日留存
    private String liucun3; // 3日留存
    private String liucun4; // 4日留存
    private String liucun5; // 5日留存
    private String liucun6; // 6日留存
    private String liucunWeek; // 周留存
    private String liucunWeek2; // 2周留存
    private String liucunMonth; // 月留存

    public AllUserByChannel(String times, String channel, String activateUser, String activeUser, String topUserOnline, String liucun, String liucun2, String liucun3, String liucun4, String liucun5, String liucun6, String liucunWeek, String liucunWeek2, String liucunMonth) {
        this.times = times;
        this.channel = channel;
        this.activateUser = activateUser;
        this.activeUser = activeUser;
        this.topUserOnline = topUserOnline;
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

    public String getActivateUser() {
        return activateUser;
    }

    public void setActivateUser(String activateUser) {
        this.activateUser = activateUser;
    }

    public String getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(String activeUser) {
        this.activeUser = activeUser;
    }

    public String getTopUserOnline() {
        return topUserOnline;
    }

    public void setTopUserOnline(String topUserOnline) {
        this.topUserOnline = topUserOnline;
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

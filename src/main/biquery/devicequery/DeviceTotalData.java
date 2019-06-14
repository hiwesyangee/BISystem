package biquery.devicequery;

/**
 * 2.12修改，增加总数统计
 */
public class DeviceTotalData {

    private String activateDeviceTotal; // 总激活设备
    private String activeDeviceTotal; // 总活跃设备
    private String totalLoginTimes; // 总设备启动次数

    public DeviceTotalData(String activateDeviceTotal, String activeDeviceTotal, String totalLoginTimes) {
        this.activateDeviceTotal = activateDeviceTotal;
        this.activeDeviceTotal = activeDeviceTotal;
        this.totalLoginTimes = totalLoginTimes;
    }

    public String getActivateDeviceTotal() {
        return activateDeviceTotal;
    }

    public void setActivateDeviceTotal(String activateDeviceTotal) {
        this.activateDeviceTotal = activateDeviceTotal;
    }

    public String getActiveDeviceTotal() {
        return activeDeviceTotal;
    }

    public void setActiveDeviceTotal(String activeDeviceTotal) {
        this.activeDeviceTotal = activeDeviceTotal;
    }

    public String getTotalLoginTimes() {
        return totalLoginTimes;
    }

    public void setTotalLoginTimes(String totalLoginTimes) {
        this.totalLoginTimes = totalLoginTimes;
    }
}

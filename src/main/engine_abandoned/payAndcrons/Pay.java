package engine_abandoned.payAndcrons;

/**
 * 用户充值相关数据接口
 */
public class Pay {
    private String times; // 时间
    private String apm;  // 活跃充值额
    private String npm;  // 新增充值额
    private String activePay;    // 活跃充值用户数
    private String newPay;   // 新增充值用户数
    private String apr;   // 活跃充值率
    private String npr;   // 新增充值率
    private String appc;   // 活跃人均充值
    private String nppc;   // 新增人均充值
    private String aARPU;   // 活跃ARPU
    private String aARPPU;   // 活跃ARPPU
    private String nARPU;   // 新增ARPU
    private String nARPPU;   // 新增ARPPU

    public Pay(String times, String apm, String npm, String activePay, String newPay, String apr, String npr, String appc, String nppc, String aARPU, String aARPPU, String nARPU, String nARPPU) {
        this.times = times;
        this.apm = apm;
        this.npm = npm;
        this.activePay = activePay;
        this.newPay = newPay;
        this.apr = apr;
        this.npr = npr;
        this.appc = appc;
        this.nppc = nppc;
        this.aARPU = aARPU;
        this.aARPPU = aARPPU;
        this.nARPU = nARPU;
        this.nARPPU = nARPPU;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getApm() {
        return apm;
    }

    public void setApm(String apm) {
        this.apm = apm;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getActivePay() {
        return activePay;
    }

    public void setActivePay(String activePay) {
        this.activePay = activePay;
    }

    public String getNewPay() {
        return newPay;
    }

    public void setNewPay(String newPay) {
        this.newPay = newPay;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getNpr() {
        return npr;
    }

    public void setNpr(String npr) {
        this.npr = npr;
    }

    public String getAppc() {
        return appc;
    }

    public void setAppc(String appc) {
        this.appc = appc;
    }

    public String getNppc() {
        return nppc;
    }

    public void setNppc(String nppc) {
        this.nppc = nppc;
    }

    public String getaARPU() {
        return aARPU;
    }

    public void setaARPU(String aARPU) {
        this.aARPU = aARPU;
    }

    public String getaARPPU() {
        return aARPPU;
    }

    public void setaARPPU(String aARPPU) {
        this.aARPPU = aARPPU;
    }

    public String getnARPU() {
        return nARPU;
    }

    public void setnARPU(String nARPU) {
        this.nARPU = nARPU;
    }

    public String getnARPPU() {
        return nARPPU;
    }

    public void setnARPPU(String nARPPU) {
        this.nARPPU = nARPPU;
    }
}

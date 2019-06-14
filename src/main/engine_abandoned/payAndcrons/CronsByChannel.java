package engine_abandoned.payAndcrons;

/**
 * 用户消费相关数据接口
 */
public class CronsByChannel {
    private String times; // 时间
    private String channel; // 渠道
    private String acm;  // 活跃消费额
    private String ncm;  // 新增消费额
    private String activeCrons;    // 活跃消费用户数
    private String newCrons;   // 新增消费用户数
    private String acr;   // 活跃消费率
    private String ncr;   // 新增消费率
    private String apcc;   // 活跃人均消费
    private String npcc;   // 新增人均消费

    public CronsByChannel(String times, String channel, String acm, String ncm, String activeCrons, String newCrons, String acr, String ncr, String apcc, String npcc) {
        this.times = times;
        this.channel = channel;
        this.acm = acm;
        this.ncm = ncm;
        this.activeCrons = activeCrons;
        this.newCrons = newCrons;
        this.acr = acr;
        this.ncr = ncr;
        this.apcc = apcc;
        this.npcc = npcc;
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

    public String getAcm() {
        return acm;
    }

    public void setAcm(String acm) {
        this.acm = acm;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getActiveCrons() {
        return activeCrons;
    }

    public void setActiveCrons(String activeCrons) {
        this.activeCrons = activeCrons;
    }

    public String getNewCrons() {
        return newCrons;
    }

    public void setNewCrons(String newCrons) {
        this.newCrons = newCrons;
    }

    public String getAcr() {
        return acr;
    }

    public void setAcr(String acr) {
        this.acr = acr;
    }

    public String getNcr() {
        return ncr;
    }

    public void setNcr(String ncr) {
        this.ncr = ncr;
    }

    public String getApcc() {
        return apcc;
    }

    public void setApcc(String apcc) {
        this.apcc = apcc;
    }

    public String getNpcc() {
        return npcc;
    }

    public void setNpcc(String npcc) {
        this.npcc = npcc;
    }
}

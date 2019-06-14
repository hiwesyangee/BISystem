package biquery.cronsquery;

/**
 * 2.12修改，增加总数统计
 */
public class CronsTotalData {

    private String acmTotal;  // 总活跃消费额
    private String ncmTotal;  // 总新增消费额
    private String activeCronsTotal;    // 总活跃消费用户数
    private String newCronsTotal;   // 总新增消费用户数

    public CronsTotalData(String acmTotal, String ncmTotal, String activeCronsTotal, String newCronsTotal) {
        this.acmTotal = acmTotal;
        this.ncmTotal = ncmTotal;
        this.activeCronsTotal = activeCronsTotal;
        this.newCronsTotal = newCronsTotal;
    }

    public String getAcmTotal() {
        return acmTotal;
    }

    public void setAcmTotal(String acmTotal) {
        this.acmTotal = acmTotal;
    }

    public String getNcmTotal() {
        return ncmTotal;
    }

    public void setNcmTotal(String ncmTotal) {
        this.ncmTotal = ncmTotal;
    }

    public String getActiveCronsTotal() {
        return activeCronsTotal;
    }

    public void setActiveCronsTotal(String activeCronsTotal) {
        this.activeCronsTotal = activeCronsTotal;
    }

    public String getNewCronsTotal() {
        return newCronsTotal;
    }

    public void setNewCronsTotal(String newCronsTotal) {
        this.newCronsTotal = newCronsTotal;
    }
}

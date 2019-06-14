package biquery.payquery;

/**
 * 2.12修改，增加总数统计
 */
public class PayTotalData {

    private String apmTotal;  // 总活跃充值额
    private String npmTotal;  // 总新增充值额
    private String activePayTotal;    // 总活跃充值用户数
    private String newPayTotal;   // 总新增充值用户数

    public PayTotalData(String apmTotal, String npmTotal, String activePayTotal, String newPayTotal) {
        this.apmTotal = apmTotal;
        this.npmTotal = npmTotal;
        this.activePayTotal = activePayTotal;
        this.newPayTotal = newPayTotal;
    }

    public String getApmTotal() {
        return apmTotal;
    }

    public void setApmTotal(String apmTotal) {
        this.apmTotal = apmTotal;
    }

    public String getNpmTotal() {
        return npmTotal;
    }

    public void setNpmTotal(String npmTotal) {
        this.npmTotal = npmTotal;
    }

    public String getActivePayTotal() {
        return activePayTotal;
    }

    public void setActivePayTotal(String activePayTotal) {
        this.activePayTotal = activePayTotal;
    }

    public String getNewPayTotal() {
        return newPayTotal;
    }

    public void setNewPayTotal(String newPayTotal) {
        this.newPayTotal = newPayTotal;
    }
}

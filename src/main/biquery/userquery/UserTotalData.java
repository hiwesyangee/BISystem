package biquery.userquery;

/**
 * 2.12修改，增加总数统计
 */
public class UserTotalData {

    private String activateUserTotal; // 总新增用户
    private String activeUserTotal; // 总活跃用户

    public UserTotalData(String activateUserTotal, String activeUserTotal) {
        this.activateUserTotal = activateUserTotal;
        this.activeUserTotal = activeUserTotal;
    }

    public String getActivateUserTotal() {
        return activateUserTotal;
    }

    public void setActivateUserTotal(String activateUserTotal) {
        this.activateUserTotal = activateUserTotal;
    }

    public String getActiveUserTotal() {
        return activeUserTotal;
    }

    public void setActiveUserTotal(String activeUserTotal) {
        this.activeUserTotal = activeUserTotal;
    }
}

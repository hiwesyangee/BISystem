package engine_abandoned.payAndcrons;

/**
 * 用户消费排行相关数据接口
 */
public class CronsRank {
    private String rank;
    private String uid;
    private String number;

    public CronsRank(String rank, String uid, String number) {
        this.rank = rank;
        this.uid = uid;
        this.number = number;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

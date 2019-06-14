package engine_abandoned.orderAndread;

/**
 * 阅读排行相关数据对象
 */
public class ReadRank {
    private String rank;
    private String cID;
    private String number;

    public ReadRank(String rank, String cID, String number) {
        this.rank = rank;
        this.cID = cID;
        this.number = number;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

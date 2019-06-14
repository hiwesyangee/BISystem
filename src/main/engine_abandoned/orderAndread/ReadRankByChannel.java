package engine_abandoned.orderAndread;

/**
 * 订阅排行相关数据对象
 */
public class ReadRankByChannel {
    private String rank;
    private String cID;
    private String number;

    public ReadRankByChannel(String rank, String cID, String number) {
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

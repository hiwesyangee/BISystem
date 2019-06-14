package biquery.payrankquery;

public class PayRankByChannel {
    private String rank;
    private String uid;
    private String number;

    public PayRankByChannel(String rank, String uid, String number) {
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

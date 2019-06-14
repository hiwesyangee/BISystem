package biquery.orderrankquery;

public class OrderRank {
    private String rank;
    private String cID;
    private String number;

    public OrderRank(String rank, String cID, String number) {
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

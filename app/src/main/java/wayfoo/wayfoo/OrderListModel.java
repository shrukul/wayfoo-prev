package wayfoo.wayfoo;

/**
 * Created by Axle on 25/03/2016.
 */
public class OrderListModel {
    private String id,oid;
    private String title,total,table,pay;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getOID() {
        return oid;
    }

    public void setOID(String oid) {
        this.oid = oid;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
}

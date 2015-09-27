package sample.jta.cdi;

import java.io.Serializable;
import javax.transaction.TransactionScoped;

@TransactionScoped
public class TransactionScopedBean implements Serializable {

    @Override
    public String toString() {
        return "TransactionScopedBean {hash=" + this.hashCode() + "}";
    }
}

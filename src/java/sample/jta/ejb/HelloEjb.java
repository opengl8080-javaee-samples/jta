package sample.jta.ejb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.transaction.TransactionSynchronizationRegistry;

@Stateless
public class HelloEjb {
    
    @Resource(lookup = "jdbc/mysql_global_1")
    private DataSource ds1;
    
    @Resource(lookup = "jdbc/mysql_global_2")
    private DataSource ds2;
    
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    public void execute() {
        this.insert(this.ds1, "hoge");
        this.insert(this.ds2, "fuga");
        
        System.out.println("txKey = " + tx.getTransactionKey());
    }
    
    private void insert(DataSource ds, String value) {
        try (Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO TEST_TABLE (VALUE) VALUES (?)");
            ) {
            
            ps.setString(1, value);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

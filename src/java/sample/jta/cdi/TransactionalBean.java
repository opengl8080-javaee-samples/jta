package sample.jta.cdi;

import java.sql.Connection;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.Transactional;

@ApplicationScoped
public class TransactionalBean {
    
    @Resource(lookup = "jdbc/mysql_local_1")
    private DataSource ds;
    
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    @Transactional
    public void required() throws Exception {
        try (Connection con = ds.getConnection()) {
            System.out.println("required() : txKey=" + tx.getTransactionKey());
        }
    }
    
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void requreisNew() throws Exception {
        try (Connection con = ds.getConnection()) {
            System.out.println("requreisNew() : txKey=" + tx.getTransactionKey());
        }
    }
}

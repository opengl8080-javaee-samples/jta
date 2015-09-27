package sample.jta.ejb;

import java.sql.Connection;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.transaction.TransactionSynchronizationRegistry;
import sample.jta.cdi.TransactionalBean;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TransactionalEjb {
    
    @Resource(lookup = "jdbc/mysql_local_1")
    private DataSource ds;
    
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    @Inject
    private TransactionalBean bean;
    
    public void execute() throws Exception {
        try (Connection con = ds.getConnection()) {
            System.out.println("TransactionalEjb : txKey=" + tx.getTransactionKey());
            this.bean.requreisNew();
            this.bean.required();
        }
    }
}

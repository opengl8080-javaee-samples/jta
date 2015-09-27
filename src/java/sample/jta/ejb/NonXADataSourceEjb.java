package sample.jta.ejb;

import java.lang.reflect.Field;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.transaction.TransactionSynchronizationRegistry;

@Stateless
public class NonXADataSourceEjb {
    
    @Resource(lookup = "jdbc/mysql_local_1")
    private DataSource ds;
    
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    public void execute() throws Exception {
        try (Connection con = ds.getConnection()) {
            Object key = tx.getTransactionKey();
            System.out.println("txKey = " + key);

            Object nonXAResource = this.getFieldValue(key, "nonXAResource");
            System.out.println("nonXAResource.class = " + nonXAResource.getClass());
        }
    }
    
    public Object getFieldValue(Object obj, String fieldName) throws Exception {
        if (obj == null) return null;
        
        Class<?> clazz = obj.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object result = field.get(obj);
            field.setAccessible(false);
            return result;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}

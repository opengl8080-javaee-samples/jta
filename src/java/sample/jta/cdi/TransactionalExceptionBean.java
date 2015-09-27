package sample.jta.cdi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Supplier;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class TransactionalExceptionBean {
    
    @Resource(lookup = "jdbc/mysql_local_1")
    private DataSource ds;
    
    public void insertAndThrowException() throws Exception {
        this.insert(this.ds, "insertAndThrowException");
        throw new Exception("test exception");
    }
    
    public void insertAndThrowRuntimeException() throws Exception {
        this.insert(this.ds, "insertAndThrowRuntimeException");
        throw new RuntimeException("test runtime exception");
    }

    @Transactional(
        value=Transactional.TxType.REQUIRES_NEW,
        rollbackOn=IOException.class,
        dontRollbackOn=NullPointerException.class
    )
    public void insertAndThrowAnyException(Supplier<? extends Exception> exceptionProducer) throws Exception {
        Exception e = exceptionProducer.get();
        this.insert(this.ds, "insertAndThrowAnyException(" + e.getClass().getSimpleName() + ")");
        throw e;
    }
    
    private void insert(DataSource ds, String value) throws SQLException {
        try (Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO TEST_TABLE (VALUE) VALUES (?)");
            ) {
            
            ps.setString(1, value);
            ps.executeUpdate();
        }
    }
}

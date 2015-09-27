package sample.jta.ejb;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import sample.jta.cdi.TransactionalExceptionBean;

@Stateless
public class TransactionalExceptionEjb {
    
    @Inject
    private TransactionalExceptionBean bean;
    
    public void execute() {
        this.executeQuietly(this.bean::insertAndThrowException);
        this.executeQuietly(this.bean::insertAndThrowRuntimeException);
        
        // RuntimeException
        this.executeQuietly(() -> this.bean.insertAndThrowAnyException(NullPointerException::new));
        this.executeQuietly(() -> this.bean.insertAndThrowAnyException(IllegalStateException::new));
        
        // Exception
        this.executeQuietly(() -> this.bean.insertAndThrowAnyException(NoSuchMethodException::new));
        this.executeQuietly(() -> this.bean.insertAndThrowAnyException(IOException::new));
    }
    
    private void executeQuietly(ThrowableRunnable tr) {
        try {
            tr.execute();
        } catch (Exception ex) {
            System.out.println("[" + ex.getClass().getSimpleName() + "] " + ex.getMessage());
        }
    }
    
    @FunctionalInterface
    private static interface ThrowableRunnable {
        void execute() throws Exception;
    }
}

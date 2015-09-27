package sample.jta.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.TransactionSynchronizationRegistry;
import sample.jta.cdi.TransactionScopedBean;

@Stateless
public class TransactionScopedEjb {
    
    @Inject
    private TransactionScopedBean bean;
    
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    @Inject
    private RequiredEjb requiredEjb;
    
    @Inject
    private RequiresNewEjb requiresNewEjb;
    
    public void execute() {
        System.out.println("[TestTransactionScopedEjb]");
        System.out.println("txKey=" + this.tx.getTransactionKey());
        System.out.println("bean=" + this.bean);
        
        this.requiresNewEjb.execute();
        this.requiredEjb.execute();
    }
}

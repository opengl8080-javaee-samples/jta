package sample.jta.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.transaction.TransactionSynchronizationRegistry;
import sample.jta.cdi.TransactionScopedBean;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RequiredEjb {
    
    @Inject
    private TransactionScopedBean bean;
    
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    public void execute() {
        System.out.println("[RequiredEjb]");
        System.out.println("txKey=" + this.tx.getTransactionKey());
        System.out.println("bean=" + this.bean);
    }
}

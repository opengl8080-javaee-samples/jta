package sample.jta.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.transaction.TransactionSynchronizationRegistry;
import sample.jta.cdi.TransactionScopedBean;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RequiresNewEjb {

    @Inject
    private TransactionScopedBean bean;
    
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    public void execute() {
        System.out.println("[RequiresNewEjb]");
        System.out.println("txKey=" + this.tx.getTransactionKey());
        System.out.println("bean=" + this.bean);
    }
}

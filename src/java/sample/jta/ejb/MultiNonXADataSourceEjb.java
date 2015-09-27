package sample.jta.ejb;

import java.sql.Connection;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class MultiNonXADataSourceEjb {

    @Resource(lookup = "jdbc/mysql_local_1")
    private DataSource ds1;

    @Resource(lookup = "jdbc/mysql_local_2")
    private DataSource ds2;
    
    public void execute() throws Exception {
        try (Connection c1 = ds1.getConnection();
             Connection c2 = ds2.getConnection()) {}
    }
}

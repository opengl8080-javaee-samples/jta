package sample.jta.web;

import java.io.IOException;
import java.lang.reflect.Method;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/*")
public class EjbDispatchServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String path = req.getPathInfo();
            String[] paths = path.split("/");
            String ejbName = paths[1];
            String methodName = paths[2];
            
            System.out.println("ejb = " + ejbName + ", method = " + methodName);
            
            Object ejb = new InitialContext().lookup("java:global/jta/" + ejbName);
            Method method = ejb.getClass().getMethod(methodName);
            
            method.invoke(ejb);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

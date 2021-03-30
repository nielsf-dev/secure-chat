package org.nelis.securechat.service.blocking.servlet;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class TxFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(TxFilter.class);

    private SessionFactory sessionFactory;

    public TxFilter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Transaction tx = null;

        try {
            logger.trace("Starting transaction..");
            tx = sessionFactory.getCurrentSession().beginTransaction();
            chain.doFilter(request, response);

            tx.commit();
            logger.trace("Committed!");
        }
        catch(Exception ex){
            if(tx != null)
                tx.rollback();

            String errMsg = "Error tijdens servlet execution";
            logger.error(errMsg, ex);
            throw new ServletException(errMsg, ex);
        }
    }

    @Override
    public void destroy() {
        sessionFactory.close();
    }
}

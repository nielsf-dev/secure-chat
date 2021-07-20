package org.nelis.securechat.service.blocking.servlet;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;

public class TxFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(TxFilter.class);

    private SessionFactory sessionFactory;

    private static Object locker = new Object();


    public TxFilter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public synchronized void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Transaction tx = null;

        try {
            logger.info("In the filter for request: " + ((HttpServletRequest)request).getRequestURI());

            logger.trace("Starting transaction..");
            tx = sessionFactory.getCurrentSession().beginTransaction();
            chain.doFilter(request, response);

            logger.trace("Committing transaction..");
            tx.commit();
            sessionFactory.getCurrentSession().close();
            logger.info("Transaction committed");
        }
        catch(Exception ex){
            String errMsg = "Error tijdens servlet execution";
            logger.error(errMsg, ex);

            if(tx != null) {
                logger.error("Rolling back transaction..");
                tx.rollback();
                logger.error("Transaction rolled back");
            }

            throw new ServletException(ex);
        }
    }

    @Override
    public void destroy() {
        sessionFactory.close();
    }
}

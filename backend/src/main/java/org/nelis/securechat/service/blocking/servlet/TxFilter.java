package org.nelis.securechat.service.blocking.servlet;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.servlet.*;
import java.io.IOException;

public class TxFilter implements Filter {

    private SessionFactory sessionFactory;

    public TxFilter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("before request");
        Transaction tx = null;

        try {
            tx = sessionFactory.getCurrentSession().beginTransaction();
            chain.doFilter(request, response);
            tx.commit();
        }
        catch(Exception ex){
            if(tx != null)
                tx.rollback();
        }
        System.out.println("after response");
    }

    @Override
    public void destroy() {

    }
}

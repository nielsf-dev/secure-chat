package org.nelis.securechat.service.blocking.servlet;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.IOException;

public abstract class AbstractChatServletCommand implements ChatServletCommand{
    private SessionFactory sessionFactory;

    public AbstractChatServletCommand(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected abstract String doGetResponse(String request) throws Exception;

    @Override
    public String getResponse(String request) throws IOException {
        Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
        try {
            String response = doGetResponse(request);
            tx.commit();
            return response;
        }
        catch(Exception ex){
            tx.rollback();
            throw new IOException(ex);
        }
    }
}

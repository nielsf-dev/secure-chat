package org.nelis.securechat.service.blocking.servlet;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.IOException;

public abstract class AbstractChatServletCommand implements ChatServletCommand{
    private SessionFactory sessionFactory;

    public AbstractChatServletCommand(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected abstract String doGetResponse(String request) throws IOException;

    @Override
    public String getResponse(String request) throws IOException {
        Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
        String response = doGetResponse(request);
        tx.commit();
        return response;
    }
}

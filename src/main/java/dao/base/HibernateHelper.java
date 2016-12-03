package dao.base;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.LoggerUtil;

/**
 * Created by Willow on 16/11/22.
 * hibernate 3.0原生使用获取session的方法
 */
public class HibernateHelper {
    public static final SessionFactory SESSION_FACTORY;
    public static final ThreadLocal session = new ThreadLocal();
    static {
        try {
            SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            LoggerUtil.error(ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session currentSession() throws HibernateException {
        Session s = (Session) session.get();
        if (null == s) {
            s = SESSION_FACTORY.openSession();
            session.set(s);
        }
        return s;
    }

    public static void closeSession() throws HibernateException {
        Session s = (Session) session.get();
        if (null != s) {
            s.close();
        }
        session.set(null);
    }
}

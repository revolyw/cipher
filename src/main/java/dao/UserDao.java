package dao;

import model.User;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.util.List;

/**
 * Created by Willow on 16/11/21.
 */
public class UserDao {
    public User find(String userName) {
        DetachedCriteria dc = DetachedCriteria.forClass(User.class);
        dc.add(Restrictions.eq("userName", userName));
        List users = new HibernateTemplate().findByCriteria(dc);
        if (null != users && 0 != users.size())
            return (User) users.get(0);
        return null;
    }
}

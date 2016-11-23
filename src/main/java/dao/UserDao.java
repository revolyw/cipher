package dao;

import dao.base.BaseDao;
import dao.base.BaseDaoSupport;
import model.User;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Created by Willow on 16/11/21.
 */
public class UserDao extends BaseDao<User> implements BaseDaoSupport<User> {


    @Override
    public User findByDetachedCritia(DetachedCriteria dc) {
        User user = find(dc);
        return user;
    }
}

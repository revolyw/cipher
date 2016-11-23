package dao.base;

import org.hibernate.criterion.DetachedCriteria;

/**
 * Created by Willow on 16/11/22.
 */
public interface BaseDaoSupport<T> {
    T findByDetachedCritia(DetachedCriteria dc);
}

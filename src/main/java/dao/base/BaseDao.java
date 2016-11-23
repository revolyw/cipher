package dao.base;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by Willow on 16/11/22.
 */
public class BaseDao<T> extends HibernateDaoSupport implements CrudRepository<T, Integer> {
    public T find(DetachedCriteria dc) {
        List<T> list = (List<T>) getHibernateTemplate().findByCriteria(dc);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    @Override
    public <S extends T> S save(S s) {
        return null;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public T findOne(Integer integer) {
        return null;
    }

    @Override
    public boolean exists(Integer integer) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Iterable<T> findAll(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void delete(T t) {

    }

    @Override
    public void delete(Iterable<? extends T> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}

package dao.base;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Willow on 16/11/22.
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
    private static final Logger log = Logger.getLogger(BaseDaoImpl.class);

    /**
     * 获取泛型的实际类型
     *
     * @return
     */
    private Class<T> getGenericClass() {
        //1得到该泛型类的子类对象的Class对象
        Class cls = this.getClass();
        //2得到子类对象的泛型父类类型（也就是BaseDaoImpl<T>）
        ParameterizedType type = (ParameterizedType) cls.getGenericSuperclass();
        Class<T> actualClass = (Class<T>) type.getActualTypeArguments()[0];
        return actualClass;
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
        DetachedCriteria dc = DetachedCriteria.forClass(getGenericClass());
        dc.add(Restrictions.eq("id", integer));
        List<T> list = (List<T>) getHibernateTemplate().findByCriteria(dc);
        return (null != list && list.size() > 0) ? list.get(0) : null;
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

    @Override
    public T findByField(String field, String value) {
        DetachedCriteria dc = DetachedCriteria.forClass(getGenericClass());
        dc.add(Restrictions.eq(field, value));
        List<T> list = (List<T>) getHibernateTemplate().findByCriteria(dc);
        return (null != list && list.size() > 0) ? list.get(0) : null;
    }
}

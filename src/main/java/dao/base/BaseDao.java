package dao.base;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Willow on 16/11/22.
 */
public interface BaseDao<T> extends CrudRepository<T, Integer> {
    /**
     * 根据模型字段查询对象
     * @param field
     * @param value
     * @return
     */
    T findByField(String field, String value);
}

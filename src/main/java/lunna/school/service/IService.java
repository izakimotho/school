package lunna.school.service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 30. Jun 2021 6:09 PM
 **/

public interface IService<T> {
    List<T> listAll();

    T getById(UUID id);

    T saveOrUpdate(T t);

    Long count();

    Long count(UUID id);

    void delete(T t);

}

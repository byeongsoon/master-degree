package demo.orderservice.service;

import demo.orderservice.ifs.CrudInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseService<Res, Req, Entity> implements CrudInterface<Res, Req> {

    @Autowired(required = false)
    protected JpaRepository<Entity,Long> baseRepository;

}

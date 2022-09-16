package demo.orderservice.controller;

import demo.orderservice.ifs.CrudInterface;
import demo.orderservice.model.network.Header;
import demo.orderservice.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
public abstract class CrudController<Res,Req,Entity> implements CrudInterface<Res,Req> {

    @Autowired(required = false)
    protected BaseService<Res,Req,Entity> baseService;

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        return baseService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable Long id) {
        return baseService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(@RequestBody Header<Req> request) {
        return baseService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return baseService.delete(id);
    }

    @Override
    @GetMapping("")
    public Header<List<Res>> search(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10) Pageable pageable) {
        return baseService.search(pageable);
    }

}

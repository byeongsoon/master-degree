package demo.orderservice.controller;

import demo.orderservice.ifs.CrudInterface;
import demo.orderservice.model.network.Header;
import demo.orderservice.model.network.request.ItemApiRequest;
import demo.orderservice.model.network.response.ItemApiResponse;
import demo.orderservice.service.ItemApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order/item")
public class ItemApiController implements CrudInterface<ItemApiResponse, ItemApiRequest> {

    @Autowired
    private ItemApiLogicService itemApiLogicService;

    @Override
    @PostMapping("")
    public Header<ItemApiResponse> create(@RequestBody Header<ItemApiRequest> request) {
        return itemApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<ItemApiResponse> read(@PathVariable Long id) {
        return itemApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<ItemApiResponse> update(@RequestBody Header<ItemApiRequest> request) {
        return itemApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return itemApiLogicService.delete(id);
    }

    @Override
    @GetMapping("")
    public Header<List<ItemApiResponse>> search(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10) Pageable pageable) {
        return itemApiLogicService.search(pageable);
    }

}

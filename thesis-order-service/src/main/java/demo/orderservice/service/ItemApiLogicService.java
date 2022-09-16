package demo.orderservice.service;

import demo.orderservice.ifs.CrudInterface;
import demo.orderservice.model.entity.Item;
import demo.orderservice.model.network.Header;
import demo.orderservice.model.network.Pagination;
import demo.orderservice.model.network.request.ItemApiRequest;
import demo.orderservice.model.network.response.ItemApiResponse;
import demo.orderservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemApiLogicService implements CrudInterface<ItemApiResponse, ItemApiRequest> {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();

        Item item = Item.builder()
                .status(itemApiRequest.getStatus())
                .name(itemApiRequest.getName())
                .content(itemApiRequest.getContent())
                .price(itemApiRequest.getPrice())
                .brandName(itemApiRequest.getBrandName())
                .build();

        return Header.OK(response(itemRepository.save(item)));
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        return itemRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();

        return itemRepository.findById(itemApiRequest.getId())
                .map(item -> item
                        .setStatus(itemApiRequest.getStatus())
                        .setName(itemApiRequest.getName())
                        .setContent(itemApiRequest.getContent())
                        .setPrice(itemApiRequest.getPrice())
                        .setBrandName(itemApiRequest.getBrandName())
                )
                .map(newItem -> itemRepository.save(newItem))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("업데이트 실패"));
    }

    @Override
    public Header delete(Long id) {
        return itemRepository.findById(id)
                .map(item -> {
                    itemRepository.delete(item);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("삭제 실패"));
    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {
        Page<Item> items = itemRepository.findAll(pageable);

        List<ItemApiResponse> itemApiResponseList = items.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPage(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .currentPage(items.getNumber())
                .currentElements(items.getNumberOfElements())
                .build();

        return Header.OK(itemApiResponseList,pagination);
    }

    public ItemApiResponse response(Item item) {
        return ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

}

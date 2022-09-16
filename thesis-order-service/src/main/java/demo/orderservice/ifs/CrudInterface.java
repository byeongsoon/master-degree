package demo.orderservice.ifs;

import demo.orderservice.model.network.Header;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudInterface<Res, Req> {

    Header<Res> create(Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> request);

    Header delete(Long id);

    Header<List<Res>> search(Pageable pageable);

}

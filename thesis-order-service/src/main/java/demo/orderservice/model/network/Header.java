package demo.orderservice.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Header<T> {

    private LocalDateTime transactionTime;

    private String description;

    private T data;

    private Pagination pagination;

    public static <T> Header<T> OK() {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .description("OK")
                .build();
    }

    public static <T> Header<T> OK(T data) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .description("OK with data")
                .data(data)
                .build();
    }

    public static <T> Header<T> OK(T data, Pagination pagination) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .description("OK with data and pagination")
                .data(data)
                .pagination(pagination)
                .build();
    }

    public static <T> Header<T> ERROR(String description) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .description(description)
                .build();
    }

}

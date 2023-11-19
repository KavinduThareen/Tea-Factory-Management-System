package lk.ijse.teaFactory.dto;

import lk.ijse.teaFactory.dto.tm.CusOrderTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaseOrderDto {
    private String orderId;
    private LocalDate date;
    private String customerId;
    private List<CusOrderTm> cartTmList = new ArrayList<>();
}

package lk.ijse.teaFactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CusOrderDto {
    private String id;
    private String cId;
    private String catagary;
    private String weigth;
    private String date;
    private String descreption;
    private double payment;
    private String isCompleted;
}

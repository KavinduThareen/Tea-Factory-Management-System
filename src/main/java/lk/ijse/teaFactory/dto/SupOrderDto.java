package lk.ijse.teaFactory.dto;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupOrderDto {

    private String id;
    private String sId;
    private Date date;
    private String weigth;
    private double  payment;

}

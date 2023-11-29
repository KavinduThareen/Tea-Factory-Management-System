package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class CartTm {
    private String itemId;
    private String cusid;
    private String descreption;
    private String catagary;
    private double weigth;
    private double payment;
    private LocalDate date;
    private JFXButton btnDelete;


}

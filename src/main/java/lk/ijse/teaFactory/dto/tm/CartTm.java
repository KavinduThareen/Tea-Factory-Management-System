package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class CartTm {
    private String itemId;
    private String descreption;
    private String catagary;
    private String weigth;
    private double payment;
    private JFXButton btnDelete;


}

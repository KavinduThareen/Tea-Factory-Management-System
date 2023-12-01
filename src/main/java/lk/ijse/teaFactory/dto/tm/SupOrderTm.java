package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class SupOrderTm {
    private String id;
    private String sId;
    private Date date;
    private String weigth;
    private double payment;
    private JFXButton btnDelete;

}

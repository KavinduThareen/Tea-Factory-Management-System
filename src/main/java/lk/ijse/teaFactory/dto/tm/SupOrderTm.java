package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class SupOrderTm {
    private String id;
    private String sId;
    private String date;
    private String weigth;
    private int payment;
    private JFXButton btnDelete;

    /*

    public SupOrderTm(String id, String sId, String date, String weigth) {

        this.id = id;
        this.sId = sId;
        this.date = date;
        this.weigth = weigth;
    }

     */
}

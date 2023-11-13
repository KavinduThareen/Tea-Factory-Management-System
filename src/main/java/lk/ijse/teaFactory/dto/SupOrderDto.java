package lk.ijse.teaFactory.dto;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupOrderDto {

    private String id;
    private String sId;
    private String date;
    private String weigth;
    private String isDelete;


    public SupOrderDto(String id, String sId, String date, String weigth) {

        this.id = id;
        this.sId = sId;
        this.date = date;
        this.weigth = weigth;
    }
}

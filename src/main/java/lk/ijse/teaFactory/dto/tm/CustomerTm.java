package lk.ijse.teaFactory.dto.tm;


import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerTm {
    private String cusid;
    private String empid;
    private String cusname;
    private String cusAddress;
    private String cusCantac;
    private JFXButton btnDelete;

}

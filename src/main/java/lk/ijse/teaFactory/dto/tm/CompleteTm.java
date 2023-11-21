package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.IdentityHashMap;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompleteTm {
    private String Id;
    private String catogary;
    private String weigth;
    private String date;
    private JFXButton btnDelete;



}

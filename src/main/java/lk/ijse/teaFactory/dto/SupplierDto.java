package lk.ijse.teaFactory.dto;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierDto {
    private String id;
    private String name;
    private String address;
    private String contac;

}

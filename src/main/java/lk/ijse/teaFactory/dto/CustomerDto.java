package lk.ijse.teaFactory.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {

    private String cusid;
    private String empid;
    private String cusname;
    private String cusAddress;
    private String cusCantac;
 //   private String isCompleted;
}

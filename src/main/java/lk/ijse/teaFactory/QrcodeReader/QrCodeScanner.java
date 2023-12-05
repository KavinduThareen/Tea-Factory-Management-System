package lk.ijse.teaFactory.QrcodeReader;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.scene.control.Alert;
import lk.ijse.teaFactory.dto.NotificationAnimation;
import lk.ijse.teaFactory.model.EmpAttendensModel;


import javax.swing.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class QrCodeScanner {


    public static ArrayList<String> scannedValues = new ArrayList<>();
    //String[][] details = JDBC.getDetails("ticket",4);


    public static void qrCodeScan() {
        EmpAttendensModel empAttendensModel = new EmpAttendensModel();
        String[] stringArray = new String[5];

        Webcam webcam = Webcam.getDefault();   //Generate Webcam Object
        webcam.setViewSize(new Dimension(320,240));
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setMirrored(false);
        JFrame jFrame = new JFrame();
        jFrame.add(webcamPanel);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(false);

        do {
            try {
                BufferedImage image = webcam.getImage();
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);
                if(result.getText() != null) {
                    System.out.println(result.getText());
                  //  int value = Integer.parseInt(result.getText());
                    String value = result.getText();

                    LocalDate date = LocalDate.now();
                    LocalTime time = LocalTime.now();

                    if (!scannedValues.contains(value)) {
                        scannedValues.add(value);
                    } else {
                        scannedValues.remove(String.valueOf(value));
                    }

                  boolean a = empAttendensModel.markAttendent(value,date,time);
                   if (a){
                        System.out.println("qr working");
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
     //               jFrame.setVisible(false);
      //              jFrame.dispose();
      //              webcam.close();
       //             break;
                }

            }catch (NotFoundException e ) {
               // pass
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } while(true);
    }
}
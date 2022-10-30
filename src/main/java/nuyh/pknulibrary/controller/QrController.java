package nuyh.pknulibrary.controller;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.google.zxing.BarcodeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class QrController {
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("text", "hihi");

        return "home";
    }

    @GetMapping("qr")
    public Object createQr(@ModelAttribute("studentId") String studentId) throws WriterException, IOException {
        int width=200;
        int height=200;

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes(3);
        String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        StringBuilder sb=new StringBuilder();
        sb.append("PK     0");
        sb.append(studentId);
        sb.append(time);
        //7자리의 정보?? 뭔지 모르겠음
        sb.append(3694133);

        BitMatrix matrix=new MultiFormatWriter().encode(sb.toString(), BarcodeFormat.QR_CODE, width, height);

        try(ByteArrayOutputStream out=new ByteArrayOutputStream();){
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(out.toByteArray());
        }
    }
}

package br.com.verbum.eccdiocesano.rest.controller;

import br.com.verbum.eccdiocesano.domain.services.CasalService;
import br.com.verbum.eccdiocesano.domain.services.PdfService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/v1/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;
    @Autowired
    private CasalService casalService;

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generatePdf() throws IOException {
        byte[] pdfBytes = pdfService.generatePdf();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "document.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @GetMapping("/first-step/{paroquiaId}")
    public byte[] allFirstStep(@PathVariable UUID paroquiaId) throws IOException {
        return casalService.findAllPrimeiraEtapaAndParoquia(paroquiaId);
    }
}
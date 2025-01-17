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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

    @GetMapping("/first-step")
    public ResponseEntity<byte[]> allFirstStep() throws IOException {
        byte[] pdfBytes = casalService.findAllPrimeiraEtapa();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "casais-para-segunda-etapa.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @GetMapping("/second-step")
    public ResponseEntity<byte[]> allSecondStep() throws IOException {
        byte[] pdfBytes = casalService.findAllSegundaEtapa();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "casais-para-terceira-etapa.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @GetMapping("/third-step")
    public ResponseEntity<byte[]> allThirdStep() throws IOException {
        byte[] pdfBytes = casalService.findAllTerceiraEtapa();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "casais-com-terceira-etapa.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @GetMapping("/inactive-couples")
    public ResponseEntity<byte[]> allInactiveCouples() throws IOException {
        byte[] pdfBytes = casalService.getInactiveCouples();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "casais-inativos.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @GetMapping("/active-couples")
    public ResponseEntity<byte[]> allActiveCouples() throws IOException {
        byte[] pdfBytes = casalService.getActiveCouples();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "casais-ativos.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @GetMapping("/without-sacrament")
    public ResponseEntity<byte[]> allWithoutSacrament() throws IOException {
        byte[] pdfBytes = casalService.findAllCouplesWithoutMatrimonySacrament();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "casais-sem-sacramento-do-matrimonio.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
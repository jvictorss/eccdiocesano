package br.com.verbum.eccdiocesano.domain.services;

import br.com.verbum.eccdiocesano.rest.dtos.CasalResponseDto;
import br.com.verbum.eccdiocesano.rest.dtos.CountCasaisDto;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static br.com.verbum.eccdiocesano.domain.utils.FormatUtils.formatDate;
import static br.com.verbum.eccdiocesano.domain.utils.FormatUtils.formatPhoneNumber;

@Service
public class PdfService {

    public byte[] generatePdf() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterEventHandler());

        cabecalhoComum(document);

        LineSeparator headerLineSeparator = new LineSeparator(new SolidLine());
        headerLineSeparator.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(headerLineSeparator);

        document.add(new Paragraph("Hello, this is a PDF document."));

        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generateCouplesFormPdf(List<CasalResponseDto> casais, CountCasaisDto countCasais, String reportName) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterEventHandler());
//        pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, cabecalhoComum(document));

        cabecalhoComum(document);

        document.add(new Paragraph(reportName.concat("\nParóquia Nossa Senhora do Rosário "))
            .setFontSize(9)
            .setTextAlignment(TextAlignment.CENTER));

        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 2, 2, 2, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell(new Cell().add(new Paragraph("Casal").setFontSize(8)).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Fone Ele / Fone Ela").setFontSize(8)).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Primeira Etapa").setFontSize(8)).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Segunda Etapa").setFontSize(8)).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Terceira Etapa").setFontSize(8)).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("O casal está").setFontSize(8)).setBold().setTextAlignment(TextAlignment.CENTER));

        for (CasalResponseDto casal : casais) {
            String casalNames = casal.getApelidoEle() + " / " + casal.getApelidoEla();
            String phones = formatPhoneNumber(casal.getTelefoneEle().trim()) + "\n" + formatPhoneNumber(casal.getTelefoneEla().trim());
            String dataPrimeiraEtapa = formatDate(casal.getDataPrimeiraEtapa());
            String dataSegundaEtapa = formatDate(casal.getDataSegundaEtapa());
            String dataTerceiraEtapa = formatDate(casal.getDataTerceiraEtapa());
            String status = casal.isActive() ? "Ativo" : "Inativo";

            table.addCell(new Cell().add(new Paragraph(casalNames).setFontSize(8))
                    .setTextAlignment(TextAlignment.CENTER))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph(phones).setFontSize(8))
                    .setTextAlignment(TextAlignment.CENTER))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph(dataPrimeiraEtapa).setFontSize(8))
                    .setTextAlignment(TextAlignment.CENTER))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph(dataSegundaEtapa).setFontSize(8))
                    .setTextAlignment(TextAlignment.CENTER))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph(dataTerceiraEtapa).setFontSize(8))
                    .setTextAlignment(TextAlignment.CENTER))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph(status).setFontSize(8))
                    .setTextAlignment(TextAlignment.CENTER))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setBorder(Border.NO_BORDER);
        }

        document.add(table);

        StringBuilder paragraphContent = new StringBuilder("Total de casais: \n");

        if (!countCasais.getCountPrimeiraEtapa().isBlank()) {
            paragraphContent.append("Primeira Etapa: ").append(countCasais.getCountPrimeiraEtapa()).append(" \n");
        }
        if (!countCasais.getCountSegundaEtapa().isBlank()) {
            paragraphContent.append("Segunda Etapa: ").append(countCasais.getCountSegundaEtapa()).append(" \n");
        }
        if (!countCasais.getCountTerceiraEtapa().isBlank()) {
            paragraphContent.append("Terceira Etapa: ").append(countCasais.getCountTerceiraEtapa()).append(" \n");
        }
        if (!countCasais.getCountSemSacramento().isBlank()) {
            paragraphContent.append("Sem Sacramento: ").append(countCasais.getCountSemSacramento()).append(" \n");
        }
        if (!countCasais.getTotalActiveCouples().isBlank()) {
            paragraphContent.append("Ativos: ").append(countCasais.getTotalActiveCouples()).append(" \n");
        }
        if (!countCasais.getTotalInactiveCouples().isBlank()) {
            paragraphContent.append("Inativos: ").append(countCasais.getTotalInactiveCouples()).append(" \n");
        }

        document.add(new Paragraph(paragraphContent.toString().trim())
            .setFontSize(9)
            .setTextAlignment(TextAlignment.RIGHT)
            .setMarginTop(10));

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generateCouplesWithoutSacrament(List<CasalResponseDto> casais, CountCasaisDto countCasais) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterEventHandler());

        cabecalhoComum(document);

        document.add(new Paragraph("Relatório de Casais sem Sacramento do Matrimônio\nParóquia Nossa Senhora do Rosário")
                .setFontSize(9)
                .setTextAlignment(TextAlignment.CENTER));

        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 2, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell(new Cell().add(new Paragraph("Casal").setFontSize(8)).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Fone Ele / Fone Ela").setFontSize(8)).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Casamento Civil").setFontSize(8)).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("O casal está").setFontSize(8)).setBold().setTextAlignment(TextAlignment.CENTER));

        for (CasalResponseDto casal : casais) {
            String casalNames = casal.getApelidoEle() + " / " + casal.getApelidoEla();
            String phones = formatPhoneNumber(casal.getTelefoneEle().trim()) + "\n" + formatPhoneNumber(casal.getTelefoneEla().trim());
            String casamentoCivil = formatDate(casal.getCasamentoCivil());
            String status = casal.isActive() ? "Ativo" : "Inativo";

            table.addCell(new Cell().add(new Paragraph(casalNames).setFontSize(8))
                            .setTextAlignment(TextAlignment.CENTER))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph(phones).setFontSize(8))
                            .setTextAlignment(TextAlignment.CENTER))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph(casamentoCivil).setFontSize(8))
                            .setTextAlignment(TextAlignment.CENTER))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setBorder(Border.NO_BORDER);
            table.addCell(new Cell().add(new Paragraph(status).setFontSize(8))
                            .setTextAlignment(TextAlignment.CENTER))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setBorder(Border.NO_BORDER);
        }

        document.add(table);

        document.add(new Paragraph("Total de casais: " + countCasais.getCountSemSacramento())
                .setFontSize(9)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(10));

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    private static void cabecalhoComum(Document document) throws IOException {
        ClassPathResource imageResource = new ClassPathResource("static/images/ECC.jpg");
        ImageData imageData = ImageDataFactory.create(imageResource.getURL());
        Image image = new Image(imageData);
        image.setHorizontalAlignment(HorizontalAlignment.CENTER);
        image.setWidth(UnitValue.createPercentValue(8));

        document.add(image);
        document.add(new Paragraph("Encontro de Casais com Cristo - ECC\nDiocese de Afogados da Ingazeira - Pernambuco")
                .setBold().setFontSize(9)
                .setTextAlignment(TextAlignment.CENTER));

        LineSeparator headerLineSeparator = new LineSeparator(new SolidLine());
        headerLineSeparator.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(headerLineSeparator);
    }


    private static class FooterEventHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            Document document = new Document(pdfDoc);

            LineSeparator footerLineSeparator = new LineSeparator(new SolidLine());
            footerLineSeparator.setHorizontalAlignment(HorizontalAlignment.CENTER);
            footerLineSeparator.setWidth(UnitValue.createPercentValue(100));
            document.showTextAligned(new Paragraph().add(footerLineSeparator), 297.5f, 40, pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0);

            document.showTextAligned(new Paragraph("Emitido em: " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(8), 50, 20, pdfDoc.getPageNumber(page), TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);

            document.showTextAligned(new Paragraph("Verbum Software - Serra Talhada-PE")
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(8), 545, 20, pdfDoc.getPageNumber(page), TextAlignment.RIGHT, VerticalAlignment.BOTTOM, 0);
        }
    }
}
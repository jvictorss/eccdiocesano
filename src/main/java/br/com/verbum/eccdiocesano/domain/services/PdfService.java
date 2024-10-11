package br.com.verbum.eccdiocesano.domain.services;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public byte[] generatePdf() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterEventHandler());

        ClassPathResource imageResource = new ClassPathResource("static/images/ECC.jpg");
        ImageData imageData = ImageDataFactory.create(imageResource.getURL());
        Image image = new Image(imageData);
        image.setHorizontalAlignment(HorizontalAlignment.CENTER);
        image.setWidth(UnitValue.createPercentValue(8)); // Resize the image to 8% of the page width

        document.add(image);
        document.add(new Paragraph("Encontro de Casais com Cristo - ECC\nDiocese de Afogados da Ingazeira - Pernambuco")
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

        LineSeparator headerLineSeparator = new LineSeparator(new SolidLine());
        headerLineSeparator.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(headerLineSeparator);

        document.add(new Paragraph("Hello, this is a PDF document."));

        document.close();

        return byteArrayOutputStream.toByteArray();
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

            document.showTextAligned(new Paragraph("Verbum Software - Serra Talhada-PE")
                    .setTextAlignment(TextAlignment.CENTER), 297.5f, 20, pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0);
        }
    }
}
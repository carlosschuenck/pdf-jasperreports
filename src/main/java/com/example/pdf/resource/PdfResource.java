package com.example.pdf.resource;

import com.example.pdf.service.PdfService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/pdf")
public class PdfResource {

    @Autowired
    private PdfService service;

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] generatePDF() throws FileNotFoundException, JRException {
        return service.generatePdf();
    }
}

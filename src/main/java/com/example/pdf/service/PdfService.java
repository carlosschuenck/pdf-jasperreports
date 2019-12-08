package com.example.pdf.service;

import com.example.pdf.domain.Form;
import com.example.pdf.domain.PDF;
import com.example.pdf.domain.QuestionAnswer;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class PdfService {

    public byte[] generatePdf() throws JRException {
        List<QuestionAnswer> questionAnswer = new ArrayList<>();
        questionAnswer.add(QuestionAnswer.builder().question("Pergunta 1").answer("Resposta 1").build());
        questionAnswer.add(QuestionAnswer.builder().question("Pergunta 2").answer("Resposta 2").build());
        questionAnswer.add(QuestionAnswer.builder().question("Pergunta 3").answer("Resposta 3").build());

        List<Form> formList = new ArrayList<>();
        formList.add(Form.builder().formTitle("1° Formulário").questionAnswerList(questionAnswer).build());
        formList.add(Form.builder().formTitle("2° Formulário").questionAnswerList(questionAnswer).build());


        PDF pdf1 = PDF.builder().incidentType("Tipo Incidente 1").formList(formList).build();
        PDF pdf2 = PDF.builder().incidentType("Tipo Incidente 2").formList(formList).build();
        PDF pdf3 = PDF.builder().incidentType("Tipo Incidente 3").formList(formList).build();

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Arrays.asList(pdf1,pdf2,pdf3));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Formulário Dinâmico");

        // Pega o arquivo .jasper localizado em resources
        InputStream jasperStream = this.getClass().getResourceAsStream("/reports/dynamicForm.jasper");

        // Cria o objeto JaperReport com o Stream do arquivo jasper
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        // Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no caso uma conexão ao banco de dados
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}

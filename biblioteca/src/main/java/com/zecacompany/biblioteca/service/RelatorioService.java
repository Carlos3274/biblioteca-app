package com.zecacompany.biblioteca.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.zecacompany.biblioteca.domain.Emprestimo;
import com.zecacompany.biblioteca.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class RelatorioService {

    private final EmprestimoRepository emprestimoRepository;

    public RelatorioService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    public byte[] gerarRelatorioEmprestimosPDF() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        document.add(new Paragraph("Relatório de Empréstimos").setFontSize(20));

        Table table = new Table(new float[]{3, 3, 3, 3});
        table.addCell("Nome do Usuário");
        table.addCell("Título do Livro");
        table.addCell("Data do Empréstimo");
        table.addCell("Data de Devolução");

        for (Emprestimo emprestimo : emprestimos) {
            table.addCell(emprestimo.getUsuario().getNome());
            table.addCell(emprestimo.getLivro().getTitulo());
            table.addCell(emprestimo.getDataEmprestimo().toString());
            table.addCell(emprestimo.getDataDevolucao() != null ? emprestimo.getDataDevolucao().toString() : "Ainda não devolvido");
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }
}


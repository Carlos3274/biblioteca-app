package com.zecacompany.biblioteca.controllers;




import com.zecacompany.biblioteca.dto.RelatorioEmprestimoDTO;
import com.zecacompany.biblioteca.service.EmprestimoService;
import com.zecacompany.biblioteca.service.RelatorioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final EmprestimoService emprestimoService;
    private final RelatorioService relatorioService;

    public RelatorioController(EmprestimoService emprestimoService, RelatorioService relatorioService) {
        this.emprestimoService = emprestimoService;
        this.relatorioService = relatorioService;
    }

    @GetMapping("/emprestimos")
    public ResponseEntity<List<RelatorioEmprestimoDTO>> gerarRelatorioEmprestimos() {
        List<RelatorioEmprestimoDTO> relatorio = emprestimoService.gerarRelatorioEmprestimos();
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/emprestimos/pdf")
    public ResponseEntity<byte[]> gerarRelatorioEmprestimosPDF() {
        byte[] pdfBytes = relatorioService.gerarRelatorioEmprestimosPDF();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "relatorio_emprestimos.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}



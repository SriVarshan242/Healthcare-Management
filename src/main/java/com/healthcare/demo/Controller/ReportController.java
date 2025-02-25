package com.healthcare.demo.Controller;

import com.healthcare.demo.Model.Report;
import com.healthcare.demo.Service.ReportService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // GET /reports?page=0&size=10&sortBy=id
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir)  {
        Page<Report> reportPage = reportService.getAllReports(page, size, sortBy,sortDir);
        return ResponseEntity.ok(reportPage.getContent());
    }

    // GET /reports/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /reports
    @PostMapping
    public ResponseEntity<Report> createOrUpdateReport(@RequestBody Report report) {
        Report savedReport = reportService.saveReport(report);
        return ResponseEntity.ok(savedReport);
    }

    // PUT /reports/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report report) {
        if (!reportService.getReportById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        report.setId(id);
        Report updatedReport = reportService.saveReport(report);
        return ResponseEntity.ok(updatedReport);
    }

    // DELETE /reports/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok("{ \"message\": \"Report deleted successfully\" }");
    }
}

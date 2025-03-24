package com.healthcare.demo.Controller;

import com.healthcare.demo.Model.Report;
import com.healthcare.demo.Service.ReportService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@Tag(name = "Report Controller", description = "APIs for managing reports in the healthcare system")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Get all reports", description = "Fetch paginated list of reports with sorting options")
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports(
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number, default is 0") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size, default is 10") int size,
            @RequestParam(defaultValue = "id") @Parameter(description = "Sort by field, default is id") String sortBy,
            @RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction, either asc or desc") String sortDir) {
        Page<Report> reportPage = reportService.getAllReports(page, size, sortBy, sortDir);
        return ResponseEntity.ok(reportPage.getContent());
    }

    @Operation(summary = "Get report by ID", description = "Fetch a report by its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(
            @PathVariable @Parameter(description = "ID of the report to fetch") Long id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create or update a report", description = "Create a new report or update an existing one")
    @PostMapping
    public ResponseEntity<Report> createOrUpdateReport(
            @RequestBody @Parameter(description = "Report details for creation or update") Report report) {
        Report savedReport = reportService.saveReport(report);
        return ResponseEntity.ok(savedReport);
    }

    @Operation(summary = "Update a report", description = "Update an existing report's details based on ID")
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(
            @PathVariable @Parameter(description = "ID of the report to update") Long id,
            @RequestBody @Parameter(description = "Updated report details") Report report) {
        if (!reportService.getReportById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        report.setId(id);
        Report updatedReport = reportService.saveReport(report);
        return ResponseEntity.ok(updatedReport);
    }

    @Operation(summary = "Delete a report", description = "Delete a report by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReport(
            @PathVariable @Parameter(description = "ID of the report to delete") Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok("{ \"message\": \"Report deleted successfully\" }");
    }
}

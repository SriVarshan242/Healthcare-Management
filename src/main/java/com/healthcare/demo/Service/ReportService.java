package com.healthcare.demo.Service;

import com.healthcare.demo.Model.*;
import com.healthcare.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {

    private ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // Create or update a report
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    // Retrieve a report by ID
    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    // Retrieve all reports with pagination and sorting
    public Page<Report> getAllReports(int page, int size, String sortBy) {
        return reportRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    // Delete a report by ID
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }
}

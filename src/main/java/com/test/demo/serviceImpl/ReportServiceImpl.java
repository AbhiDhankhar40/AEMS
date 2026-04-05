package com.test.demo.serviceImpl;

import com.test.demo.model.Events;
import com.test.demo.service.ReportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public byte[] generateEventsExcel(List<Events> events) {
        String[] HEADERS = {
            "ID", "Title", "Organiser", "Department", "Date", "Time", "Venue",
            "Objective", "Speakers", "Participants", "Programme Schedule",
            "Highlights", "Purpose", "Outcome", "Feedback", "Budget",
            "Conclusion", "Acknowledgement", "Status"
        };
        String SHEET_NAME = "Events";

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }

            // Data
            int rowIdx = 1;
            for (Events event : events) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(event.getId());
                row.createCell(1).setCellValue(event.getTitle());
                row.createCell(2).setCellValue(event.getOrganiser());
                row.createCell(3).setCellValue(event.getDepartment());
                row.createCell(4).setCellValue(event.getDate() != null ? event.getDate().toString() : "");
                row.createCell(5).setCellValue(event.getTime() != null ? event.getTime().toString() : "");
                row.createCell(6).setCellValue(event.getVenue());
                row.createCell(7).setCellValue(event.getObjective());
                row.createCell(8).setCellValue(event.getSpeakers());
                row.createCell(9).setCellValue(event.getParticipants());
                row.createCell(10).setCellValue(event.getProgrammeSchedule());
                row.createCell(11).setCellValue(event.getHighLights());
                row.createCell(12).setCellValue(event.getPurpose());
                row.createCell(13).setCellValue(event.getOutcome());
                row.createCell(14).setCellValue(event.getFeedback());
                row.createCell(15).setCellValue(event.getBudget() != null ? event.getBudget() : 0.0);
                row.createCell(16).setCellValue(event.getConclusion());
                row.createCell(17).setCellValue(event.getAcknowledgement());
                row.createCell(18).setCellValue(event.getStatus());
            }

            // Auto-size columns for better readability
            for (int col = 0; col < HEADERS.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            // Consider more specific exception handling or logging
            throw new RuntimeException("Failed to generate Excel file: " + e.getMessage(), e);
        }
    }
}
package com.test.demo.service;

import com.test.demo.model.Events;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface ReportService {
    ByteArrayInputStream generateEventsExcel(List<Events> events);
}
package com.test.demo.service;

import com.test.demo.model.Events;
import java.util.List;

public interface ReportService {
    byte[] generateEventsExcel(List<Events> events);
}
package com.worsley.service;

import com.worsley.dao.DatabaseConnection;
import com.worsley.dao.MySqlTrackRepo;
import com.worsley.dao.exception.RepositoryException;
import com.worsley.dto.QuickListensSummary;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrackServiceIntegrationTest {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Chinook?enabledTLSProtocols=TLSv1.2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    @Test
    void runQuery_shouldReturnSummaryInExpectedFormat() throws RepositoryException {
        TrackService service = new TrackService(new MySqlTrackRepo(new DatabaseConnection(JDBC_URL, USERNAME, PASSWORD)));
        QuickListensSummary result = service.getQuickListens();
        assertEquals(20, result.tracks().size());
        assertTrue(0 < result.cost());
        assertTrue(result.totalRuntime().matches("\\d{2}:\\d{2}"));
    }
}
package com.worsley.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.worsley.dao.exception.RepositoryException;
import com.worsley.dto.Track;
import org.junit.jupiter.api.Test;
import java.util.List;

class TrackRepoIntegrationTest {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Chinook?enabledTLSProtocols=TLSv1.2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final int TRACK_LENGTH = 120000;
    private static final int MAX_NO_OF_TRACKS = 20;

    @Test
    void runQuery_shouldReturnTracks() throws RepositoryException {
        MySqlTrackRepo repo = new MySqlTrackRepo(new DatabaseConnection(JDBC_URL, USERNAME, PASSWORD));
        List<Track> results = repo.getTracksWithRuntimeLessThan(TRACK_LENGTH, MAX_NO_OF_TRACKS);
        assertEquals(20, results.size());
        results = repo.getTracksWithRuntimeLessThan(TRACK_LENGTH, 31);
        assertEquals(31, results.size());
    }

    @Test
    void runQuery_databaseConnectionError_shouldThrowException()
    {
        final String invalidUrl = "invalid";
        MySqlTrackRepo repo = new MySqlTrackRepo(new DatabaseConnection(invalidUrl, USERNAME, PASSWORD));
        assertThrows(RepositoryException.class,
                () -> repo.getTracksWithRuntimeLessThan(TRACK_LENGTH, MAX_NO_OF_TRACKS));
    }
}
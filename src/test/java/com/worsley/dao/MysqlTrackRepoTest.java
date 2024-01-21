package com.worsley.dao;

import com.worsley.dao.exception.RepositoryException;
import com.worsley.dto.Track;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * NOTE: I've provided this as an example of how you could unit test the DAO layer. Code at the "edge" of your codebase
 * is more difficult to test as it calls out to external dependencies. Some argue it is better to use Integration tests
 * as the main way to test code at the edge of the codebase. You'll notice there is heavy mocking here which is one of
 * the reasons you might prefer integration tests at this layer.
 * I've not written all the tests you potentially could, but I've written enough to demonstrate the approach.
 */
@ExtendWith(MockitoExtension.class)
class MysqlTrackRepoTest {

    private static final int QUICK_LISTEN_TIME = 120000; // 2 minutes
    private static final int MAX_NO_OF_TRACKS = 2;

    @Mock
    private DatabaseConnection databaseConnection;

    @InjectMocks
    private MySqlTrackRepo subjectUnderTest;

    @Test
    void getTracksWithRuntimeLessThan_dbReturnsLessThanMaxTracks_shouldReturnMaxNoOfTracks() throws RepositoryException, SQLException {
        Track testTrack1 = new Track("Track 1", 1.99f, 100000, "Rock");
        List<Track> expectedTracks = Arrays.asList(testTrack1);
        ResultSet resultSet = givenWeConnectToTheDatabase();
        givenDatabaseReturnLessThanMaxNoOfTracks(resultSet);
        List<Track> results = whenGetTracksWithRuntimeLessThanCalled();
        thenExpectedTracksReturned(expectedTracks, results);
    }

    @Test
    void getTracksWithRuntimeLessThan_dbReturnsMaxTracks_shouldReturnMaxNoOfTracks() throws RepositoryException, SQLException {
        Track testTrack1 = new Track("Track 1", 1.99f, 100000, "Rock");
        Track testTrack2 = new Track("Track 2", 1.99f, 200000, "Pop");
        List<Track> expectedTracks = Arrays.asList(testTrack1, testTrack2);
        ResultSet resultSet = givenWeConnectToTheDatabase();
        givenDatabaseReturnMaxNoOfTracks(resultSet);
        List<Track> results = whenGetTracksWithRuntimeLessThanCalled();
        thenExpectedTracksReturned(expectedTracks, results);
    }

    @Test
    void getTracksWithRuntimeLessThan_preparedStatementError_shouldThrowException() throws SQLException {
        Connection connection = mock(Connection.class);
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(RepositoryException.class,
                () -> subjectUnderTest
                        .getTracksWithRuntimeLessThan(QUICK_LISTEN_TIME, MAX_NO_OF_TRACKS));
    }

    @Test
    void getTracksWithRuntimeLessThan_resultSetError_shouldThrowException() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenThrow(SQLException.class);
        assertThrows(RepositoryException.class,
                () -> subjectUnderTest
                        .getTracksWithRuntimeLessThan(QUICK_LISTEN_TIME, MAX_NO_OF_TRACKS));
    }

    private ResultSet givenWeConnectToTheDatabase() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(databaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        return resultSet;
    }

    private void givenDatabaseReturnLessThanMaxNoOfTracks(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("name")).thenReturn("Track 1");
        when(resultSet.getString("genre")).thenReturn("Rock");
        when(resultSet.getFloat("unitprice")).thenReturn(1.99f);
        when(resultSet.getInt("milliseconds")).thenReturn(100000);
    }

    private void givenDatabaseReturnMaxNoOfTracks(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("name")).thenReturn("Track 1").thenReturn("Track 2");
        when(resultSet.getString("genre")).thenReturn("Rock").thenReturn("Pop");
        when(resultSet.getFloat("unitprice")).thenReturn(1.99f).thenReturn(2.99f);
        when(resultSet.getInt("milliseconds")).thenReturn(100000).thenReturn(200000);
    }

    private List<Track> whenGetTracksWithRuntimeLessThanCalled() throws RepositoryException {
        return subjectUnderTest.getTracksWithRuntimeLessThan(QUICK_LISTEN_TIME, MAX_NO_OF_TRACKS);
    }

    private void thenExpectedTracksReturned(List<Track> expectedTracks, List<Track> actualTracks) {
        assertEquals(expectedTracks.size(), actualTracks.size());
        for (int i = 0; i < expectedTracks.size()-1 ; i++) {
            assertEquals(expectedTracks.get(i).name(), actualTracks.get(i).name());
            assertEquals(expectedTracks.get(i).genre(), actualTracks.get(i).genre());
            assertEquals(expectedTracks.get(i).price(), actualTracks.get(i).price());
            assertEquals(expectedTracks.get(i).runtimeInMilliseconds(), actualTracks.get(i).runtimeInMilliseconds());
        }
    }
}
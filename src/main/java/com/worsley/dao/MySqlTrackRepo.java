package com.worsley.dao;


import com.worsley.dao.exception.RepositoryException;
import com.worsley.dto.Track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is an MySQL implementation of {@code TrackRepo}.
 * NOTE: You will often see ORMs used to map database tables to Java objects but
 * for the purposes of this exercise I went with standard JDBC.
 */
public class MySqlTrackRepo implements TrackRepo {

    private final DatabaseConnection databaseConnection;

    private static final String GET_TRACKS_OF_RUNTIME_QUERY =
            "SELECT T.name, T.unitprice, T.milliseconds, G.name as genre FROM track AS T\n" +
                    "INNER JOIN genre AS G ON t.genreid = g.genreid\n" +
                    "WHERE T.milliseconds < ?; ";

    public MySqlTrackRepo(DatabaseConnection database) {
        this.databaseConnection = Objects.requireNonNull(database, "database must not be null");
    }

    @Override
    public List<Track> getTracksWithRuntimeLessThan(int trackLength, int maxNoOfTracks) throws RepositoryException {
        List<Track> tracks = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TRACKS_OF_RUNTIME_QUERY)) {
            preparedStatement.setString(1, String.valueOf(trackLength));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int count = 0;
                while (resultSet.next()) {
                    Track track = new Track(
                            resultSet.getString("name"),
                            resultSet.getFloat("unitprice"),
                            resultSet.getInt("milliseconds"),
                            resultSet.getString("genre")
                    );
                    tracks.add(track);
                    count++;
                    if (count == maxNoOfTracks) {
                        break;
                    }
                }
            }
        }
        catch (SQLException ex) {
            String errorMessage = "An error occurred while querying the database: " + ex.getMessage();
            throw new RepositoryException(errorMessage, ex);
        }
        return tracks;
    }
}

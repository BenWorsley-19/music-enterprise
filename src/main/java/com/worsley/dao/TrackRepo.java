package com.worsley.dao;

import com.worsley.dao.exception.RepositoryException;
import com.worsley.dto.Track;

import java.util.List;

/**
 * An EmployeeDao represents a store of employees.
 */
public interface TrackRepo
{
    /**
     * Retrieves the {@link Track}s which have a runtime less than the given track length.
     *
     * @param trackLength the length of the track.
     * @param maxNoOfTracks the max number of tracks to return in the list.
     * @return A list of {@link Track}s that satisfy the query.
     *
     * @throws RepositoryException if an error occurs retrieving the data from the database.
     */
    List<Track> getTracksWithRuntimeLessThan(int trackLength, int maxNoOfTracks) throws RepositoryException;
}
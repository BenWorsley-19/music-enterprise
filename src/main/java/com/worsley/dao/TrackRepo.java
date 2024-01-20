package com.worsley.dao;

import com.worsley.dao.exception.RepositoryException;
import com.worsley.model.Track;

import java.util.List;

/**
 * An EmployeeDao represents a store of employees.
 */
public interface TrackRepo
{
    /**
     * Retrieves the {@link Track}s which have a runtime less than the given track length.
     *
     * @param trackLength               the length of the track.
     * @return A list of {@link Track}s that satisfy the query.
     *
     * @throws RepositoryException if an error occurs retrieving the data from the database.
     */
    List<Track> getTracksWithRuntimeLessThan(int trackLength) throws RepositoryException;
}
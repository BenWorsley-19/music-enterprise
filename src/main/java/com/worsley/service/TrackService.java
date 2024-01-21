package com.worsley.service;

import com.worsley.dao.TrackRepo;
import com.worsley.dao.exception.RepositoryException;
import com.worsley.dto.QuickListensSummary;
import com.worsley.dto.Track;

import java.util.List;

/**
 * Provides the services based around Tracks
 */
public class TrackService {

    private static final int QUICK_LISTEN_TIME = 120000; // 2 minutes
    private static final int MAX_NO_OF_TRACKS = 20;
    private static final int ROCK_GENRE_DISCOUNT_PERCENT = 20;

    private final TrackRepo trackRepo;

    public TrackService(TrackRepo trackRepo) {
        this.trackRepo = trackRepo;
    }

    public QuickListensSummary getQuickListens() throws RepositoryException {
        List<Track> tracks = trackRepo.getTracksWithRuntimeLessThan(QUICK_LISTEN_TIME, MAX_NO_OF_TRACKS);
        float totalPrice = 0;
        int totalRuntimeMs = 0;
        for (Track track : tracks) {
            totalPrice += "Rock".equals(track.genre()) ? track.discountedPrice(ROCK_GENRE_DISCOUNT_PERCENT) : track.price();
            totalRuntimeMs += track.runtimeInMilliseconds();
        }
        // Convert totalRuntime to minutes and seconds in the format mm:ss
        String totalRuntime = String.format("%d:%02d", totalRuntimeMs / 60000, (totalRuntimeMs % 60000) / 1000);
        return new QuickListensSummary(tracks, totalPrice, totalRuntime);
    }
}
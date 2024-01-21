package com.worsley.service;

import com.worsley.dao.TrackRepo;
import com.worsley.dao.exception.RepositoryException;
import com.worsley.dto.QuickListensSummary;
import com.worsley.dto.Track;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @Mock
    private TrackRepo repo;

    @InjectMocks
    private TrackService subjectUnderTest;

    @Test
    void getQuickListens_repoReturnsAllRockTracks_translatesToSummaryAsExpected() throws RepositoryException {
        givenRepoReturnsAllRockTracks();
        QuickListensSummary summary = whenGetQuickListensCalled();
        thenCorrectNumberOfTracksReturned(summary);
        thenRockDiscountIsAppliedToAll(summary);
        thenTotalRuntimeIsCorrect(summary);
    }

    @Test
    void getQuickListens_repoReturnsNoRockTracks_translatesToSummaryAsExpected() throws RepositoryException {
        givenRepoReturnsAllPopTracks();
        QuickListensSummary summary = whenGetQuickListensCalled();
        thenCorrectNumberOfTracksReturned(summary);
        thenNoDiscountIsApplied(summary);
        thenTotalRuntimeIsCorrect(summary);
    }

    @Test
    void getQuickListens_repoReturnsSomeTracks_translatesToSummaryAsExpected() throws RepositoryException {
        givenRepoReturnsMixOfGenres();
        QuickListensSummary summary = whenGetQuickListensCalled();
        thenCorrectNumberOfTracksReturned(summary);
        thenRockDiscountIsAppliedOnlyToRockTracks(summary);
        thenTotalRuntimeIsCorrect(summary);
    }

    private void givenRepoReturnsAllRockTracks() throws RepositoryException {
        mockReturnTracks("Rock", "Rock", "Rock");
    }

    private void givenRepoReturnsAllPopTracks() throws RepositoryException {
        mockReturnTracks("Pop", "Pop", "Pop");
    }

    private void givenRepoReturnsMixOfGenres() throws RepositoryException {
        mockReturnTracks("Rock", "Pop", "Rap");
    }

    private void mockReturnTracks(String genre1, String genre2, String genre3) throws RepositoryException {
        Track testTrack1 = new Track("Track 1", 10f, 100500, genre1);
        Track testTrack2 = new Track("Track 2", 10f, 210000, genre2);
        Track testTrack3 = new Track("Track 3", 10f, 240000, genre3);
        List<Track> tracks = Arrays.asList(testTrack1, testTrack2, testTrack3);
        when(repo.getTracksWithRuntimeLessThan(120000, 20)).thenReturn(tracks);
    }

    private QuickListensSummary whenGetQuickListensCalled() throws RepositoryException {
        return subjectUnderTest.getQuickListens();
    }

    private void thenCorrectNumberOfTracksReturned(QuickListensSummary summary) {
        assertEquals(3, summary.tracks().size());
    }

    private void thenRockDiscountIsAppliedToAll(QuickListensSummary summary) {
        assertEquals(24.0f, summary.cost());
    }

    private void thenRockDiscountIsAppliedOnlyToRockTracks(QuickListensSummary summary) {
        assertEquals(28.0f, summary.cost());
    }

    private void thenNoDiscountIsApplied(QuickListensSummary summary) {
        assertEquals(30.0f, summary.cost());
    }

    private void thenTotalRuntimeIsCorrect(QuickListensSummary summary) {
        assertEquals("9:10", summary.totalRuntime());
    }
}
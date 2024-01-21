package com.worsley.ui;

import com.worsley.dao.exception.RepositoryException;
import com.worsley.dto.QuickListensSummary;
import com.worsley.service.TrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;


/**
 * AppCli represents a UI from the command line for Music Enterprise.
 * NOTE: I could have returned this to a web UI via a REST API but I wanted to keep this simple.
 */
public class AppCli {

    private static final String DISPLAY_MESSAGE = """
            Welcome to Music Enterprise!\n
            Here are the tracks that are less than 2 minutes long:\n%s\n
            The total cost of these tracks is: $%.2f\n
            The total runtime of these tracks is: %s\n
            Thank you for using Music Enterprise!
            """;

    private static final Logger LOGGER = LoggerFactory.getLogger(AppCli.class);

    private final TrackService trackService;

    public AppCli(TrackService trackService) {
        this.trackService = trackService;
    }

    /**
     * Runs the CLI to start off our application
     */
    public void findAndDisplayQuickListens() {
        try {
            QuickListensSummary summary = trackService.getQuickListens();
            displayResult(summary);
        }
        catch (RepositoryException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void displayResult(QuickListensSummary summary) {
        String trackList = summary.tracks()
                .stream()
                .map(t -> "Track: " + t.name() + ", Genre: " + t.genre())
                .collect(Collectors.joining("\n"));
        System.out.println(String.format(DISPLAY_MESSAGE, trackList, summary.cost(), summary.totalRuntime()));
    }
}

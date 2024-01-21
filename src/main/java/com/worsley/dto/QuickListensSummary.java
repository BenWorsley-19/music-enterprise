package com.worsley.dto;

import java.util.List;

public record QuickListensSummary(List<Track> tracks, float cost, String totalRuntime) {
}

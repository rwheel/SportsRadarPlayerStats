package com.github.rwheel.SportsRadarProject.models;

import java.util.List;

public class HierarchyResponse {

    private List<League> leagues;

    public List<League> getLeagues() {
        return leagues;
    }

    @Override
    public String toString() {
        return "HierarchyResponse{" +
                "leagues=" + leagues +
                '}';
    }
}

package com.github.rwheel.SportsRadarProject.models;

import java.util.ArrayList;

public class Division {

    private ArrayList<Team> teams;

    public ArrayList<Team> getTeams() {
        return teams;
    }




    @Override
    public String toString() {

        return ""+teams+"";
    }
}

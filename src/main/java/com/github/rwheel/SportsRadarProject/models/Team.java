package com.github.rwheel.SportsRadarProject.models;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;

public class Team {

    private String name;
    private String market;
    private String id;
    private ArrayList<Player> players;

    public String getName() {
        return name;
    }

    public String getMarket() {
        return market;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public String toString() {

        return "Team Name: "+name+", Market: "+market+", TeamID: "+id+"\n";
    }
}

package com.github.rwheel.SportsRadarProject.models;

import java.util.ArrayList;

public class League {

    private ArrayList<Division> divisions;

    public ArrayList<Division> getDivisions() {
        return divisions;
    }




    @Override
    public String toString() {
        return ""+divisions+"";
    }
}

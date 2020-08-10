module SportsRadarPlayerStats {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;
    requires java.net.http;

    opens com.github.rwheel.SportsRadarProject;
    opens com.github.rwheel.SportsRadarProject.controllers;
    opens com.github.rwheel.SportsRadarProject.models;

}
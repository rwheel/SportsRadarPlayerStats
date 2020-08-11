package com.github.rwheel.SportsRadarProject.controllers;

import com.github.rwheel.SportsRadarProject.models.HierarchyResponse;
import com.github.rwheel.SportsRadarProject.models.Team;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayerController implements Initializable {
    private String sportsRadarApiKey = System.getenv("Sports_API_Key");
    private String baseUrl = "http://api.sportradar.us/mlb/trial/v6.6/en";
    private HttpClient client;
    private static List<String> teamInfo;
    private static List<String> teamName;
    private static List<String> teamMarket;
    private static List<String> teamID;
    private static List<String> playerInfo;

    @FXML
    private TextField teamNumberField;

    @FXML
    private TextArea outPutArea;

    @FXML
    private TextArea colOne;
    @FXML
    private TextArea colTwo;
    @FXML
    private TextArea colThree;


    @FXML
    void handleTeams(ActionEvent event) {
        var uri = baseUrl + "/league/hierarchy.json";
        HashMap<String, String> params = new HashMap<>() {{
            put("api_key", sportsRadarApiKey);
        }};

        try {
            var response = doGet(uri, params);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            var hierarchyResponse = gson.fromJson(response.body(), HierarchyResponse.class);

            displayTeamData(hierarchyResponse);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    void handleTeamPlayers(ActionEvent event) {
        var teamID = teamNumberField.getText();
        var uri = baseUrl + "/teams/"+teamID+"/profile.json";
        HashMap<String, String> params = new HashMap<>(){{
            put("api_key",sportsRadarApiKey);
        }};
        try {
            var response = doGet(uri, params);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            var team = gson.fromJson(response.body(), Team.class);
            displayPlayerData(team);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    private HttpResponse<String> doGet(String uri, HashMap<String, String> params) throws Exception {
        var paramsString = params.keySet().stream()
                                          .map((key)->key+"="+params.get(key))
                                          .collect(Collectors.joining("&"));
        var requestURI = URI.create(uri +"?"+paramsString);

        var request = HttpRequest.newBuilder(requestURI).GET().build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;

    }
    void displayTeamData(HierarchyResponse response) throws IOException {
        String str = String.valueOf(response.getLeagues());
        var into = str.replace("[", "")
                      .replace("]", "")
                      .replace("{", "")
                      .replace("}", "");

        teamInfo = Arrays.stream(into.split("[,\\/]")).collect(Collectors.toList());
        teamName = IntStream.range(0, teamInfo.size()).filter(n -> n % 3 == 0).mapToObj(teamInfo::get).collect(Collectors.toList());
        teamMarket = IntStream.range(0, teamInfo.size()).filter(n -> n % 3 == 1).mapToObj(teamInfo::get).collect(Collectors.toList());
        teamID = IntStream.range(0, teamInfo.size()).filter(n -> n % 3 == 2).mapToObj(teamInfo::get).collect(Collectors.toList());
        System.out.print(into);
        String strB = String.valueOf(teamInfo);
        var name = strB.replace("[", " ")
                          .replace("]", "")
                          .replace(", ", "\n");

         outPutArea.setText(name);
    }


    void displayPlayerData(Team response) throws IOException {

                String str = String.valueOf(response.getPlayers());
                var into = str.replace("[", "")
                              .replace("]", "")
                              .replace("{", "")
                              .replace("}", "")
                              .replace("null", "No College on Record");
                System.out.print(into);
                playerInfo = Arrays.stream(into.split("[,\\/]")).collect(Collectors.toList());

        String strB = String.valueOf(playerInfo);

             var name = strB.replace("[", " ")
                       .replace("]", "")
                       .replace(", ", "\n");

                outPutArea.setText(" "+response.getName()+"\n\n"+name);
            }

            @Override
            public void initialize(URL url, ResourceBundle resourceBundle) {
                client = HttpClient.newHttpClient();
            }
        }


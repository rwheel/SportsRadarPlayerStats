package com.github.rwheel.SportsRadarProject.controllers;

import com.github.rwheel.SportsRadarProject.models.HierarchyResponse;
import com.github.rwheel.SportsRadarProject.models.League;
import com.github.rwheel.SportsRadarProject.models.Team;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PlayerController implements Initializable {
    private String sportsRadarApiKey = System.getenv("Sports_API_Key");
    private String baseUrl = "http://api.sportradar.us/mlb/trial/v6.6/en";
    private HttpClient client;
    private static List<String> teamInfo;
//    public static List<String> teamName;
//    private static List<String> teamMarket;
//    private static List<String> teamID;

    @FXML
    private TextField teamNumberField;

    @FXML
    private TextArea outPutArea;




    @FXML
    void handleTeams(ActionEvent event) {
        var uri = baseUrl + "/league/hierarchy.json";
        HashMap<String, String> params = new HashMap<>(){{
            put("api_key",sportsRadarApiKey);
        }};


        try {
            var response = doGet(uri, params);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            var hierarchyResponse = gson.fromJson(response.body(), HierarchyResponse.class);

            displayTeamData(hierarchyResponse);
//            outPutArea.setText(hierarchyResponse.toString());
//            System.out.print(hierarchyResponse);
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

            System.out.print(team);
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
    void displayTeamData(HierarchyResponse user) throws IOException {
    String str = String.valueOf(user.getLeagues());
    var into = str.replace("[","")
                  .replace("]", "")
                  .replace("{","")
                  .replace("}", "")
                  .replace(",", "");


//     teamInfo = Arrays.stream(into.split("[,\\/]")).collect(Collectors.toList());
//     teamName = IntStream.range(0, teamInfo.size()).filter(n -> n%3 == 0).mapToObj(teamInfo::get).collect(Collectors.toList());
//     teamMarket = IntStream.range(0, teamInfo.size()).filter(n -> n%3 == 1).mapToObj(teamInfo::get).collect(Collectors.toList());
//     teamID = IntStream.range(0, teamInfo.size()).filter(n -> n%3 == 2).mapToObj(teamInfo::get).collect(Collectors.toList());
     outPutArea.setText(into);


    }
    void displayPlayerData(Team user) throws IOException {
        String str = String.valueOf(user.getPlayers());
        var into = str.replace("[","")
                      .replace("]", "")
                      .replace("{","")
                      .replace("}", "")
                      .replace(",", "")
                      .replace("null", "No College on Record");

        outPutArea.setText(user.getName()+"\n"+into);


    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client = HttpClient.newHttpClient();
    }
}


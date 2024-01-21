package com.worsley;

import com.worsley.dao.DatabaseConnection;
import com.worsley.dao.MySqlTrackRepo;
import com.worsley.service.TrackService;
import com.worsley.ui.AppCli;

public class Main {

    public static void main(String[] args) {
        String dbUrl = System.getenv("MUSIC_ENTERPRISE_DB_URL");
        String dbUsername = System.getenv("MUSIC_ENTERPRISE_DB_USERNAME");
        String dbPassword = System.getenv("MUSIC_ENTERPRISE_DB_PASSWORD");
        // Fixes issues with compatibility between Java and MySQL versions
        dbUrl += "?enabledTLSProtocols=TLSv1.2&useSSL=false";
        MySqlTrackRepo repo = new MySqlTrackRepo(new DatabaseConnection(dbUrl, dbUsername, dbPassword));
        TrackService service = new TrackService(repo);
        AppCli app = new AppCli(service);
        app.findAndDisplayQuickListens();
    }
}
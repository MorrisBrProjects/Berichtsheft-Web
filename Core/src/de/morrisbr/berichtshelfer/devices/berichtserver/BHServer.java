package de.morrisbr.berichtshelfer.devices.berichtserver;

import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.nio.file.Paths;

public class BHServer {

    private Javalin app;
    private String ressourcePath;
    private int port;

    public BHServer(int port, String ressourcePath) {

        this.port = port;
        this.ressourcePath = ressourcePath;

        this.app = Javalin.create().start(port);
        app.config.addStaticFiles(ressourcePath, Location.EXTERNAL);
        //app.config.addStaticFiles("C:/Users/MorrisBrandt/Desktop/Test", Location.EXTERNAL);
        DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(Paths.get(ressourcePath));
    }

    public Javalin getApp() {
        return app;
    }

    public String getRessourcePath() {
        return ressourcePath;
    }
}

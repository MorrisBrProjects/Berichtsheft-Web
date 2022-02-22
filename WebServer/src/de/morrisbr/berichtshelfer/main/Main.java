package de.morrisbr.berichtshelfer.main;

import java.util.List;

import de.morrisbr.berichtshelfer.core.devices.bericht.Bericht;
import de.morrisbr.berichtshelfer.core.devices.berichtserver.BHServer;
import de.morrisbr.berichtshelfer.core.devices.services.BerichtService;
import de.morrisbr.berichtshelfer.utils.JsonConverter;
import io.javalin.Javalin;

public class Main {

	private BHServer bhServer;
	private BerichtService berichtService;

	public Main(int port, String ressourcePath) {
		//this.bhServer = new BHServer(port, ressourcePath);
		this.berichtService = new BerichtService("mongodb://37.114.47.77:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false", "BerichtHelfer");
	}

	public void start() {

		Javalin javalin = Javalin.create().start(82);

		javalin.get("/getTask", ctx -> {
			String name = ctx.queryParam("name");

		});

		javalin.get("/get", ctx -> {
			String name = ctx.queryParam("name");

		});

		javalin.get("/createTask", ctx -> {
			String name = ctx.queryParam("name");

		});

		javalin.post("/createBericht", ctx -> {
			String name = ctx.queryParam("name");
			System.out.println("_-_-_-_-_-_-_____>" + ctx.body());
			Bericht bericht = (Bericht) JsonConverter.jsonStringToObject(ctx.body(), Bericht.class);
			berichtService.createBericht(bericht);
		});

		//PASS AUF DAS BEFOR EDITING DER BERICHT AUCH WIRKLICH EXISTIERT
		//Berichte werden nie umbenannt, deshalb haben die immer die selben name, aber VTL statt namen eine UUID
		javalin.post("/editBericht", ctx -> {
			String name = ctx.queryParam("name");
			System.out.println("_-_-_-_-_-_-_____>" + ctx.body());
			Bericht bericht = (Bericht) JsonConverter.jsonStringToObject(ctx.body(), Bericht.class);
			bericht.delete();
		});

		javalin.get("/getBericht", ctx -> {
			String name = ctx.queryParam("name");
			ctx.json(berichtService.getBerichtAsObject(name));
		});

		javalin.get("/removeBericht", ctx -> {
			String name = ctx.queryParam("name");
			berichtService.deleteBericht(name);
		});

		javalin.get("/getAllBerichte", ctx -> {
			List<Bericht> berichte = berichtService.getAllBerichteAsObject();
			ctx.json(berichte);
		});
	}

    public static void main(String[] args) {
		new Main(82, "resources/DataStorage").start();
    }

}

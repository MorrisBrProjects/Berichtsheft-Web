package de.morrisbr.witzlecraft.main;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import de.morrisbr.witzlecraft.bericht.Bericht;
import de.morrisbr.witzlecraft.bericht.task.Task;
import de.morrisbr.witzlecraft.bericht.task.elements.ByteImage;
import de.morrisbr.witzlecraft.utils.JsonConverter;
import de.morrisbr.witzlecraft.services.BerichtService;
import de.morrisbr.witzlecraft.services.TaskService;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import javax.imageio.ImageIO;

public class Main {

	private Javalin app;
	private String ressourcePath;
	private int port;

	private BerichtService berichtService;

	public Main(int port, String ressourcePath) {
		this.port = port;
		this.ressourcePath = ressourcePath;
		this.berichtService = new BerichtService();
	}

	public void start() {
		this.app = Javalin.create().start(port);
		app.config.addStaticFiles(ressourcePath, Location.EXTERNAL);
		//app.config.addStaticFiles("C:/Users/MorrisBrandt/Desktop/Test", Location.EXTERNAL);
		DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(Paths.get(ressourcePath));

		//app.post("/uploadImage", ctx -> {
		//	ctx.uploadedFiles("files").forEach(uploadedFile -> {
		//		FileUtil.streamToFile(uploadedFile.getContent(), "resources/OnlineBanking/tasks/img" + uploadedFile.getFilename());
		//	});
		//});

		app.get("/getTask", ctx -> {
			String name = ctx.queryParam("name");
			ctx.json(TaskService.getTaskAsJson(name));
		});

		app.get("/get", ctx -> {
			String name = ctx.queryParam("name");
			//TaskService.getTaskAsObject("test").getImage().saveImage("test.png");
		});

		app.get("/createTask", ctx -> {
			String name = ctx.queryParam("name");
			ByteImage byteImage = new ByteImage(ByteImage.toByteArray(ImageIO.read(new File(ressourcePath + "/tasks/img/test.png")), "png"));
			Task task = new Task(name);
			//task.setImage(byteImage);
			JsonConverter.objectToJsonFile(ressourcePath + "/tasks/" + name + ".json", task);
		});

		app.post("/createBericht", ctx -> {
			String name = ctx.queryParam("name");
			System.out.println("_-_-_-_-_-_-_____>" + ctx.body());
			Bericht bericht = (Bericht) JsonConverter.jsonStringToObject(ctx.body(), Bericht.class);
			JsonConverter.objectToJsonFile(ressourcePath + "/berichte/" + bericht.getTitle() + ".json", bericht);
			//Bericht bericht = new Bericht(name);
			//DayBericht dayBericht = new DayBericht(name);
			//dayBericht.addTask(new Task(name), name + " Content");
			//bericht.addDay(Veriables.MONDAY, dayBericht);

			//System.out.println(ctx.body() + "------------------------");
			//Bericht bericht = (Bericht) JsonConverter.jsonStringToObject(ctx.resultString(), Bericht.class);

			//ByteImage byteImage = new ByteImage(ByteImage.toByteArray(ImageIO.read(new File("resources/OnlineBanking/tasks/img/test.png")), "png"));

			//task.setImage(byteImage);

			//JsonConverter.objectToJsonFile("resources/OnlineBanking/berichte/" + bericht.getTitle() + ".json", bericht);
		});

		//PASS AUF DAS BEFOR EDITING DER BERICHT AUCH WIRKLICH EXISTIERT
		//Berichte werden nie umbenannt, deshalb haben die immer die selben name, aber VTL statt namen eine UUID
		app.post("/editBericht", ctx -> {
			String name = ctx.queryParam("name");
			System.out.println("_-_-_-_-_-_-_____>" + ctx.body());
			Bericht bericht = (Bericht) JsonConverter.jsonStringToObject(ctx.body(), Bericht.class);
			bericht.delete();
			JsonConverter.objectToJsonFile(ressourcePath + "/berichte/" + bericht.getTitle() + ".json", bericht);
		});

		app.get("/getBericht", ctx -> {
			String name = ctx.queryParam("name");
			Bericht bericht = (Bericht) JsonConverter.jsonFileToObject(ressourcePath + "/berichte/" + name + ".json", Bericht.class);
			ctx.json(bericht);
		});

		app.get("/removeBericht", ctx -> {
			String name = ctx.queryParam("name");
			berichtService.getBerichtAsObject(name).delete();
		});

		app.get("/getAllBerichte", ctx -> {
			List<Bericht> berichte = berichtService.getAllBerichteAsObject();
			ctx.json(berichte);
		});
	}

    public static void main(String[] args) {
		new Main(82, "resources/DataStorage").start();
    }
}

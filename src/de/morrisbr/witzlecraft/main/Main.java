package de.morrisbr.witzlecraft.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.morrisbr.witzlecraft.objects.*;
import de.morrisbr.witzlecraft.pages.RegisterPage;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.Javalin;
import io.javalin.core.util.FileUtil;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.template.JavalinJte;

import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) {

		
        Javalin app = Javalin.create().start(82);

		app.config.addStaticFiles("resources/OnlineBanking", Location.EXTERNAL);

		//app.config.addStaticFiles("C:/Users/MorrisBrandt/Desktop/Test", Location.EXTERNAL);

        DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(Paths.get("resources/OnlineBanking"));
        TemplateEngine engine = TemplateEngine.create(codeResolver, ContentType.Html);
        engine.createPrecompiled(Path.of("jte-classes"), ContentType.Html);
        JavalinJte.configure(engine);


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

			ByteImage byteImage = new ByteImage(ByteImage.toByteArray(ImageIO.read(new File("resources/OnlineBanking/tasks/img/test.png")), "png"));

			Task task = new Task(name);
			//task.setImage(byteImage);

			JsonConverter.objectToJsonFile("resources/OnlineBanking/tasks/" + name + ".json", task);
		});

		app.post("/createBericht", ctx -> {
			String name = ctx.queryParam("name");

			System.out.println("_-_-_-_-_-_-_____>" + ctx.body());
			Bericht bericht = (Bericht) JsonConverter.jsonStringToObject(ctx.body(), Bericht.class);

			JsonConverter.objectToJsonFile("resources/OnlineBanking/berichte/" + bericht.getTitle() + ".json", bericht);

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

		app.get("/getBericht", ctx -> {
			String name = ctx.queryParam("name");


			Bericht bericht = (Bericht) JsonConverter.jsonFileToObject("resources/OnlineBanking/berichte/" + name + ".json", Bericht.class);


			ctx.json(bericht);
		});
		app.get("/removeBericht", ctx -> {
			String name = ctx.queryParam("name");

			BerichtService.getBerichtAsObject(name).delete();
		});

		app.get("/getAllBerichte", ctx -> {
			List<Bericht> berichte = BerichtService.getAllBerichteAsObject();
			ctx.json(berichte);
		});

		app.get("/login", ctx -> {
			RegisterPage page = new RegisterPage();
			page.setCode("443w53453465436456");
			ctx.render("login.jte", Collections.singletonMap("page", page));
		});

		
		

		



        
    }

}

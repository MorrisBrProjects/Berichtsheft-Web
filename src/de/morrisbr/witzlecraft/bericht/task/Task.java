package de.morrisbr.witzlecraft.bericht.task;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Task {
    private String title;
    //private BufferedImage image = null;
    //private ByteImage image;

    public Task() {

    }

    public Task(String name) {
        this.title = name;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //public ByteImage getImage() {
    //    return image;
   // }

    //public void setImage(ByteImage image) {
    //    this.image = image;
    //}
}

package de.morrisbr.berichtshelfer.core.devices.bericht.category;


import de.morrisbr.berichtshelfer.core.devices.bericht.task.Task;

public class CategoryWithContent {


    private Task task;
    private String content;

    public CategoryWithContent(Task task) {
        this.task = task;
    }

    public CategoryWithContent() {

    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

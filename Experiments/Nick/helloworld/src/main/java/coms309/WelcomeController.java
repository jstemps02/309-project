package coms309;

import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.ArrayList;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "<h1>Hello and welcome to COMS 309</h1>";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "<h1>Hello " + name + ", and welcome to COMS 309!</h1>";
    }

    @GetMapping("/home")
    public String home() {
        String s = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Home</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Main Menu</h1>    \n" +
                "    <p>\n" +
                "       <a href=\"/home/task/new\">Create Task</a> <br>" +
                "        To create a task add \"/new\" to the URL. <br>\n" +
                "    </p>\n" +
                "</body>\n" +
                "</html>";
        return s;
    }

    @GetMapping("/home/task")
    public String tasks() {
        String s = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Task</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Active Tasks</h1>\n" +
                "    <ul>\n" +
                "        <li>ex: Example Task</li>\n" +
                "    </ul>\n" +
                "</body>\n" +
                "</html>";
        return s;
    }

    @GetMapping("/home/task/new")
    public String newTask() {
        String s = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Create Task</title>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Create a Task</h1>\n" +
                "    <form class=\"input\" action=\"/task\" method=\"get\">\n" +
                "        <label for=\"name\">Task name:</label><br>\n" +
                "        <input type=\"text\" placeholder=\"Enter name of task...\" id=\"name\" name=\"name\"><br>\n" +
                "        <label for=\"date\">Due Date:</label><br>\n" +
                "        <input type=\"date\" placeholder=\"Enter task due date...\" id=\"date\" name=\"date\" ><br><br>\n" +
                "        <input type=\"submit\" value=\"Submit\">\n" +
                "      </form>\n" +
                "</body>\n" +
                "</html>";
        return s;
    }

    @PostMapping("/echo/{task}/{date}")
    public String echo(@PathVariable String task,@PathVariable String date) {
        return "You entered:  " + task + " and "+ date;
    }

    @PostMapping("/task/new")
    public Task Task(@PathVariable String task,@PathVariable String date) {
        Task t = new Task(task, date);
        System.out.println(t);
        System.out.println(t.name);
        System.out.println(t.date);
        return t;
    }
}





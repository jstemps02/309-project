package coms309;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Task {
    String name;
    String date;

    public Task(){
        name = "";
        date = "";
    }
    public Task newTask(){
        Task t = new Task();
        return t;
    }

}

package coms309;

// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
public class Task {
    String name;
    String date;
  
    public Task(String n,String d) {
      name = n;
      date = d; 
    }
  
    public static void main(String[] args) {
      Task myObj = new Task("this","2-13-23");
      System.out.println(myObj.name);
      System.out.println(myObj.date);
    }
  }
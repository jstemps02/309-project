package backend.Users;


import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import backend.MD5;
import backend.Todos.Todo;
import backend.Groups.Group;
import backend.Stacks.Stack;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    private boolean isActive;
    private boolean isLoggedIn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Todo> Todos;
//    @ManyToMany(cascade = CascadeType.ALL)
//    private List<Group> Groups;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Stack> Stacks;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.isActive = true;
        this.isLoggedIn = false;
        Todos = new ArrayList<>();
//        Groups = new ArrayList<>();
        Stacks = new ArrayList<>();
    }

    public User(){
        Todos = new ArrayList<>();
    }

    public  int getId(){ return id; }

    public void setId(int id){ this.id = id;}

    public String getUsername(){ return username; }

    public void setUsername(String username){ this.username = username; }

    public boolean getLoggedIn(){ return isLoggedIn; }

    public void setLogIn(){ this.isLoggedIn = true; }

    public void setLogOut(){ this.isLoggedIn = false; }
    public String getPassword(){ return password; }

    public void setPassword(String password){ this.password = password; }

    public boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }

    public  int nextAvailableID(){ return id; }

    public List<Todo> getTodos() {    return Todos;   }

    public void setTodos(Todo todo) { Todos.add(todo);    }

    public void setTodoList(List <Todo> t) { this.Todos = t;    }

//    public void addGroups(Group group){
//        this.Groups.add(group);
//    }
//    public List<Group> getGroup() {    return Groups;   }

    public void addStack(Stack stack){
        this.Stacks.add(stack);
    }
    public List<Stack> getStack() {    return Stacks;   }

    public void setStack(List<Stack> s) {   this.Stacks = s;   }
    public void addTodos(Todo todos){  this.Todos.add(todos);



    }
}

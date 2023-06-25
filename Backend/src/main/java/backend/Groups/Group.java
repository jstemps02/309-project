package backend.Groups;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import backend.Stacks.Stack;
import backend.Todos.Todo;
import backend.Users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//insig
    private String groupname;

    private String owner;
    private boolean isActive;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private List<Stack> Stack;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private List<User> UserList;



    public Group() {

    }
    public Group(String groupname, String owner){
        this.groupname = groupname;
        this.isActive = true;
        this.owner = owner;
        Stack = new ArrayList<Stack>();
        UserList = new ArrayList<>();
    }

//    public Group(){
//        UserList = new ArrayList<>();
//    }

    public  int getId(){ return id; }

    public void setId(int id){ this.id = id;}

    public String getGroupname(){ return groupname; }

    public void setGroupname(String groupname){ this.groupname = groupname; }

    public String getOwner(){ return owner; }

    public void setOwner(String owner){ this.owner = owner; }

    public boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }

    public  int nextAvailableID(){ return id; }

    public List<Stack> getStacks(){
        return Stack;
    }

    public void setStackNull() { this.Stack = null; }
    public void addStack(Stack stack) {
        this.Stack.add(stack);
    }
    public List<User> getUsers(){
        return UserList;
    }
    public void setUserlistNull() { this.UserList = null; }
    public void addUser(User user) {
        this.UserList.add(user);
    }

    public void removeUser(User user) {
        this.UserList.remove(user);
    }

    public void setStack(List<Stack> stack) {   this.Stack = stack;}
}
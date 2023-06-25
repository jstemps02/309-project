package backend.Todos;
import java.util.ArrayList;
import java.util.List;

import backend.Stacks.Stack;
import backend.Stacks.StackRepository;
import backend.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import backend.Users.User;

//    @Operation(summary = "", description = "")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
//            @ApiResponse(responseCode = "400", description = "")
//    })

@RestController
public class TodoController {

    @Autowired
    TodoRepository todoRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    StackRepository stackRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    private String alreadyMade = "{\"message\":\"user already made\"}";

    private String notFound = "{\"message\":\"user not found\"}";
    private String alreadyLoggedIn = "{\"message\":\"user already logged in\"}";
    private String notLoggedIn = "{\"message\":\"user not logged in\"}";


    @Operation(summary = "Lists all todos in the database", description = "Uses the FindALl command to create a JSON object of all existing todos in the database at the current time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The todo list was successfully sent", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class))),
    })
    @GetMapping(path = "/todos")
    ResponseEntity<?> getAllTodos(){
        return ResponseEntity.status(200).body(todoRepository.findAll());
    }

    @Operation(summary = "Lists todo in the database by id", description = "Uses the FindByID command to create a JSON object of the Todo in the database at the given id at the current time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Todo list was successfully sent", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class))),
    })
    @GetMapping(path = "/todos/id/{id}")
    ResponseEntity<?> getTodoById( @PathVariable int id){
        return ResponseEntity.status(200).body(todoRepository.findById(id));
    }

    @Operation(summary = "Gets the list of Todos under the ownership of the given username", description = "This method returns a list of all the todos of a specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully created todo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "400", description = "failure, test using smaller methods, this one is too big to debug"),
            @ApiResponse(responseCode = "401", description = "failure, test using smaller methods, this one is too big to debug")
    })
    @GetMapping(path = "/todos/user/{username}")
    ResponseEntity<?> getTodoByUsername( @PathVariable String username){
        User foundUser = userRepository.findByUsername(username);
        String usernameOfuser;

        try {
            usernameOfuser = foundUser.getUsername();
            if(foundUser.getLoggedIn() == false){
                return ResponseEntity.status(400).body("failure");
            }
        } catch (NullPointerException e) {
            return ResponseEntity.status(401).body("null");
        }
        return ResponseEntity.status(200).body(todoRepository.findListByUser(foundUser));
    }

    @Operation(summary = "Adds a Todo to an existing user", description = "this will add a Todo to a stack, which will both create the Todo for the user that makes it and adds it to the Todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully added a ToDo to the stack", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class))),
            @ApiResponse(responseCode = "401", description = "The provided stack-name did not resolve to a stack in the database"),
            @ApiResponse(responseCode = "404", description = "The given Todo is missing one of its fields, which will break the program later on")
    })
    @PostMapping(path = "/todos/user/{username}")
    ResponseEntity<?> putTodoByUsername( @PathVariable String username, @RequestBody Todo todo){
        if(todo.getCalendar() == null || todo.getUsername() == null || todo.getTitle() == null){
            return ResponseEntity.status(403).body("todo is null");
        }
        User foundUser = userRepository.findByUsername(username);
        String usernameOfuser;
        try {
            usernameOfuser = foundUser.getUsername();
            if(foundUser.getLoggedIn() == false){
                return ResponseEntity.status(401).body("notLoggedIn");
            }
        } catch (NullPointerException x) {
            return ResponseEntity.status(404).body("user notFound");
        }
        try{
            String t = todoRepository.findByTitle(todo.getTitle()).getTitle();
            return ResponseEntity.status(402).body("todo with this name already exists");
        }
        catch (NullPointerException e){

            todo.setUser(foundUser);
            todo.setUsername(username);
            todoRepository.save(todo);
            foundUser.setTodos(todo);
            userRepository.save(foundUser);
            return ResponseEntity.status(200).body("success");
        }


    }


//    @Operation(summary = "creates a new Todo", description = "This will return a JSON of all of the ToDos in the given stack, which can be used to look at this information")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "successfully returned the list of todos in the stack", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class))),
//            @ApiResponse(responseCode = "400", description = "the provided todo was not found"),
//            @ApiResponse(responseCode = "400", description = "A Todo with this name already exists")
//    })
//    @PostMapping(path = "/todos")
//    ResponseEntity<?> createTodo(@RequestBody  Todo currentTodo){
//        if(currentTodo.getTitle() == null || currentTodo.getUsername() == null)
//            return ResponseEntity.status(400).body("failure");
//        User user = userRepository.findByUsername(currentTodo.getUsername());
//        currentTodo.setUser(user);
//        user.setTodos(currentTodo);
//        userRepository.save(user);
//        return ResponseEntity.status(200).body("success");
//    }



//    @Operation(summary = "Creates a new todo by username", description = "Creates a new todo by id, a todo object must be passed into this, this is more of a testing method than anything else")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "successfully created the todo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class))),
//    })
//    @PostMapping("/todos/{TodoID}/users/{username}/")
//    ResponseEntity<?> assignTodoToUser(@PathVariable String username,@PathVariable int TodoID){
//        User user = userRepository.findByUsername(username);
//        Todo todo = todoRepository.findById(TodoID);
//        if(user == null || todo == null)
//            return ResponseEntity.status(400).body("failure");
//        todo.setUser(user);
//        user.setTodos(todo);
//        userRepository.save(user);
//        return ResponseEntity.status(200).body("success");
//    }

    @Operation(summary = "Delete a Todo", description = "This is a method used to delete a todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully deleted the todo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "failure, test using smaller methods, this one is too big to debug")
    })
    @DeleteMapping(path = "/todos/id/{id}")
    ResponseEntity<?> deleteTodo(@PathVariable int id){
        todoRepository.deleteById(id);
        return ResponseEntity.status(200).body("success" );
    }


    @Operation(summary = "update a Todo", description = "This is a method used to update a todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated todo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "failure, test using smaller methods, this one is too big to debug")
    })
    @PutMapping("/todos/id/{id}")
    ResponseEntity<?> updateTodo(@PathVariable int id, @RequestBody Todo request){
        if(request.getTitle() == null || request.getUsername() == null)
            return ResponseEntity.status(200).body("failure");
        request.setId(id);
        todoRepository.save(request);
        return ResponseEntity.status(200).body("success");
    }

    @Operation(summary = "Deletes a todo with given name", description = "This method will take in the name of a todo and delete the todo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully deleted the todo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "todo with this title does not exist"),
    })
    @PostMapping(path = "/delete/todo/{todo}")
    ResponseEntity<?> deleteTodo(@PathVariable String todo){
        try{
            Todo modify = todoRepository.findByTitle(todo);
            User u = userRepository.findByUsername(modify.getUsername());
            List<Todo> removeArr = u.getTodos();
            removeArr.remove(modify);
            modify.setUser(null);
            modify.setStacksNull();
            //go throguh all todos in all stacks, check for modify
            List<Stack> s = stackRepository.findAll();
            for(int i =0; i < s.size(); i++){
                List<Todo> t = s.get(i).getTodos();
                for(int j = 0; j < t.size() ; j++){
                    if(t.get(j).getTitle().equals(modify.getTitle())){
                        t.remove(t.get(j));
                    }
                }
                s.get(i).setTodo(t);
                stackRepository.save(s.get(i));
            }
            List<Todo> tod = u.getTodos();
            for(int x = 0; x < tod.size(); x++){
                if(tod.get(x).getTitle().equals(modify.getTitle())){
                    tod.remove(tod.get(x));
                }
            }
            u.setTodoList(tod);
            userRepository.save(u);
            todoRepository.delete(modify);
            return ResponseEntity.status(200).body("success");
        }
        catch (NullPointerException e){
            return ResponseEntity.status(400).body("todo not found");
        }

    }

}

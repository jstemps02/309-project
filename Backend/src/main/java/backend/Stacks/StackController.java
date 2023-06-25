package backend.Stacks;

//    @Operation(summary = "", description = "")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
//            @ApiResponse(responseCode = "400", description = "")
//    })

import java.util.ArrayList;
import java.util.List;

import backend.Groups.Group;
import backend.Groups.GroupRepository;
import backend.Stacks.Stack;
import backend.Stacks.StackController;
import backend.Stacks.StackRepository;
import backend.Todos.TodoRepository;
import backend.Users.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.NonUniqueResultException;
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
import backend.Todos.Todo;
import org.springframework.web.method.support.InvocableHandlerMethod;



@RestController
public class StackController {

    @Autowired
    StackRepository stackRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    TodoRepository todoRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    private String alreadyMade = "{\"message\":\"user already made\"}";

    private String notFound = "{\"message\":\"user not found\"}";
    private String alreadyLoggedIn = "{\"message\":\"user already logged in\"}";
    private String notLoggedIn = "{\"message\":\"user not logged in\"}";

    private String todoMissing = "{\"message\":\"todo has a null value\"}";

    private String stackNameNotFound = "{\"message\":\"stack not found\"}";

    private String groupNameNotFound = "{\"message\":\"group not found\"}";

    private String stackOrGroupNotFound  = "{\"message\":\"group or stack not found\"}";

    @Operation(summary = "Lists all stacks in the database", description = "Uses the FindALl command to create a JSON object of all existing Stacks in the database at the current time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Stack list was successfully sent", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Stack.class))),
    })
    @GetMapping(path = "/stacks")
    ResponseEntity<?> getAllStacks(){
        return ResponseEntity.status(200).body(stackRepository.findAll());
    }

    @Operation(summary = "Lists stack in the database by id", description = "Uses the FindByID command to create a JSON object of the Stack in the database at the given id at the current time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The User list was successfully sent", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Stack.class))),
    })
    @GetMapping(path = "/stacks/id/{id}")
    ResponseEntity<?> getStackById( @PathVariable int id){
        return ResponseEntity.status(200).body(stackRepository.findById(id));
    }


    @Operation(summary = "Lists of the stacks of a given user", description = "Finds the user by the username that is given in the URL, then gets all of the stacks of that user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned a list of stacks of the given user users and all other data included in said stacks via JSON", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "The username was not found")
    })
    @GetMapping(path = "/users/stacks/{username}")
    ResponseEntity<?> getAllUserStacks(@PathVariable String username){
        try{
            User us = userRepository.findByUsername(username);
            List<Stack> StackList = userRepository.findByUsername(username).getStack();
            List<Stack> returnVal = new ArrayList<Stack>();
            for(int i = 0; i < StackList.size(); i++){
                returnVal.add(StackList.get(i));
            }
            return ResponseEntity.status(200).body(returnVal);
        }
        catch(NullPointerException e){
            return ResponseEntity.status(400).body("User not found");
        }

    }


    @Operation(summary = "Gets the details of all of the Todos in a stack", description = "This will return a JSON of all of the ToDos in the given stack, which can be used to look at this information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully returned the list of todos in the stack", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "the provided stackname was not found")
    })
    @GetMapping(path = "/stacks/{stackname}")
    ResponseEntity<?> getAllTodoinStack(@PathVariable String stackname){
        try{
            Stack s = stackRepository.findByTitle(stackname);
            List<Todo> returnVal = s.getTodos();
            return ResponseEntity.status(200).body(returnVal);
        }
        catch(NullPointerException e){
            return ResponseEntity.status(401).body("stack not found");
        }
    }


    @Operation(summary = "Created a new Group, Stack, and Todo under the ownership of the given username", description = "This is a massive method, which will create a new group with one user (the owner), that group will have a new stack created with a new Todo, which is passed in via JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully created group, stack, and todo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "failure, test using smaller methods, this one is too big to debug"),
            @ApiResponse(responseCode = "401", description = "group with this title already exists")
    })
    @PostMapping(path = "stack/{username}/newStack/{stackName}/{groupTitle}/")
    ResponseEntity<?> newStackByUsername(@PathVariable String username, @PathVariable String stackName, @PathVariable String groupTitle, @RequestBody Todo todo){
        if(todo.getCalendar() == null || todo.getUsername() == null || todo.getTitle() == null){
            return ResponseEntity.status(401).body("todo missing");
        }
        try{
            User foundUser = userRepository.findByUsername(username);

            String usernameOfuser;


            Group newGroup = new Group(groupTitle, foundUser.getUsername());
            List<Group> allGroups = groupRepository.findAll();
            for(int i = 0; i < allGroups.size(); i++){ //NICK
                if(groupTitle.equals(allGroups.get(i).getGroupname())){
                    return ResponseEntity.status(401).body("group with this title already exists");
                }
            }
            List<Todo> newTodoList= new ArrayList<Todo>();
            newTodoList.add(todo);
            Stack newStack = new Stack(stackName, true, newTodoList, newGroup);
            newStack.addTodos(todo);
            newGroup.addStack(newStack);
            newGroup.addUser(foundUser);
            foundUser.addStack(newStack);
            groupRepository.save(newGroup);
            todoRepository.save(todo);
            userRepository.save(foundUser);
            //stackRepository.save(newStack);
        }
        catch (NullPointerException e){
            return ResponseEntity.status(400).body("failure");
        }


        return ResponseEntity.status(200).body("success");
    }

    @Operation(summary = "Adds a Todo to an existing stack", description = "this will add a Todo to a stack, which will both create the Todo for the user that makes it and adds it to the Todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully added a ToDo to the stack", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "The provided stack-name did not resolve to a stack in the database"),
            @ApiResponse(responseCode = "404", description = "The given Todo is missing one of its fields, which will break the program later on")
    })
    @PostMapping(path = "/addTodo/{stackname}")
    ResponseEntity<?> addTodoToStack(@PathVariable String stackname, @RequestBody Todo todo){
        if(todo.getCalendar() == null || todo.getUsername() == null || todo.getTitle() == null){
            return ResponseEntity.status(404).body("todo missing");
        }

        try{
            Stack modify = stackRepository.findByTitle(stackname);
            modify.addTodos(todo);

            stackRepository.save(modify);
        }
        catch(NullPointerException e){
            return ResponseEntity.status(401).body("stackname not found");
        }

        return ResponseEntity.status(200).body("success");
    }

    @Operation(summary = "Adds an empty stack to a group", description = "Passed in a Stack object as a JSON, this will create an empty stack for the group given, effectively, this makes an empty stack with no ToDos currently in it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully created the empty stack", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "stack with this title already exists"),
            @ApiResponse(responseCode = "402", description = "the given group-name was not found")
    })
    @PostMapping(path = "/stacks/groups/{groupName}")
    ResponseEntity<?> addEmptyStackToGroup(@PathVariable String groupName, @RequestBody Stack title){
        if(title == null){
            return ResponseEntity.status(401).body("Stack is null");
        }
        try{
            Group modify = groupRepository.findByGroupname(groupName);
            for(int i = 0; i < modify.getStacks().size(); i++){
                if((title).equals(modify.getStacks().get(i).getTitle())){
                    return ResponseEntity.status(400).body("stack with this title already exists");
                }
            }
            List<Todo> t = new ArrayList<Todo>();
            Stack s = new Stack(title.getTitle(), true, t, modify);
            modify.addStack(s);
            groupRepository.save(modify);
            //stackRepository.save(s);
            return ResponseEntity.status(200).body("success");
        }
        catch (NullPointerException e) {
            return ResponseEntity.status(402).body("group-name not found");
        }
    }
    @Operation(summary = "Deletes a stack with given name TODOS WILL NOT BE DELETED", description = "This method will take in a stackname and delete the stack without deleting the ToDos in said stack.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully deleted the empty stack", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "stack with this title does not exist"),
    })
    @PostMapping(path = "/delete/stack/{stackname}")
    ResponseEntity<?> deleteStack(@PathVariable String stackname){

        try{
            Stack modify = stackRepository.findByTitle(stackname);
            modify.setGroup(null);
            modify.setTodoNull();
            List<Group> g = groupRepository.findAll();
            for(int i = 0 ; i < g.size(); i++){
                List<Stack> s = g.get(i).getStacks();
                for(int j = 0; j < s.size(); j++){
                    if(s.get(j).getTitle().equals(modify.getTitle())){
                        s.remove(s.get(j));
                    }
                }
                g.get(i).setStack(s);
                groupRepository.save(g.get(i));
            }
            List<User> u = userRepository.findAll();
            for(int x = 0; x < u.size(); x++){
                List<Stack> s = u.get(x).getStack();
                for(int y = 0; y < s.size(); y++){
                    if(s.get(y).getTitle().equals(modify.getTitle())){
                        s.remove(s.get(y));
                    }
                }
                u.get(x).setStack(s);
                userRepository.save(u.get(x));
            }

            stackRepository.delete(modify);
            return ResponseEntity.status(200).body("success");
        }
        catch (NullPointerException e){
            return ResponseEntity.status(400).body("stack-name not found");
        }

    }





    @Operation(summary = "Adds a new Stack to a Group with an initial ToDo", description = "This method is very similar to the method that adds an empty stack to a group but this one also has an initial Todo right after creation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully added a stack to the group", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "stack with this title already exists"),
            @ApiResponse(responseCode = "403", description = "The stackname or the groupname did not resolve to anything"),
            @ApiResponse(responseCode = "404", description = "The given ToDo via JSON was missing a field")
    })
    @PostMapping(path = "/stacks/{stackName}/groups/{groupName}")
    ResponseEntity<?> addStackToGroup(@PathVariable String groupName, @PathVariable String stackName, @RequestBody Todo todo){
        if(todo.getCalendar() == null || todo.getUsername() == null || todo.getTitle() == null){
            return ResponseEntity.status(404).body("todo missing");
        }
        try{
            todo.setIsActive(true);
            Group modify = groupRepository.findByGroupname(groupName);
            for(int i = 0; i < modify.getStacks().size(); i++){
                if(stackName.equals(modify.getStacks().get(i).getTitle())){
                    return ResponseEntity.status(400).body("stack with this title already exists");
                }
            }
            List<Todo> t = new ArrayList<Todo>();
            Stack s = new Stack(stackName, true, t, modify);
            s.addTodos(todo);
            //add try catch if modify is null below
            modify.addStack(s);
            groupRepository.save(modify);
            //stackRepository.save(s);
        }
        catch(NullPointerException e){
            return ResponseEntity.status(403).body("stackname or groupname not found");
        }

        return ResponseEntity.status(200).body("success");
    }
    @Operation(summary = "Delete a stack", description = "This is a method used to delete a stack by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully deleted the todo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "failure, test using smaller methods, this one is too big to debug")
    })
    @DeleteMapping(path = "/stack/id/{id}")
    ResponseEntity<?> deleteStack(@PathVariable int id){
        stackRepository.deleteById(id);
        return ResponseEntity.status(200).body("success");
    }


    @Operation(summary = "update a stack", description = "This is a method used to update a stack by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully updated stack", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "failure")
    })
    @PutMapping("/stack/id/{id}")
    ResponseEntity<?> updateStack(@PathVariable int id, @RequestBody Stack request){
        Stack stack = stackRepository.findById(id);
        if(request.getTitle() == null){
            return ResponseEntity.status(400).body("failure");
        }
        request = stack;
        stackRepository.save(request);
        return ResponseEntity.status(200).body("success");

    }


}
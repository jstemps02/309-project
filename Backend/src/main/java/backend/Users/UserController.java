package backend.Users;


//    @Operation(summary = "", description = "")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
//            @ApiResponse(responseCode = "400", description = "")
//    })

import java.util.ArrayList;
import java.util.List;

import backend.Groups.Group;
import backend.MD5;
import backend.Todos.Todo;
import backend.Todos.TodoController;
import backend.Todos.TodoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import backend.Users.User;
import org.springframework.web.method.support.InvocableHandlerMethod;

import javax.validation.constraints.Null;

import static backend.MD5.getMd5;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TodoRepository todoRepository;


    @Operation(summary = "Lists all users in the database", description = "Uses the FindALl command to create a JSON object of all existing Users in the database at the current time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The User list was successfully sent", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
    })
    @GetMapping(path = "/users")
    ResponseEntity<?> getAllUsers(){
        return ResponseEntity.status(200).body(userRepository.findAll());
    }

    @Operation(summary = "Gets the data of all logged in users", description = "This returns the same information as get @/users, but this only returns logged in users and their information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned a list of logged in users and all other data via JSON", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "An error occurred")
    })
    @GetMapping(path = "/login")
    ResponseEntity<?> getLoggedIn(){
        try{
            List<User> userArr=  userRepository.findAll();
            List<User> loggedIn = new ArrayList<User>();
            for(int i = 0; i < userArr.size(); i++){
                if(userArr.get(i).getLoggedIn() == true){
                    loggedIn.add(userArr.get(i));
                }
            }
            return ResponseEntity.status(200).body(loggedIn);
        }
        catch(NullPointerException e){
            return ResponseEntity.status(400).body("Error");
        }
    }

    @Operation(summary = "Returns the password of a user", description = "When passed in a username via JSON, this will return the user's password hash")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned the user and returned the username", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "if the passed in username is not found")
    })
    @GetMapping(path = "/users/{username}")
    ResponseEntity<?> getPassByName( @PathVariable String username){
        try{
            User userLogin = userRepository.findByUsername(username);
            if(userLogin.getLoggedIn() == false){
                return ResponseEntity.status(401).body("Not logged in");
            }
            return ResponseEntity.status(200).body(userLogin.getPassword());
        }
        catch(NullPointerException e){
            return ResponseEntity.status(400).body("failure");
        }
    }

    @Operation(summary = "Creates a User Object", description = "When passed in a user object via JSON, this will create a new user on the database, a username, password, and isActive fields are needed where first 2 are strings and isActive is a boolean")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the User.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "302", description = "The user already exists"),
            @ApiResponse(responseCode = "406", description = "if the passed in user object is null.")
    })
    @PostMapping(path = "/users")
    ResponseEntity<?> createUser(@RequestBody User user){
        if (user.getUsername() == null || user.getPassword() == null)
            return ResponseEntity.status(406).body("User is null");
        try{
            String existingUser = userRepository.findByUsername(user.getUsername()).getUsername();
        }
        catch(NullPointerException e) {
            String pass = MD5.getMd5(user.getPassword());
            User use = new User(user.getUsername(), pass);
            userRepository.save(use);
            return ResponseEntity.status(200).body("success");
        }

        return ResponseEntity.status(302).body("user already exists");
    }


    @Operation(summary = "Deletes a User Object by name", description = "When passed in a user object via JSON, this will delete said user on the database, a username, password, and isActive fields are needed where first 2 are strings and isActive is a boolean")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the User", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "302", description = "The user does not exist"),
            @ApiResponse(responseCode = "406", description = "if the passed in user object is null.")
    })
    @PostMapping(path = "/users/delete")
    ResponseEntity<?> deleteUserByName(@RequestBody User user){
        if (user.getUsername() == null || user.getPassword() == null)
            return ResponseEntity.status(406).body("User is null");
        try{
            String existingUser = userRepository.findByUsername(user.getUsername()).getUsername();
            int id =  userRepository.findByUsername(user.getUsername()).getId();
            userRepository.deleteById(id);
            return ResponseEntity.status(200).body("success");
        }
        catch(NullPointerException e) {
            return ResponseEntity.status(302).body("user does not exists");

        }

    }
    @Operation(summary = "Logs in the user via username", description = "Logs in the username passed into the function via the JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in the user.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "300", description = "The given user was already logged in"),
            @ApiResponse(responseCode = "404", description = "The user was not found"),
            @ApiResponse(responseCode = "400", description = "login was not a success.")
    })
    @PostMapping(path = "/login")
    ResponseEntity<?> loginUser(@RequestBody User username) {
        User foundUser = userRepository.findByUsername(username.getUsername());

        String pass;
        try {
            if(foundUser.getLoggedIn() == true){
                return ResponseEntity.status(300).body("user already logged in");
            }
            pass = foundUser.getPassword();
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body("User not found");
        }
        String hash = getMd5(username.getPassword());
        if (hash.equals(pass)) {
            foundUser.setLogIn();
            userRepository.save(foundUser);
            return ResponseEntity.status(200).body("success");
        }

        return ResponseEntity.status(400).body("failure");

    }

    @Operation(summary = "Creates a User Object", description = "When passed in a user object via JSON, this will create a new user on the database, a username, password, and isActive fields are needed where first 2 are strings and isActive is a boolean")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged out the user.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "300", description = "The given user was already not logged in", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "The user was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "login was not a success.")
    })
    @PostMapping(path = "/logout")
    ResponseEntity<?> logoutUser(@RequestBody User username) {

        User foundUser = userRepository.findByUsername(username.getUsername());

        String thisUsername;
        try {
            thisUsername = foundUser.getUsername();
            if(foundUser.getLoggedIn() == false){
                return ResponseEntity.status(300).body("user not logged in");
            }
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body("User not found");
        }
        try{
            foundUser.setLogOut();
            userRepository.save(foundUser);
            return ResponseEntity.status(200).body("success");
        }
        catch (NullPointerException e) {
            return ResponseEntity.status(400).body("failure");
        }

    }
    @Operation(summary = "Puts a new user at the given id", description = "When passed in a user object via JSON, this will create a new user on the database, a username, password, and isActive fields are needed where first 2 are strings and isActive is a boolean")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success message upon completion", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    })
    @PutMapping("/users/{id}")
    ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User request){
        User user = userRepository.findById(id);
        if(request.getUsername() == null || request.getPassword() == null)
            return ResponseEntity.status(400).body("failure");
        user = request;
        userRepository.save(user);
        return ResponseEntity.status(200).body("success");
    }
    @Operation(summary = "Deletes a user at a given id", description = "Takes in the given id and deletes the user at that id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success message upon completion", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    })
    @DeleteMapping(path = "/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        return ResponseEntity.status(200).body("success");
    }

}
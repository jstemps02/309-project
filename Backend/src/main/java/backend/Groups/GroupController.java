package backend.Groups;


//    @Operation(summary = "", description = "")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
//            @ApiResponse(responseCode = "400", description = "")
//    })

import java.util.ArrayList;
import java.util.List;

import backend.Groups.Group;
import backend.Groups.GroupController;
import backend.Groups.GroupRepository;
import backend.Stacks.Stack;
import backend.Stacks.StackRepository;
import backend.Todos.Todo;
import backend.Users.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import backend.Users.User; // posibbly need this for users in a group
import org.springframework.web.method.support.InvocableHandlerMethod;

//import static backend.MD5.getMd5;

@RestController
public class GroupController {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    StackRepository stackRepository;

    @Autowired
    UserRepository userRepository;
//    @Autowired
//    TodoRepository todoRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    private String alreadyMade = "{\"message\":\"group already made\"}";

    private String notFound = "{\"message\":\"group not found\"}";
    private String alreadyExistent = "{\"message\":\"group already exists\"}";
    private String notLoggedIn = "{\"message\":\"User does not exist\"}";

    @Operation(summary = "Lists all groups in the database", description = "Uses the FindALl command to create a JSON object of all existing Groups in the database at the current time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Group list was successfully sent", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))),
    })
    @GetMapping(path = "/groups")
    ResponseEntity<?> getAllGroups(){
        return ResponseEntity.status(200).body(groupRepository.findAll());
    }

    @Operation(summary = "Lists group in the database by id", description = "Uses the FindByID command to create a JSON object of the Groups in the database at the given id at the current time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Group list was successfully sent", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))),
    })
    @GetMapping(path = "/groups/id/{id}")
    ResponseEntity<?> getGroupById( @PathVariable int id){
        return ResponseEntity.status(200).body(groupRepository.findById(id));
    }

    @Operation(summary = "Lists group in the database by groupname", description = "Uses the FindByID command to create a JSON object of the Group in the database at the given id at the current time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Group list was successfully sent", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))),
    })
    @GetMapping(path = "/groups/{groupname}")
    ResponseEntity<?> getUserGroups(@PathVariable String groupname){
        Group userLogin = groupRepository.findByGroupname(groupname);
        return ResponseEntity.status(200).body(userLogin);
    }


    @Operation(summary = "Created a new Group under the ownership of the given username", description = "This method is used by the group controller to create a new group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully created group", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))),
            @ApiResponse(responseCode = "400", description = "failed to create Group")
    })
    @PostMapping(path = "/groups/user/{username}")
    ResponseEntity<?> newGroupByUsername(@PathVariable String username, @RequestBody Group groupname){

        String name = groupname.getGroupname();
        User foundUser = userRepository.findByUsername(username);
        String usernameOfUser;
        try{
            Group gru = groupRepository.findByGroupname(groupname.getGroupname());
            String groupsName = gru.getGroupname();
        }
        catch(NullPointerException e){
            try {
                usernameOfUser = foundUser.getUsername();
                if(foundUser.getLoggedIn() == false){
                    return ResponseEntity.status(404).body("not logged in");
                }
            } catch (NullPointerException f) {
                return ResponseEntity.status(404).body("notFound");
            }

                Group newGroup = new Group(groupname.getGroupname(), foundUser.getUsername());
                newGroup.addUser(foundUser);
                userRepository.save(foundUser);
                groupRepository.save(newGroup);
                return ResponseEntity.status(200).body("success");

        }
        return ResponseEntity.status(404).body("group with this name already exists");
    }

    @Operation(summary = "Adds a user to a group", description = "Takes in a groupname and the username of the owner of the group and updates the group based on those parameters. Used to update a group when it is changed by other methods.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully created the group", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))),
            @ApiResponse(responseCode = "400", description = "update failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))),
    })
    @PostMapping("/groups/{groupname}/{username}")
    ResponseEntity<?> addUserToGroup(@PathVariable String groupname, @PathVariable String username){
        try {
            Group foundGroup = groupRepository.findByGroupname(groupname);
            User foundUser = userRepository.findByUsername(username);
            System.out.println(foundUser.getUsername());
            System.out.println(foundGroup.getOwner());
            if (foundUser.getUsername().equals(foundGroup.getOwner())) {
                return ResponseEntity.status(400).body("invalid user");}
            foundGroup.addUser(foundUser);
            groupRepository.save(foundGroup);
            userRepository.save(foundUser);
            return ResponseEntity.status(200).body("success");
        } catch (NullPointerException e) {
            return ResponseEntity.status(400).body("invalid input");
        }
    }
//d
    //CURRENTLY DOES NOT WORK, WILL DELETE USER WITH THE GROUP
    @Operation(summary = "Deletes a group with given name STACKS WILL BE DELETED", description = "This method will take in a groupname and delete the group and all stacks without deleting the ToDos in said stack.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully deleted the group and all internal stacks", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "group with this title does not exist"),
    })
    @PostMapping(path = "/delete/group/{groupname}")
    ResponseEntity<?> deleteGroup(@PathVariable String groupname){
        try{
            Group modify = groupRepository.findByGroupname(groupname);
            if(modify.getStacks().size() != 0){
                return ResponseEntity.status(405).body("Stacks still found in group, delete all stacks before removing the group");
            }
            System.out.println(modify.getGroupname());
            modify.setStackNull();
            modify.setUserlistNull();

            groupRepository.delete(modify);
            return ResponseEntity.status(200).body("success");
        }
        catch (NullPointerException e){
            return ResponseEntity.status(400).body("group-name not found");
        }
    }

    @Operation(summary = "Removes a user to a group", description = "Takes in a groupname and the username of the owner of the group and updates the group based on those parameters. Used to update a group when it is changed by other methods.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully removed user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))),
            @ApiResponse(responseCode = "400", description = "update failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))),
    })
    @PostMapping("/groups/remove/{groupname}/{username}")
    ResponseEntity<?> removeUserFromGroup(@PathVariable String groupname, @PathVariable String username){
        try {
            Group foundGroup = groupRepository.findByGroupname(groupname);
            User foundUser = userRepository.findByUsername(username);
            if (foundUser.getUsername().equals(foundGroup.getOwner())) {
                return ResponseEntity.status(400).body("invalid user");}
            foundGroup.removeUser(foundUser);
            groupRepository.save(foundGroup);
            userRepository.save(foundUser);
            return ResponseEntity.status(200).body("success");
        } catch (NullPointerException e) {
            return ResponseEntity.status(400).body("invalid input");
        }
    }

    @Operation(summary = "Transfers group ownership to new user", description = "Takes in a groupname and the username of the owner of the group and updates the group based on those parameters. Used to update a group when it is changed by other methods.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully changed owner", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))),
            @ApiResponse(responseCode = "400", description = "update failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))),
    })
    @PutMapping("/groups/{groupname}/{username}")
    ResponseEntity<?> changeOwnerGroup(@PathVariable String groupname, @PathVariable String username){
        try {
            Group foundGroup = groupRepository.findByGroupname(groupname);
            User foundUser = userRepository.findByUsername(username);
            if (foundUser.getUsername().equals(foundGroup.getOwner())) {
                return ResponseEntity.status(400).body("invalid user");}
            foundGroup.setOwner(foundUser.getUsername());
            groupRepository.save(foundGroup);
            userRepository.save(foundUser);
            return ResponseEntity.status(200).body("success");
        } catch (NullPointerException e) {
            return ResponseEntity.status(400).body("invalid input");
        }
    }
}
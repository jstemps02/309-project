package backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import backend.Groups.Group;
import backend.Todos.Todo;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.response.ResponseBody;
import org.json.*;
import org.hamcrest.Matchers.*;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import backend.Users.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.sql.Array;
import java.util.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class TestingSystemTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }




    //-------------------------------------------------------------------------
    //GROUP TESTS - NICK
    //-------------------------------------------------------------------------

    //GROUP CONTROLLER TEST -> NICK WROTE ("Why are we yelling!?" -Brick)
    @Test // test 1
    public void getGroupTest() {

        Response getAllGroups1 = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                when().
                get("/groups");


        // Check status code
        int statusCode1 = getAllGroups1.getStatusCode();
        assertEquals(200, statusCode1);

        // deletes group created for test
        ResponseBody newGroup2a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/Nick_Test_Group").
                thenReturn().body();
    }

    @Test // test 2
    public void createGroupTest() {

        // checks if user "a" is logged in
        Map<String, Object> mapInit2 = new HashMap<>();
        mapInit2.put("username", "a");
        mapInit2.put("password", "a");
        ResponseBody responseInInit = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit2).
                when().
                post("/login").
                thenReturn().body();

        Map<String, Object> map2 = new HashMap<>();
        map2.put("groupname", "test_group");
        ResponseBody newGroup2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map2).
                when().
                post("/groups/user/a").
                thenReturn().body();
        String answer2 = newGroup2.asString();
        assertEquals("success", answer2);

        Response getGroup2 = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                when().
                get("/groups/test_group");

        int statusCode0 = getGroup2.getStatusCode();
        assertEquals(200, statusCode0);

        // deletes group created for test
        ResponseBody newGroup2a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/test_group").
                thenReturn().body();
        String answer2a = newGroup2a.asString();
        assertEquals("success", answer2a);
    }
    @Test // test 3
    public void createGroupTwiceTest() {
        // checks if user "a" is logged in
        Map<String, Object> mapInit3a = new HashMap<>();
        mapInit3a.put("username", "a");
        mapInit3a.put("password", "a");
        ResponseBody responseInInit = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit3a).
                when().
                post("/login").
                thenReturn().body();

        Map<String, Object> map3a = new HashMap<>();
        map3a.put("groupname", "test_group");
        ResponseBody newGroup3a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map3a).
                when().
                post("/groups/user/a").
                thenReturn().body();
        String answer3a = newGroup3a.asString();
        assertEquals("success", answer3a);


        ResponseBody newGroup3b = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map3a).
                when().
                post("/groups/user/a").
                thenReturn().body();
        String answer3b = newGroup3b.asString();
        assertEquals("group with this name already exists", answer3b);

        // deletes group created for test
        ResponseBody newGroup3c = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/test_group").
                thenReturn().body();
        String answer3c = newGroup3c.asString();
        assertEquals("success", answer3c);
    }
    @Test // test 4
    public void createGroupNoUserTest() {

        Map<String, Object> map4 = new HashMap<>();
        map4.put("groupname", "test_group");
        ResponseBody responseCreate4 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map4).
                when().
                post("/groups/user/NoSuchPerson").
                thenReturn().body();
        assertEquals("notFound", responseCreate4.asString());

        // deletes group created for test
        ResponseBody newGroup2a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/test_group").
                thenReturn().body();

    }
    @Test // test 5
    public void userLoggedOutTest() {

        // checks if user "a" is logged in
        Map<String, Object> mapInit5a = new HashMap<>();
        mapInit5a.put("username", "a");
        ResponseBody responseInInit5 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit5a).
                when().
                post("/logout").
                thenReturn().body();

        Map<String, Object> map5a = new HashMap<>();
        map5a.put("groupname", "test_group");
        ResponseBody newGroup5a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map5a).
                when().
                post("/groups/user/a").
                thenReturn().body();
        String answer5a = newGroup5a.asString();
        assertEquals("not logged in", answer5a);

        // deletes group created for test
        ResponseBody newGroup2a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/test_group").
                thenReturn().body();
    }
    @Test // test 6
    public void updateGroupByIDFailTest() {

        Map<String, Object> map6a = new HashMap<>();
        map6a.put("groupname", "test_group");
        ResponseBody test6a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map6a).
                when().
                post("/groups/id/1").
                thenReturn().body();
        String answer6a = test6a.asString();
        assertEquals("invalid input", answer6a);
    }

    @Test // test 7
    public void getIDTest() {

        Response getAllGroups7 = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                when().
                get("/groups/id/1");


        // Check status code
        int statusCode7= getAllGroups7.getStatusCode();
        assertEquals(200, statusCode7);
    }

    @Test // test 8
    public void deleteFailTest() {

        // checks if user "a" is logged in
        Map<String, Object> mapInit7a = new HashMap<>();
        mapInit7a.put("username", "a");
        mapInit7a.put("password", "a");
        ResponseBody responseInInit6a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit7a).
                when().
                post("/login").
                thenReturn().body();

        // deletes group created for test
        ResponseBody newGroup8a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/bogus").
                thenReturn().body();
        String answer8a = newGroup8a.asString();
        assertEquals("group-name not found", answer8a);

        // deletes group created for test
        ResponseBody newGroup2a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/test_group").
                thenReturn().body();
    }

    @Test // test 9
    public void addUserToGroupTest() {

        // checks if user "a" is logged in
        Map<String, Object> mapInit9a = new HashMap<>();
        mapInit9a.put("username", "a");
        mapInit9a.put("password", "a");
        ResponseBody responseInInit9a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit9a).
                when().
                post("/login").
                thenReturn().body();

        // checks if user "b" is logged in
        Map<String, Object> mapInit9b = new HashMap<>();
        mapInit9b.put("username", "b");
        mapInit9b.put("password", "b");
        ResponseBody responseInInit9b = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit9b).
                when().
                post("/login").
                thenReturn().body();

        Map<String, Object> map9c = new HashMap<>();
        map9c.put("groupname", "test_group");
        ResponseBody test9c = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map9c).
                when().
                post("/groups/user/a").
                thenReturn().body();
        String answer9c = test9c.asString();
        assertEquals("success", answer9c);

        ResponseBody test9d = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/groups/test_group/a").
                thenReturn().body();
        String answer9d = test9d.asString();
        assertEquals("invalid user", answer9d);

        ResponseBody test9e = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/groups/test_group/b").
                thenReturn().body();
        String answer9e = test9e.asString();
        assertEquals("success", answer9e);


        // deletes group created for test
        ResponseBody test9f = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/test_group").
                thenReturn().body();
    }
    @Test // test 10
    public void addUserFailTest() {

        // checks if user "a" is logged in
        Map<String, Object> mapInit10a = new HashMap<>();
        mapInit10a.put("username", "a");
        mapInit10a.put("password", "a");
        ResponseBody responseInInit10a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit10a).
                when().
                post("/login").
                thenReturn().body();

        Map<String, Object> map10c = new HashMap<>();
        map10c.put("groupname", "test_group");
        ResponseBody test9c = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map10c).
                when().
                post("/groups/user/a").
                thenReturn().body();
        String answer10c = test9c.asString();
        assertEquals("success", answer10c);


        ResponseBody test10e = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/groups/test_group/NoSuchUser").
                thenReturn().body();
        String answer10e = test10e.asString();
        assertEquals("invalid input", answer10e);


        // deletes group created for test
        ResponseBody test10f = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/test_group").
                thenReturn().body();
    }

    @Test // test 11
    public void removeUserFromGroupTest() {

        // checks if user "a" is logged in
        Map<String, Object> mapInit9a = new HashMap<>();
        mapInit9a.put("username", "a");
        mapInit9a.put("password", "a");
        ResponseBody responseInInit9a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit9a).
                when().
                post("/login").
                thenReturn().body();


        Map<String, Object> map9c = new HashMap<>();
        map9c.put("groupname", "test_group");
        ResponseBody test9c = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map9c).
                when().
                post("/groups/user/a").
                thenReturn().body();
        String answer9c = test9c.asString();
        assertEquals("success", answer9c);

        ResponseBody test9d = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/groups/remove/test_group/a").
                thenReturn().body();
        String answer9d = test9d.asString();
        assertEquals("invalid user", answer9d);

        ResponseBody test9e = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/groups/remove/test_group/b").
                thenReturn().body();
        String answer9e = test9e.asString();
        assertEquals("success", answer9e);

        ResponseBody test10e = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/groups/remove/test_group/NoSuchUser").
                thenReturn().body();
        String answer10e = test10e.asString();
        assertEquals("invalid input", answer10e);


        // deletes group created for test
        ResponseBody test9f = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/test_group").
                thenReturn().body();
    }

    @Test // test 12
    public void changeOwnerTest() {

        // checks if user "a" is logged in
        Map<String, Object> mapInit12a = new HashMap<>();
        mapInit12a.put("username", "a");
        mapInit12a.put("password", "a");
        ResponseBody responseInInit9a = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit12a).
                when().
                post("/login").
                thenReturn().body();

        Map<String, Object> mapInit12b = new HashMap<>();
        mapInit12b.put("username", "b");
        mapInit12b.put("password", "b");
        ResponseBody responseInInit12b = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit12b).
                when().
                post("/login").
                thenReturn().body();


        Map<String, Object> map12c = new HashMap<>();
        map12c.put("groupname", "test_group");
        ResponseBody test12c = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map12c).
                when().
                post("/groups/user/a").
                thenReturn().body();
        String answer12c = test12c.asString();
        assertEquals("success", answer12c);

        ResponseBody test12d = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                put("/groups/test_group/a").
                thenReturn().body();
        String answer12d = test12d.asString();
        assertEquals("invalid user", answer12d);

        ResponseBody test12e = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                put("/groups/test_group/b").
                thenReturn().body();
        String answer12e = test12e.asString();
        assertEquals("success", answer12e);

        ResponseBody test12f = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                put("/groups/test_group/NoSuchUser").
                thenReturn().body();
        String answer12f = test12f.asString();
        assertEquals("invalid input", answer12f);


        // deletes group created for test
        ResponseBody test12g = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/test_group").
                thenReturn().body();
    }

    //-------------------------------------------------------------------------
    // END OF GROUP TESTS - NICK
    //-------------------------------------------------------------------------

    //USER CONTROLLER TEST -> JUSTIN WROTE
    @Test
    public void create_deleteTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "test1");
        ResponseBody responseCreate1 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post("/users").
                thenReturn().body();
        assertEquals("User is null", responseCreate1.asString());

        map.put("password", "test1");
        ResponseBody responseCreate2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post("/users").
                thenReturn().body();
        assertEquals("success", responseCreate2.asString());

        ResponseBody responseCreate3 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post("/users").
                thenReturn().body();
        assertEquals("user already exists", responseCreate3.asString());

        ResponseBody responseDelete1 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post("/users/delete").
                thenReturn().body();
        assertEquals("success", responseCreate2.asString());
        ResponseBody responseDelete2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post("/users/delete").
                thenReturn().body();
        assertEquals("user does not exists", responseDelete2.asString());
        Map<String, Object> mapFail = new HashMap<>();
        mapFail.put("username", "test1");
        ResponseBody responseDelete3 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapFail).
                when().
                post("/users/delete").
                thenReturn().body();
        assertEquals("User is null", responseDelete3.asString());
    }
    //USER CONTROLLER TEST -> JUSTIN WROTE
    @Test
    public void login_logoutTest() {
        Map<String, Object> mapInit = new HashMap<>();
        mapInit.put("username", "a");
        mapInit.put("password", "a");
        ResponseBody responseInInit = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/login").
                thenReturn().body();

        Map<String, Object> map = new HashMap<>();
        map.put("username", "a");
        ResponseBody responseOut1 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post("/logout").
                thenReturn().body();

        assertEquals("success", responseOut1.asString());

        ResponseBody responseOut2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post("/logout").
                thenReturn().body();

        assertEquals("user not logged in", responseOut2.asString());
        map.put("password", "a");
        ResponseBody responseIn1 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post("/login").
                thenReturn().body();

        assertEquals("success", responseIn1.asString());

        ResponseBody responseIn2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post("/login").
                thenReturn().body();

        assertEquals("user already logged in", responseIn2.asString());


        Map<String, Object> mapFail = new HashMap<>();
        mapInit.put("username", "meanttofail14314");
        ResponseBody responseOutFail = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapFail).
                when().
                post("/login").
                thenReturn().body();
        assertEquals("User not found", responseOutFail.asString());
        mapInit.put("password", "meanttofail213134");
        ResponseBody responseInFail = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapFail).
                when().
                post("/logout").
                thenReturn().body();
        assertEquals("User not found", responseInFail.asString());


    }

    //USER CONTROLLER TEST -> JUSTIN WROTE
    @Test
    public void put_deleteTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "tobedeleted");
        ResponseBody responsePutFail = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                put("/users/99").
                thenReturn().body();
        assertEquals("failure", responsePutFail.asString());
        map.put("password", "123");
        map.put("isActive", true);
        ResponseBody responsePut = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                put("/users/99").
                thenReturn().body();
        assertEquals("success", responsePut.asString());

        ResponseBody responseDelete = RestAssured.given().
                delete("/users/99").
                thenReturn().body();

        assertEquals("success", responseDelete.asString());


        ResponseBody responseDelete4 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post("/users/delete").
                thenReturn().body();
        assertEquals("success", responseDelete4.asString());

    }

    //USER CONTROLLER TEST -> JUSTIN WROTE
    @Test
    public void getUserTest() {
        // Send request and receive response
        Response getAllUsers = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                when().
                get("/users");


        // Check status code
        int statusCode1 = getAllUsers.getStatusCode();
        assertEquals(200, statusCode1);


        Response getLoggedIn = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                when().
                get("/login");
        // Check status code
        int statusCode2 = getLoggedIn.getStatusCode();
        assertEquals(200, statusCode2);

        Response getLoggedInUsers = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                when().
                get("/users/a");


        // Check status code
        int statusCode3 = getLoggedInUsers.getStatusCode();
        assertEquals(200, statusCode3);
    }

    //TESTING GET METHODS FOR STACK -> JUSTIN WROTE
    @Test
    public void getStackTest() {
        // Send request and receive response
        Response getAllStacks = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                when().
                get("/stacks");
        int statusCode1 = getAllStacks.getStatusCode();
        assertEquals(200, statusCode1);
        // Send request and receive response
        Response getUserStacks = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                when().
                get("/users/stacks/a");
        int statusCode2 = getUserStacks.getStatusCode();
        assertEquals(200, statusCode2);

        Response getUserStacks2 = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                when().
                get("/users/stacks/supposedToFail");
        int statusCode3 = getUserStacks2.getStatusCode();
        assertEquals(400, statusCode3);

    }


    //STACK CONTROLLER TEST -> JUSTIN WROTE
    @Test
    public void stack_createTest() {
        Map<String, Object> mapInit = new HashMap<>();
        mapInit.put("title", "test123");
        mapInit.put("username", "a");
        ResponseBody responseIn2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/stacks/testStack1/groups/test").
                thenReturn().body();

        assertEquals("todo missing", responseIn2.asString());
        mapInit.put("isActive", true);
        mapInit.put("calendar", "TestCalendar");
        ResponseBody responseIn3 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/stacks/testStack1/groups/test").
                thenReturn().body();

        assertEquals("success", responseIn3.asString());

        ResponseBody responseIn4 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/stacks/testStack1/groups/test").
                thenReturn().body();

        assertEquals("stack with this title already exists", responseIn4.asString());


        Response getUserStacks4 = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                when().
                get("/stacks/testStack1");
        int statusCode1 = getUserStacks4.getStatusCode();
        assertEquals(200, statusCode1);

        Response getUserStacks5 = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                when().
                get("/stacks/testStack10");
        int statusCode2 = getUserStacks5.getStatusCode();
        assertEquals(401, statusCode2);

        ResponseBody responseDelete1 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/delete/stack/testStack1").
                thenReturn().body();

        assertEquals("success", responseDelete1.asString());


        Map<String, Object> mapInit2 = new HashMap<>();
        mapInit2.put("title", "testStack2");
        ResponseBody responseIn5 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit2).
                when().
                post("/stacks/groups/test").
                thenReturn().body();

        assertEquals("success", responseIn5.asString());




        ResponseBody responseDelete2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/delete/stack/testStack2").
                thenReturn().body();

        assertEquals("success", responseDelete2.asString());
        ResponseBody responseDelete3 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/delete/stack/testStack3").
                thenReturn().body();

        assertEquals("stack-name not found", responseDelete3.asString());


        ResponseBody responseDeleteTodo1 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/delete/todo/test123").
                thenReturn().body();

        assertEquals("success", responseDeleteTodo1.asString());


        ResponseBody responseDeleteTodo2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/delete/todo/test1234").
                thenReturn().body();

        assertEquals("todo not found", responseDeleteTodo2.asString());

    }



    //STACK CONTROLLER TEST -> JUSTIN WROTE
    @Test
    public void put_deleteStackTest() throws JSONException {
        Map<String, Object> map = new HashMap<>();
        map.put("isActive", true);
        ResponseBody responsePutFail = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                put("/stack/id/99").
                thenReturn().body();
        assertEquals("failure", responsePutFail.asString());

//        Group g = new Group("tobedeletedGroup", "a");
//        JSONObject jsonObj = new JSONObject();
//        JSONArray jsonArr = new JSONArray();
//        jsonObj.put("title", "tobedeletedStack");
//        jsonObj.put("todo", (Object)jsonArr);
//        jsonObj.put("isActive", true);
//        jsonObj.put("group", g);
//        ResponseBody responsePut = RestAssured.given().
//                accept(ContentType.JSON).
//                contentType(ContentType.JSON).
//                body(jsonObj.toString()).
//                when().
//                put("/stack/id/99").
//                thenReturn().body();
//        assertEquals("success", responsePut.asString());

        ResponseBody responseDelete = RestAssured.given().
                delete("/stack/id/99").
                thenReturn().body();

        assertEquals("success", responseDelete.asString());
    }

    //TODOS-CONTROLLER TEST -> JUSTIN WROTE
    @Test // test 1
    public void getTodoTest() {

        Response getAllGroups1 = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                when().
                get("/todos");


        // Check status code
        int statusCode1 = getAllGroups1.getStatusCode();
        assertEquals(200, statusCode1);

        Response getAllGroups2 = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                when().
                get("/todos/user/a");

        // Check status code
        int statusCode2 = getAllGroups2.getStatusCode();
        assertEquals(200, statusCode2);

        Response getAllGroups3 = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                when().
                get("/todos/user/meantToFail");

        // Check status code
        int statusCode3 = getAllGroups3.getStatusCode();
        assertEquals(401, statusCode3);

        // logs a out
        Map<String, Object> logInit2 = new HashMap<>();
        logInit2.put("username", "a");
        logInit2.put("password", "a");
        ResponseBody responseInInit = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(logInit2).
                when().
                post("/logout").
                thenReturn().body();

        Response getAllGroups4 = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                when().
                get("/todos/user/a");

        // Check status code
        int statusCode4 = getAllGroups4.getStatusCode();
        assertEquals(400, statusCode4);

// logs a back in

        ResponseBody responseInInit2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(logInit2).
                when().
                post("/login").
                thenReturn().body();

    }

    //TODOS CONTROLLER TEST -> JUSTIN WROTE
    @Test
    public void todo_createTest() {
        Map<String, Object> mapInit = new HashMap<>();
        mapInit.put("title", "example");
        mapInit.put("username", "a");
        ResponseBody responseIn2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/todos/user/a").
                thenReturn().body();

        assertEquals("todo is null", responseIn2.asString());
        mapInit.put("isActive", true);
        mapInit.put("calendar", "TestCalendar");
        ResponseBody responseIn3 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/todos/user/a").
                thenReturn().body();

        assertEquals("success", responseIn3.asString());

        ResponseBody responseIn4 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/todos/user/a").
                thenReturn().body();

        assertEquals("todo with this name already exists", responseIn4.asString());

        ResponseBody responseIn5 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/todos/user/meantToFail").
                thenReturn().body();

        assertEquals("user notFound", responseIn5.asString());

        // logs a out
        Map<String, Object> logInit2 = new HashMap<>();
        logInit2.put("username", "a");
        logInit2.put("password", "a");
        ResponseBody responseInInit = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(logInit2).
                when().
                post("/logout").
                thenReturn().body();

        ResponseBody responseIn6 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/todos/user/a").
                thenReturn().body();

        assertEquals("notLoggedIn", responseIn6.asString());
// logs a back in

        ResponseBody responseInInit2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(logInit2).
                when().
                post("/login").
                thenReturn().body();

        ResponseBody responseDelete2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/delete/todo/example").
                thenReturn().body();

        assertEquals("success", responseDelete2.asString());
        ResponseBody responseDelete3 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/delete/todo/example").
                thenReturn().body();

        assertEquals("todo not found", responseDelete3.asString());


    }
    //STACK CONTROLLER TEST -> JUSTIN WROTE
    @Test
    public void put_deleteTodoTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("isActive", true);
        map.put("title", "example2");
        map.put("calendar", "cal");
        map.put("username", "a");
        ResponseBody responsePutFail = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                put("/todos/id/999").
                thenReturn().body();
        assertEquals("success", responsePutFail.asString());

        Response getAllGroups1 = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                when().
                get("/todos/id/999");

        int statusCode1 = getAllGroups1.getStatusCode();
        assertEquals(200, statusCode1);


        ResponseBody responseDelete = RestAssured.given().
                delete("/todos/id/999").
                thenReturn().body();

        assertEquals("success", responseDelete.asString());

        ResponseBody delete2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/todo/example2").
                thenReturn().body();

        assertEquals("success", delete2.asString());

    }

    //STACK CONTROLLER TEST -> JUSTIN WROTE
    @Test
    public void Second_Stack_createTest() {
        Map<String, Object> mapInit = new HashMap<>();
        mapInit.put("title", "SecondTest1");
        mapInit.put("username", "a");
        mapInit.put("calendar", "cal");
        mapInit.put("isActive", true);
        ResponseBody responseIn1 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/stack/a/newStack/testStack1/testGroup1/").
                thenReturn().body();

        assertEquals("success", responseIn1.asString());
        Map<String, Object> mapInit2 = new HashMap<>();
        mapInit.put("title", "SecondTest2");
        mapInit.put("username", "a");
        mapInit.put("calendar", "cal");
        mapInit.put("isActive", true);
        ResponseBody responseIn2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(mapInit).
                when().
                post("/addTodo/testStack1").
                thenReturn().body();


        ResponseBody delete1 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/todo/SecondTest1").
                thenReturn().body();

        assertEquals("success", delete1.asString());

        ResponseBody delete2 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/todo/SecondTest2").
                thenReturn().body();

        assertEquals("success", delete2.asString());
        assertEquals("success", responseIn2.asString());
        ResponseBody delete4 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/stack/testStack1").
                thenReturn().body();

        assertEquals("success", delete4.asString());

        ResponseBody delete3 = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                post("/delete/group/testGroup1").
                thenReturn().body();

        assertEquals("success", delete3.asString());


    }
}

package com.example.todos;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.todos.lists.CalendarActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//------------------------------------------------------------//
//                      FRONTEND TESTS                        //
//------------------------------------------------------------//


// Mock the RequestServerForService class
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class SystemTests {

    private static final int SIMULATED_DELAY_MS = 250;

    @Rule   // needed to launch the activity
    public ActivityTestRule<CalendarActivity> activityRule1 = new ActivityTestRule<>(CalendarActivity.class);

    //------------------------------------------------------------//
    //                   JACOB LYONS TESTS                        //
    //------------------------------------------------------------//

    /**
     * Start the server and run this test
     */

    @Test
    public void CalendarActivityTest(){
        onView(withId(R.id.settings)).perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.listOnlineStatus)).check(matches(isDisplayed()));

        onView(withId(R.id.goToUser)).perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.title)).check(matches(isDisplayed()));
        onView(withId(R.id.tabs)).check(matches(isDisplayed()));

        onView(withId(R.id.goToCalendar)).perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.logout)).check(matches(isDisplayed()));

        onView(withId(R.id.todosButton)).perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.goToNewToDo)).check(matches(isDisplayed()));

        onView(withId(R.id.goToCalendar)).perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.todosButton)).check(matches(isDisplayed()));

        onView(withId(R.id.stacksButton)).perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.goToNewStack)).check(matches(isDisplayed()));

        onView(withId(R.id.goToCalendar)).perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.my_groups)).check(matches(isDisplayed()));

        onView(withId(R.id.my_groups)).perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.goToNewGroup)).check(matches(isDisplayed()));

        onView(withId(R.id.goToCalendar)).perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.title_view)).check(matches(isDisplayed()));
    }


    @Test
    public void LoginActivityTest(){
        onView(withId(R.id.logout)).perform(ViewActions.click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.username)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));

        onView(withId(R.id.username)).perform(typeText("b"));
        onView(withId(R.id.password)).perform(typeText("bbbbbb"));

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.username)).check(matches(withText("b")));
        onView(withId(R.id.password)).check(matches(withText("bbbbbb")));

        onView(withId(R.id.login)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.stacksButton)).check(matches(isDisplayed()));

    }

    @Test
    public void PretendMakeNewThingsTest(){
        onView(withId(R.id.todosButton)).perform(ViewActions.click());

        onView(withId(R.id.title)).check(matches(isDisplayed()));

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.goToNewToDo)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.create_new_todo)).check(matches(isDisplayed()));
        onView(withId(R.id.button_date)).check(matches(isDisplayed()));
        onView(withId(R.id.button_time)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonback)).check(matches(isDisplayed()));

        onView(withId(R.id.todoName)).perform(typeText("b"));
        onView(withId(R.id.input_time)).perform(typeText("b"));
        onView(withId(R.id.input_date)).perform(typeText("b"));

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.todoName)).check(matches(withText("b")));
        onView(withId(R.id.input_date)).check(matches(withText("b")));
        onView(withId(R.id.input_time)).check(matches(withText("b")));

        onView(withId(R.id.buttonback)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.listToDos)).check(matches(isDisplayed()));

        onView(withId(R.id.goToGroups)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.title)).check(matches(isDisplayed()));
        onView(withId(R.id.goToTodos)).check(matches(isDisplayed()));

        onView(withId(R.id.goToNewGroup)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.create_new_group)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonback3)).check(matches(isDisplayed()));

        onView(withId(R.id.groupOwner)).perform(typeText("b"));
        onView(withId(R.id.groupName)).perform(typeText("b"));

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.groupOwner)).check(matches(withText("b")));
        onView(withId(R.id.groupName)).check(matches(withText("b")));

        onView(withId(R.id.buttonback3)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.goToCalendar)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.stacksButton)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.title)).check(matches(isDisplayed()));

        onView(withId(R.id.goToNewStack)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.create_new_group)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonback2)).check(matches(isDisplayed()));

        onView(withId(R.id.groupOwner)).perform(typeText("b"));
        onView(withId(R.id.groupName)).perform(typeText("b"));

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.groupOwner)).check(matches(withText("b")));
        onView(withId(R.id.groupName)).check(matches(withText("b")));

        onView(withId(R.id.buttonback2)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.goToCalendar)).perform(ViewActions.click());

    }

    @Test
    public void MakeNewThingsTest(){
        onView(withId(R.id.todosButton)).perform(ViewActions.click());

        onView(withId(R.id.title)).check(matches(isDisplayed()));

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.goToNewToDo)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.create_new_todo)).check(matches(isDisplayed()));
        onView(withId(R.id.button_date)).check(matches(isDisplayed()));
        onView(withId(R.id.button_time)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonback)).check(matches(isDisplayed()));

        onView(withId(R.id.todoName)).perform(typeText("b"));
        onView(withId(R.id.input_time)).perform(typeText("b"));
        onView(withId(R.id.input_date)).perform(typeText("b"));

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.todoName)).check(matches(withText("b")));
        onView(withId(R.id.input_date)).check(matches(withText("b")));
        onView(withId(R.id.input_time)).check(matches(withText("b")));

        onView(withId(R.id.create_new_todo)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.listToDos)).check(matches(isDisplayed()));

        onView(withId(R.id.goToGroups)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.title)).check(matches(isDisplayed()));
        onView(withId(R.id.goToTodos)).check(matches(isDisplayed()));

        onView(withId(R.id.goToNewGroup)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.create_new_group)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonback3)).check(matches(isDisplayed()));

        onView(withId(R.id.groupOwner)).perform(typeText("b"));
        onView(withId(R.id.groupName)).perform(typeText("b"));

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.groupOwner)).check(matches(withText("b")));
        onView(withId(R.id.groupName)).check(matches(withText("b")));

        onView(withId(R.id.create_new_group)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.goToCalendar)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.stacksButton)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.title)).check(matches(isDisplayed()));

        onView(withId(R.id.goToNewStack)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.create_new_group)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonback2)).check(matches(isDisplayed()));

        onView(withId(R.id.groupOwner)).perform(typeText("b"));
        onView(withId(R.id.groupName)).perform(typeText("b"));

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.groupOwner)).check(matches(withText("b")));
        onView(withId(R.id.groupName)).check(matches(withText("b")));

        onView(withId(R.id.create_new_group)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.goToCalendar)).perform(ViewActions.click());

    }


    //------------------------------------------------------------//
    //                   Alexander Black TESTS                    //
    //------------------------------------------------------------//

    @Test
    public void MakeNewUserTest() {
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.title_view)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.Register)).perform(ViewActions.click());


        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.username)).perform(typeText("user"));
        onView(withId(R.id.password)).perform(typeText("123456"));
        onView(withId(R.id.Confirm)).perform(typeText("123456"));

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.username)).check(matches(withText("user")));
        onView(withId(R.id.password)).check(matches(withText("123456")));
        onView(withId(R.id.Confirm)).check(matches(withText("123456")));

        onView(withId(R.id.login)).perform(ViewActions.click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.Register)).perform(ViewActions.click());


        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.username)).perform(typeText("user2"));
        onView(withId(R.id.password)).perform(typeText("123456"));
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.Confirm)).perform(typeText("1234567"));

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.username)).check(matches(withText("user2")));
        onView(withId(R.id.password)).check(matches(withText("123456")));
        onView(withId(R.id.Confirm)).check(matches(withText("1234567")));

        onView(withId(R.id.login)).perform(ViewActions.click());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.Confirm)).check(matches(isDisplayed()));
    }
}

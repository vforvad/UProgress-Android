package com.example.vsokoltsov.uprogress;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.ui.AuthorizationActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.getIdlingResources;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by vsokoltsov on 26.01.17.
 */
@RunWith(AndroidJUnit4.class)
public class AuthorizationActivityTest {
    @Rule
    public ActivityTestRule<AuthorizationActivity> authorizationActivityTestActivityTestRule =
            new ActivityTestRule<>(AuthorizationActivity.class);

    public AuthorizationActivityTest() {

        super();
    }

    @Test
    public void failedSignInFragmentAuth() throws JSONException {
//        MockW
        JSONObject object = new JSONObject();
        JSONObject innerObject = new JSONObject();
        List<String> errors = new ArrayList<String>();
        errors.add("Can\'t be blank");
        innerObject.put("email", errors);
        object.put("errors", innerObject);
        MockWebServer server = new MockWebServer();
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.enqueue(new MockResponse()
                        .setResponseCode(401)
                        .setBody(object.toString()));
        onView(withId(R.id.signInButton)).perform(click());
        onView(allOf(withId(R.id.emailField), withParent(withId(R.id.signInFragment)))).check(matches(hasErrorText("Can\'t be blank")));
    }


}

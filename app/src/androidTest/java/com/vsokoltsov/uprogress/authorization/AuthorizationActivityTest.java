package com.vsokoltsov.uprogress.authorization;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vsokoltsov.uprogress.common.BaseTestApplication;
import com.vsokoltsov.uprogress.common.IntentServiceIdlingResource;
import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.RestServiceTestHelper;
import com.vsokoltsov.uprogress.authentication.ui.AuthorizationActivity;
import com.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.vsokoltsov.uprogress.direction_detail.ui.DirectionDetailActivity;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by vsokoltsov on 26.01.17.
 */
@RunWith(AndroidJUnit4.class)
public class AuthorizationActivityTest {

    private IntentServiceIdlingResource mIdlingResource;
    private Resources resources;
    private MockWebServer server = new MockWebServer();
    String serverURL;
    private ApiRequester requester;
    private BaseTestApplication baseTestApplication;

    @Rule
    public ActivityTestRule<AuthorizationActivity> authorizationActivityRule =
            new ActivityTestRule<AuthorizationActivity>(AuthorizationActivity.class){
                @Override
                public AuthorizationActivity launchActivity(@Nullable Intent startIntent) {
                    try {
                        if (server != null) {
                            server.shutdown();
                        }
                        server = new MockWebServer();
                        server.start(3000);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    return super.launchActivity(startIntent);
                }
            };


    @Before
    public void beforeEach() throws Exception {
        resources = authorizationActivityRule.getActivity().getResources();
    }

    @Test
    public void itemsForViewPage() throws Exception {
        onView(allOf(withText(resources.getString(R.string.sign_in)), isDescendantOfA(withId(R.id.tabs)))).check(matches(isDisplayed()));
        onView(allOf(withText(resources.getString(R.string.sign_up)), isDescendantOfA(withId(R.id.tabs)))).check(matches(isDisplayed()));
    }

    @Test
    public void testPresenceOfEmailAndPasswordFieldsOnSignInFragment() throws Exception {
        onView(allOf(withId(R.id.emailField), isDescendantOfA(withId(R.id.signInFragment)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.passwordField), isDescendantOfA(withId(R.id.signInFragment)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.signInButton), isDescendantOfA(withId(R.id.signInFragment)))).check(matches(isDisplayed()));
    }

    @Test
    public void testPresenceOfEmailandPasswordFieldsOnSignUpFragment() throws Exception {
        onView(withId(R.id.pager)).perform(swipeLeft());

        onView(allOf(withId(R.id.emailField), isDescendantOfA(withId(R.id.signUpFragment)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.passwordField), isDescendantOfA(withId(R.id.signUpFragment)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.signUpButton), isDescendantOfA(withId(R.id.signUpFragment)))).check(matches(isDisplayed()));
    }

    @Test
    public void testFailedSignIn() throws Exception {
        String fileName = "failed_sign_in.json";
        server.enqueue(new MockResponse()
                .setResponseCode(403)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        onView(withId(R.id.signInButton)).perform(click());
        onView(allOf(withId(R.id.emailField), isDescendantOfA(withId(R.id.signInFragment)))).check(matches(hasErrorText("Can't be blank\n")));
    }

    @Test
    public void testFailedSignUp() throws Exception {
        String fileName = "failed_sign_in.json";
        server.enqueue(new MockResponse()
                .setResponseCode(403)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        onView(withId(R.id.pager)).perform(swipeLeft());
        onView(withId(R.id.signUpButton)).perform(click());

        onView(allOf(withId(R.id.emailField), isDescendantOfA(withId(R.id.signUpFragment)))).check(matches(hasErrorText("Can't be blank\n")));
    }

    @Test
    public void testSuccessSignIn() throws Exception {
        String token = "token.json";
        String currentUser = "current_user.json";

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), token)));

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), currentUser)));

        onView(allOf(withId(R.id.emailField), isDescendantOfA(withId(R.id.signInFragment)))).perform(typeText("aaaa"));
        onView(allOf(withId(R.id.passwordField), isDescendantOfA(withId(R.id.signInFragment)))).perform(typeText("bbb"));
        onView(withId(R.id.signInButton)).perform(click());

        Thread.sleep(1000);

        onView(withId(R.id.collapsing_toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void testSuccessSingUp() throws Exception {
        String token = "token.json";
        String currentUser = "current_user.json";

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), token)));

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), currentUser)));

        onView(withId(R.id.pager)).perform(swipeLeft());

        onView(allOf(withId(R.id.emailField), isDescendantOfA(withId(R.id.signUpFragment)))).perform(typeText("aaaa"));
        onView(allOf(withId(R.id.passwordField), isDescendantOfA(withId(R.id.signUpFragment)))).perform(typeText("bbb"));
        onView(withId(R.id.signUpButton)).perform(click());


        onView(allOf(withId(R.id.collapsing_toolbar))).check(matches(isDisplayed()));

    }

    @After
    public void after() throws Exception {
        try {
            server.shutdown();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


}

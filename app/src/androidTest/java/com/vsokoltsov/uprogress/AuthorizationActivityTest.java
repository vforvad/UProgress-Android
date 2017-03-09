package com.vsokoltsov.uprogress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.ui.AuthorizationActivity;
import com.vsokoltsov.uprogress.authentication.ui.SignInFragment;
import com.vsokoltsov.uprogress.authentication.ui.SignUpFragment;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.vsokoltsov.uprogress.common.utils.SlidingTabLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.getIdlingResources;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by vsokoltsov on 26.01.17.
 */
@RunWith(AndroidJUnit4.class)
public class AuthorizationActivityTest {
    @Rule
    public ActivityTestRule<AuthorizationActivity> authorizationActivityRule =
            new ActivityTestRule<>(AuthorizationActivity.class, true, true);

    private IdlingResource mIdlingResource;
    private Resources resources;
    private MockWebServer server = new MockWebServer();
    String serverURL;
    private ApiRequester requester;

    @Before
    public void before() throws IOException {
        server = new MockWebServer();
        server.start();
        mIdlingResource = authorizationActivityRule.getActivity().getIdlingResourceInit();
        resources = authorizationActivityRule.getActivity().getResources();
        try {
            ((BaseApplication) authorizationActivityRule.getActivity().getApplicationContext()).setApiUrl(authorizationActivityRule.getActivity().getApplicationContext(), server.url("/sessions").toString());
            ApiRequester.API_ADDRESS = server.url("/").toString();
            requester = ApiRequester.getInstance();
            serverURL = ApiRequester.API_ADDRESS;
            Espresso.registerIdlingResources(mIdlingResource);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public AuthorizationActivityTest() {
        super();
    }

    @Test
    public void itemsForViewPage() throws JSONException {
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

        Intent intent = new Intent();
        authorizationActivityRule.launchActivity(intent);

        onView(withId(R.id.signInButton)).perform(click());
        onView(allOf(withId(R.id.emailField), isDescendantOfA(withId(R.id.signInFragment)))).check(matches(hasErrorText("Can't be blank\n")));

    }

    @After
    public void after() {
        Espresso.unregisterIdlingResources(mIdlingResource);
    }


}

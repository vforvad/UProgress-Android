package com.vsokoltsov.uprogress.reset_password;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.ui.AuthorizationActivity;
import com.vsokoltsov.uprogress.common.RestServiceTestHelper;
import com.vsokoltsov.uprogress.reset_password.ui.ResetPasswordActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
 * Created by vsokoltsov on 07.04.17.
 */
@RunWith(AndroidJUnit4.class)
public class ResetPasswordActivityTest {
    private Resources resources;
    private MockWebServer server = new MockWebServer();

    @Rule
    public ActivityTestRule<ResetPasswordActivity> resetPasswordActivityRule =
            new ActivityTestRule<ResetPasswordActivity>(ResetPasswordActivity.class){
                @Override
                public ResetPasswordActivity launchActivity(@Nullable Intent startIntent) {
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
        resources = resetPasswordActivityRule.getActivity().getResources();
    }

    @Test
    public void itemsForViewPage() throws Exception {
        onView(allOf(withId(R.id.passwordField), isDescendantOfA(withId(R.id.resetPasswordFragment)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.passwordConfirmationField), isDescendantOfA(withId(R.id.resetPasswordFragment)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.resetPasswordButton), isDescendantOfA(withId(R.id.resetPasswordFragment)))).check(matches(isDisplayed()));
    }

    @Test
    public void testSuccessResetPassword() throws Exception {
        String token = "reset_password.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), token)));

        onView(allOf(withId(R.id.passwordField), isDescendantOfA(withId(R.id.resetPasswordFragment)))).perform(typeText("aaaa"));
        onView(allOf(withId(R.id.passwordConfirmationField), isDescendantOfA(withId(R.id.resetPasswordFragment)))).perform(typeText("aaaa"));
        onView(withId(R.id.resetPasswordButton)).perform(click());

        onView(withId(R.id.signInFragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testFailedResetPassword() throws Exception {
        String token = "failed_reset_password.json";
        server.enqueue(new MockResponse()
                .setResponseCode(422)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), token)));

        onView(withId(R.id.resetPasswordButton)).perform(click());

        onView(allOf(withId(R.id.passwordField), isDescendantOfA(withId(R.id.resetPasswordFragment)))).check(matches(hasErrorText("Can't be blank\n")));
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

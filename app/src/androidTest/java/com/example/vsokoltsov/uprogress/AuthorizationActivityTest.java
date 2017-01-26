package com.example.vsokoltsov.uprogress;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.ui.AuthorizationActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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
    public void failedSignInFragmentAuth() {
        onView(withId(R.id.signInButton)).perform(click());
        onView(withId(R.id.emailField)).check(matches(hasErrorText("Can\'t be blank")));
    }


}

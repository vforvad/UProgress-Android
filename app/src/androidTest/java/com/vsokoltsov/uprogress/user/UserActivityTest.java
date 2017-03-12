package com.vsokoltsov.uprogress.user;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.BaseTestApplication;
import com.vsokoltsov.uprogress.common.IntentServiceIdlingResource;
import com.vsokoltsov.uprogress.common.RestServiceTestHelper;
import com.vsokoltsov.uprogress.direction_detail.ui.DirectionDetailActivity;
import com.vsokoltsov.uprogress.user.ui.UserActivity;

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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.vsokoltsov.uprogress.common.TestUtils.hasTextInputLayoutErrorText;
import static com.vsokoltsov.uprogress.common.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by vsokoltsov on 12.03.17.
 */
@RunWith(AndroidJUnit4.class)
public class UserActivityTest {
    private IntentServiceIdlingResource mIdlingResource;
    private Resources resources;
    private MockWebServer server;
    private BaseTestApplication baseTestApplication;
    private String currentUser = "current_user.json";
    String errors = "user_error.json";
    String updatedUser = "updated_user.json";

    @Rule
    public ActivityTestRule<UserActivity> authorizationActivityRule =
            new ActivityTestRule<UserActivity>(UserActivity.class) {

                @Override
                public UserActivity launchActivity(@Nullable Intent startIntent) {
                    try {
                        server = new MockWebServer();
                        server.start(3000);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    return super.launchActivity(startIntent);
                }

                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent intent = new Intent(targetContext, UserActivity.class);
                    return intent;
                }
            };

    @Before
    public void beforeEach() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        mIdlingResource = new IntentServiceIdlingResource(context);
        resources = context.getResources();
    }

    @After
    public void afterEach() throws IOException {
        server.shutdown();
    }

    @Test
    public void testCurrentUserInfoExistence() throws Exception {
        onView(withId(R.id.collapsing_toolbar)).check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.infoTitle)).check(matches(withText(resources.getString(R.string.user_profile_email))));
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.infoTitle)).check(matches(withText(resources.getString(R.string.user_profile_nick))));
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(2, R.id.infoTitle)).check(matches(withText(resources.getString(R.string.user_profile_location))));
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(3, R.id.infoTitle)).check(matches(withText(resources.getString(R.string.user_profile_description))));
    }

    @Test
    public void testUserFormFieldsExistance() throws Exception {
        onView(withId(R.id.addDirection)).perform(click());

        onView(allOf(withId(R.id.firstNameField), isDescendantOfA(withId(R.id.userFormPopup)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.lastNameField), isDescendantOfA(withId(R.id.userFormPopup)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.emailField), isDescendantOfA(withId(R.id.userFormPopup)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.locationField), isDescendantOfA(withId(R.id.userFormPopup)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.descriptionField), isDescendantOfA(withId(R.id.userFormPopup)))).check(matches(isDisplayed()));
    }

    @Test
    public void testFailedUserUpdate() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(403)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), errors)));


        onView(withId(R.id.addDirection)).perform(click());

        onView(allOf(withId(R.id.submitForm), isDescendantOfA(withId(R.id.userFormPopup)))).perform(click());

        onView(allOf(withId(R.id.firstNameWrapper), isDescendantOfA(withId(R.id.userFormPopup)))).check(matches(hasTextInputLayoutErrorText("Can't be blank\n")));
        onView(allOf(withId(R.id.lastNameWrapper), isDescendantOfA(withId(R.id.userFormPopup)))).check(matches(hasTextInputLayoutErrorText("Can't be blank\n")));
        onView(allOf(withId(R.id.emailWrapper), isDescendantOfA(withId(R.id.userFormPopup)))).check(matches(hasTextInputLayoutErrorText("Can't be blank\n")));
    }

    @Test
    public void testSuccessUserUpdate() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), updatedUser)));

        onView(withId(R.id.addDirection)).perform(click());

        onView(allOf(withId(R.id.firstNameField), isDescendantOfA(withId(R.id.userFormPopup)))).perform(typeText("example@mail.com"));
        onView(allOf(withId(R.id.lastNameField), isDescendantOfA(withId(R.id.userFormPopup)))).perform(typeText("firstName"));
        onView(allOf(withId(R.id.emailField), isDescendantOfA(withId(R.id.userFormPopup)))).perform(typeText("lastName"));
        onView(allOf(withId(R.id.submitForm), isDescendantOfA(withId(R.id.userFormPopup)))).perform(click());

        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.infoValue)).check(matches(withText("example@mail.com")));
    }
}

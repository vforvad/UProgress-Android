package com.vsokoltsov.uprogress.directions;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.BaseTestApplication;
import com.vsokoltsov.uprogress.common.IntentServiceIdlingResource;
import com.vsokoltsov.uprogress.common.RecyclerViewMatcher;
import com.vsokoltsov.uprogress.common.RestServiceTestHelper;
import com.vsokoltsov.uprogress.directions_list.adapters.DirectionsListAdapter;
import com.vsokoltsov.uprogress.directions_list.ui.DirectionsActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.vsokoltsov.uprogress.common.TestUtils.hasTextInputLayoutErrorText;
import static com.vsokoltsov.uprogress.common.TestUtils.recyclerClick;
import static com.vsokoltsov.uprogress.common.TestUtils.withCustomConstraints;
import static com.vsokoltsov.uprogress.common.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by vsokoltsov on 10.03.17.
 */
@RunWith(AndroidJUnit4.class)
public class DirectionsActitvityTest {

    private IntentServiceIdlingResource mIdlingResource;
    private Resources resources;
    private MockWebServer server;
    private BaseTestApplication baseTestApplication;
    String directions = "directions.json";

    @Rule
    public ActivityTestRule<DirectionsActivity> authorizationActivityRule =
            new ActivityTestRule<>(DirectionsActivity.class, true, true);

    @Before
    public void beforeEach() throws IOException {
        Context context = authorizationActivityRule.getActivity().getApplicationContext();
        mIdlingResource = new IntentServiceIdlingResource(context);
        resources = authorizationActivityRule.getActivity().getResources();
        try {

            baseTestApplication = ((BaseTestApplication) authorizationActivityRule.getActivity().getApplicationContext());
            server = baseTestApplication.getServer();
            server.start(3000);

            Espresso.registerIdlingResources(mIdlingResource);



        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExistanceOfDirections() throws Exception {

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), directions)));

        Intent intent = new Intent();
        authorizationActivityRule.launchActivity(intent);

        onView(withRecyclerView(R.id.directionsList).atPositionOnView(0, R.id.directionTitle)).check(matches(withText("Title")));
        onView(withRecyclerView(R.id.directionsList).atPositionOnView(0, R.id.directionPercents)).check(matches(withText("75")));
    }

    @Test
    public void testSwipeRefreshLayout() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), directions)));

        Intent intent = new Intent();
        authorizationActivityRule.launchActivity(intent);

        onView(withId(R.id.directionsList))
                .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)));
    }

    @Test
    public void testFooterProgressBar() throws Exception {

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), directions)));

        Intent intent = new Intent();
        authorizationActivityRule.launchActivity(intent);

        onView(withId(R.id.directionsList)).perform(scrollToPosition(13));
        onView(withId(R.id.directionsList)).perform(swipeUp());
        onView(allOf(withId(R.id.progressBar), isDescendantOfA(withId(R.id.directionsList)))).check(matches(isDisplayed()));
    }

    @Test
    public void testDisplayingCreateDirectionPopup() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), directions)));

        Intent intent = new Intent();
        authorizationActivityRule.launchActivity(intent);

        onView(withId(R.id.addDirection)).perform(click());
        onView(allOf(withId(R.id.directionTitle), isDescendantOfA(withId(R.id.directionFormLayout)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.directionDescription), isDescendantOfA(withId(R.id.directionFormLayout)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.submitDirection), isDescendantOfA(withId(R.id.directionFormLayout)))).check(matches(isDisplayed()));
    }

    @Test
    public void testSuccessDirectionCreation() throws Exception {
        String direction = "direction.json";

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), directions)));

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), direction)));

        Intent intent = new Intent();
        authorizationActivityRule.launchActivity(intent);

        onView(withId(R.id.addDirection)).perform(click());
        onView(allOf(withId(R.id.directionTitle), isDescendantOfA(withId(R.id.directionFormLayout)))).perform(typeText("CREATED TITLE"));
        onView(allOf(withId(R.id.directionDescription), isDescendantOfA(withId(R.id.directionFormLayout)))).perform(typeText("Description"));
        onView(allOf(withId(R.id.submitDirection), isDescendantOfA(withId(R.id.directionFormLayout)))).perform(click());

        onView(withRecyclerView(R.id.directionsList).atPositionOnView(0, R.id.directionTitle)).check(matches(withText("CREATED TITLE")));
    }

    @Test
    public void testFailedDirectionCreation() throws Exception {
        String error = "direction_error.json";

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), directions)));

        server.enqueue(new MockResponse()
                .setResponseCode(403)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), error)));

        Intent intent = new Intent();
        authorizationActivityRule.launchActivity(intent);

        onView(withId(R.id.addDirection)).perform(click());
        onView(allOf(withId(R.id.submitDirection), isDescendantOfA(withId(R.id.directionFormLayout)))).perform(click());

        onView(allOf(withId(R.id.titleWrapper), isDescendantOfA(withId(R.id.directionFormLayout)))).check(matches(hasTextInputLayoutErrorText("Can't be blank\n")));
        onView(allOf(withId(R.id.descriptionWrapper), isDescendantOfA(withId(R.id.directionFormLayout)))).check(matches(hasTextInputLayoutErrorText("Can't be blank\n")));
    }

    @Test
    public void testSearch() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), directions)));

        Intent intent = new Intent();
        authorizationActivityRule.launchActivity(intent);

        onView(withId(R.id.search)).perform(click());
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText("Some"));
        onView(withRecyclerView(R.id.directionsList).atPositionOnView(0, R.id.directionTitle)).check(matches(withText("Some unique title")));
    }

    @Test
    public void testMovingToDetailView() throws Exception {
        String direction = "direction.json";

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), directions)));

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), direction)));

        Intent intent = new Intent();
        authorizationActivityRule.launchActivity(intent);

        onView(withId(R.id.directionsList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, recyclerClick()));
        onView(withId(R.id.directionDetailDescription)).check(matches(withText("Description")));
    }
}

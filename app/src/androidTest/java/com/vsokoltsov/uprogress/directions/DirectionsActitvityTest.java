package com.vsokoltsov.uprogress.directions;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.BaseTestApplication;
import com.vsokoltsov.uprogress.common.IntentServiceIdlingResource;
import com.vsokoltsov.uprogress.common.RecyclerViewMatcher;
import com.vsokoltsov.uprogress.common.RestServiceTestHelper;
import com.vsokoltsov.uprogress.directions_list.ui.DirectionsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by vsokoltsov on 10.03.17.
 */
@RunWith(AndroidJUnit4.class)
public class DirectionsActitvityTest {
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
    private IntentServiceIdlingResource mIdlingResource;
    private Resources resources;
    private MockWebServer server;
    private BaseTestApplication baseTestApplication;

    @Rule
    public ActivityTestRule<DirectionsActivity> authorizationActivityRule =
            new ActivityTestRule<>(DirectionsActivity.class, true, true);

    @Before
    public void before() throws IOException {
        Context context = authorizationActivityRule.getActivity().getApplicationContext();
        mIdlingResource = new IntentServiceIdlingResource(context);
        resources = authorizationActivityRule.getActivity().getResources();
        try {

            baseTestApplication = ((BaseTestApplication) authorizationActivityRule.getActivity().getApplicationContext());
            server = baseTestApplication.getServer();
            server.start(3000);

            Espresso.registerIdlingResources(mIdlingResource);

            String directions = "directions.json";

            server.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), directions)));

            Intent intent = new Intent();
            authorizationActivityRule.launchActivity(intent);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExistanceOfDirections() throws Exception {
        onView(withRecyclerView(R.id.directionsList).atPositionOnView(0, R.id.directionTitle)).check(matches(withText("Title")));
    }
}

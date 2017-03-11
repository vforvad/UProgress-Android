package com.vsokoltsov.uprogress.direction_detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.BaseTestApplication;
import com.vsokoltsov.uprogress.common.IntentServiceIdlingResource;
import com.vsokoltsov.uprogress.common.RestServiceTestHelper;
import com.vsokoltsov.uprogress.direction_detail.ui.DirectionDetailActivity;
import com.vsokoltsov.uprogress.directions_list.ui.DirectionsActivity;

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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.vsokoltsov.uprogress.common.TestUtils.withRecyclerView;

/**
 * Created by vsokoltsov on 11.03.17.
 */
@RunWith(AndroidJUnit4.class)
public class DirectionDetailActivityTest {
    private IntentServiceIdlingResource mIdlingResource;
    private Resources resources;
    private MockWebServer server;
    private BaseTestApplication baseTestApplication;
    private String direction = "direction.json";

    @Rule
    public ActivityTestRule<DirectionDetailActivity> authorizationActivityRule =
            new ActivityTestRule<DirectionDetailActivity>(DirectionDetailActivity.class) {

                @Override
                public DirectionDetailActivity launchActivity(@Nullable Intent startIntent) {
                    try {
                        server = new MockWebServer();
                        server.start(3000);

                        server.enqueue(new MockResponse()
                                .setResponseCode(200)
                                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), direction)));
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
                    Intent intent = new Intent(targetContext, DirectionDetailActivity.class);
                    intent.putExtra("user", "vforvad");
                    intent.putExtra("direction", "1");
                    return intent;
                }
            };

    @Before
    public void beforeEach() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        mIdlingResource = new IntentServiceIdlingResource(context);
    }

    @Test
    public void testCorrectDataOfDirection() throws Exception {
        onView(withId(R.id.directionDetailTitle)).check(matches(withText("CREATED TITLE")));
        onView(withId(R.id.directionDetailDescription)).check(matches(withText("Description")));
    }

    @After
    public void afterEach() {
        try {
            server.shutdown();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}

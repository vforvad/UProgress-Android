package com.vsokoltsov.uprogress.user;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vsokoltsov.uprogress.common.BaseTestApplication;
import com.vsokoltsov.uprogress.common.IntentServiceIdlingResource;
import com.vsokoltsov.uprogress.common.RestServiceTestHelper;
import com.vsokoltsov.uprogress.direction_detail.ui.DirectionDetailActivity;
import com.vsokoltsov.uprogress.user.ui.UserActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

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

    @Rule
    public ActivityTestRule<UserActivity> authorizationActivityRule =
            new ActivityTestRule<UserActivity>(UserActivity.class) {

                @Override
                public UserActivity launchActivity(@Nullable Intent startIntent) {
                    try {
                        server = new MockWebServer();
                        server.start(3000);

                        server.enqueue(new MockResponse()
                                .setResponseCode(200)
                                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), currentUser)));
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


}

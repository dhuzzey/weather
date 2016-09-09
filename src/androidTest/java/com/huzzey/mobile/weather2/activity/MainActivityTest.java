package com.huzzey.mobile.weather2.activity;


import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.huzzey.mobile.weather2.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.huzzey.mobile.weather2.Utils.atPosition;
import static com.huzzey.mobile.weather2.Utils.matchToolbarTitle;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private String toDaysDate;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        try {
            Calendar calender = Calendar.getInstance();
            DateFormat dateFormat = DateFormat.getDateInstance(SimpleDateFormat.MEDIUM);
            toDaysDate = dateFormat.format(calender.getTimeInMillis());
        } catch (Exception e){
            toDaysDate = "";
        }
    }

    @Test
    public void mainActivityDefault() {
        onView(withId(R.id.list_view)).check(matches(atPosition(0, hasDescendant(withText(toDaysDate)))));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.action_location), withContentDescription("location"), isDisplayed()));
        textView2.check(matches(isDisplayed()));

        matchToolbarTitle("London").check(matches(isDisplayed()));
    }

    @Test
    public void mainActivityChangeToNewYork() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_location), withContentDescription("location"), isDisplayed()));
        actionMenuItemView.perform(click());

        //does the list appear
        ViewInteraction listView = onView(withId(R.id.select_dialog_listview));
        listView.check(matches(isDisplayed()));

        //select New York
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("New York"),
                        withParent(withId(R.id.select_dialog_listview)),
                        isDisplayed()));
        textView.check(matches(withText("New York")));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("New York"),
                        withParent(withId(R.id.select_dialog_listview)),
                        isDisplayed()));
        appCompatTextView.perform(click());

        //results returned
        onView(withId(R.id.list_view)).check(matches(atPosition(0, hasDescendant(withText(toDaysDate)))));
    }
}

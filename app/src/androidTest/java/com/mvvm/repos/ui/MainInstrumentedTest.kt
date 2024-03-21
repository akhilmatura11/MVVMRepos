package com.mvvm.repos.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.mvvm.repos.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainInstrumentedTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test(expected = PerformException::class)
    fun itemWithText_doesNotExist() {
        Thread.sleep(3000)
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions
                    .scrollTo<MainRecyclerAdapter.MainRecyclerViewHolder>(hasDescendant(withText("repository")))
            )
    }

    @Test
    fun testRecyclerViewScroller() {
        Thread.sleep(3000)
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions
                    .scrollToPosition<MainRecyclerAdapter.MainRecyclerViewHolder>(10)
            )

    }


}
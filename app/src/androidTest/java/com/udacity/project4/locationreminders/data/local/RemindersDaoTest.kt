package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import junit.framework.Assert.*

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {

//    TODO: Add testing implementation to the RemindersDao.kt

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RemindersDatabase
    private lateinit var reminderDTO : ReminderDTO

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RemindersDatabase::class.java).build()
        reminderDTO = ReminderDTO("Reminder Title", "Reminder Description","Null Island", 0.0, 0.0)
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun testGetReminders() = runBlockingTest {
        database.reminderDao().saveReminder(reminderDTO)
        val reminders = database.reminderDao().getReminders()
        assertNotNull(reminders)
        assertEquals(1, reminders.size)
    }

    @Test
    fun testGetReminderById() = runBlockingTest{
        database.reminderDao().saveReminder(reminderDTO)
        val reminder = database.reminderDao().getReminderById(reminderDTO.id)
        assertNotNull(reminder)
        assertEquals(reminderDTO.id, reminder!!.id)
        assertEquals(reminderDTO.description, reminder.description)
        assertEquals(reminderDTO.location, reminder.location)
        assertEquals(reminderDTO.latitude, reminder.latitude)
        assertEquals(reminderDTO.longitude, reminder.longitude)
    }

    @Test
    fun testSaveReminder() = runBlockingTest{
        database.reminderDao().saveReminder(reminderDTO)
        val reminder = database.reminderDao().getReminderById(reminderDTO.id)
        assertNotNull(reminder)
    }

    @Test
    fun testDeleteAllReminders() = runBlockingTest{
        database.reminderDao().saveReminder(reminderDTO)
        database.reminderDao().deleteAllReminders()
        val reminders = database.reminderDao().getReminders()
        assertEquals(0, reminders.size)
    }

}
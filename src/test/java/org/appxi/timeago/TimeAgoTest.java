package org.appxi.timeago;
/*
 * Copyright (c) 2016, marlonlom, yuanuo
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.appxi.timeago.TimeAgo.Periods;

/**
 * Unit tests Class for TimeAgo usage.
 *
 * @author marlonlom, yuanuo
 * @version 4.0.3
 * @since 2.1.0
 */
public class TimeAgoTest {
    /**
     * The available languages array.
     */
    private static List<String> LANGUAGES_ARRAY = Arrays.asList("ar;cs;da;de;en;es;eu;fa;fr;in;it;nl;pt;tr;zh;zh_TW".split(";"));

    /**
     * Constant for bundle name
     */
    static final String BUNDLE_NAME = "org.appxi.timeago.messages";

    /**
     * The time ago messages.
     */
    private TimeAgo.Messages mTimeAgoMessages;

    /**
     * The Local bundle.
     */
    private ResourceBundle localBundle;

    /**
     * Gets random language reference.
     *
     * @return the random language code
     */
    private String randomLanguageRef() {
        final int rnd = new Random().nextInt(LANGUAGES_ARRAY.size());
        final String result = LANGUAGES_ARRAY.get(rnd);
        System.out.println("Selected language for testing: " + result + ".");
        return result;
    }

    /**
     * Setup messages resource.
     */
    @Before
    public void setupMessagesResource() {
        final Locale languageTag = Locale.forLanguageTag(randomLanguageRef());
        mTimeAgoMessages = TimeAgo.MessagesBuilder.start().withLocale(languageTag).build();
        localBundle = ResourceBundle.getBundle(BUNDLE_NAME, languageTag);
    }

    /**
     * Gets expected message.
     *
     * @param key    the key
     * @param values the values
     * @return the expected message
     */
    private String getExpectedMessage(String key, int... values) {
        String bundledMessage = localBundle.getString(key);
        if (values.length > 0)
            return bundledMessage.replace("{0}", String.valueOf(values[0]));
        return bundledMessage;
    }

    /**
     * Should show actual date time.
     */
    @Test
    public void shouldShowDateTimeNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, (calendar.get(Calendar.MILLISECOND) + 1) * -1);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.NOW.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with almost two years.
     */
    @Test
    public void shouldShowPastDateTimeWithAlmostTwoYears() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -10);
        calendar.add(Calendar.YEAR, -1);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ALMOSTTWOYEARS_PAST.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time over one year.
     */
    @Test
    public void shouldShowPastDateTimeOverOneYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -4);
        calendar.add(Calendar.YEAR, -1);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.OVERAYEAR_PAST.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with almost one year.
     */
    @Test
    public void shouldShowPastDateTimeWithAlmostOneYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ABOUTAYEAR_PAST.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with six months.
     */
    @Test
    public void shouldShowPastDateTimeWithSixMonths() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.XMONTHS_PAST.msgKey, 6);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with almost one month.
     */
    @Test
    public void shouldShowPastDateTimeWithAlmostOneMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ABOUTAMONTH_PAST.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with six days.
     */
    @Test
    public void shouldShowPastDateTimeWithSixDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -6);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.XDAYS_PAST.msgKey, 6);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with one day.
     */
    @Test
    public void shouldShowPastDateTimeWithOneDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ONEDAY_PAST.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with five hours.
     */
    @Test
    public void shouldShowPastDateTimeWithFiveHours() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -3);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.XHOURS_PAST.msgKey, 3);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with fifty one minutes.
     */
    @Test
    public void shouldShowPastDateTimeWithFiftyOneMinutes() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -51);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ABOUTANHOUR_PAST.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with nine minutes.
     */
    @Test
    public void shouldShowPastDateTimeWithNineMinutes() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -9);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.XMINUTES_PAST.msgKey, 9);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with one minute.
     */
    @Test
    public void shouldShowPastDateTimeWithOneMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ONEMINUTE_PAST.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show future date time with almost two years.
     */
    @Test
    public void shouldShowFutureDateTimeWithAlmostTwoYears() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 10);
        calendar.add(Calendar.YEAR, 1);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ALMOSTTWOYEARS_FUTURE.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show future date time over one year.
     */
    @Test
    public void shouldShowFutureDateTimeOverOneYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 4);
        calendar.add(Calendar.YEAR, 1);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.OVERAYEAR_FUTURE.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show future date time with almost one year.
     */
    @Test
    public void shouldShowFutureDateTimeWithAlmostOneYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 12);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ABOUTAYEAR_FUTURE.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show future date time with six months.
     */
    @Test
    public void shouldShowFutureDateTimeWithSixMonths() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 6);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.XMONTHS_FUTURE.msgKey, 6);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show future date time with almost one month.
     */
    @Test
    public void shouldShowFutureDateTimeWithAlmostOneMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        calendar.add(Calendar.HOUR, 15);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ABOUTAMONTH_FUTURE.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show future date time with ten days.
     */
    @Test
    public void shouldShowFutureDateTimeWithTenDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.XDAYS_FUTURE.msgKey, 10);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show future date time with one day.
     */
    @Test
    public void shouldShowFutureDateTimeWithOneDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ONEDAY_FUTURE.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show future date time with five hours.
     */
    @Test
    public void shouldShowFutureDateTimeWithFiveHours() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 5);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.XHOURS_FUTURE.msgKey, 5);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show future date time with fifty one minutes.
     */
    @Test
    public void shouldShowFutureDateTimeWithFiftyOneMinutes() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 51);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ABOUTANHOUR_FUTURE.msgKey);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show future date time with nine minutes.
     */
    @Test
    public void shouldShowFutureDateTimeWithNineMinutes() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 9);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.XMINUTES_FUTURE.msgKey, 9);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show future date time with one minute.
     */
    @Test
    public void shouldShowFutureDateTimeWithOneMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 61);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ONEMINUTE_FUTURE.msgKey);
        Assert.assertEquals(expected, results);
    }


    /**
     * Should show past date time with a week.
     */
    @Test
    public void  shouldShowPastDateTimeWithAWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -8);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.ONEWEEK_PAST.msgKey, 8);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with two weeks.
     */
    @Test
    public void  shouldShowPastDateTimeWithTwoWeeks() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -16);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.XWEEKS_PAST.msgKey, 2);
        Assert.assertEquals(expected, results);
    }

    /**
     * Should show past date time with three weeks.
     */
    @Test
    public void  shouldShowPastDateTimeWithThreeWeeks() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -23);
        String results = TimeAgo.using(calendar.getTimeInMillis(), mTimeAgoMessages);
        String expected = getExpectedMessage(Periods.XWEEKS_PAST.msgKey, 3);
        Assert.assertEquals(expected, results);
    }

}

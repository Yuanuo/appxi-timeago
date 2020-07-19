/*
 * Copyright (c) 2020, marlonlom, yuanuo
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
package org.appxi.timeago;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * The Class **TimeAgo**. Performs date time parsing into a text with 'time ago' syntax.
 * <br></br>
 * <br></br>
 * Usage:
 * <br></br>
 * <br></br>
 * *(1) Default:*
 * <pre>
 * TimeAgo.using(new java.util.Date().getTime());
 * </pre>
 * <br></br>
 * *(2) With Specific Locale (by language tag):*
 * <br></br>
 * <pre>
 * TimeAgo.using(new java.util.Date().getTime(), TimeAgo.MessagesBuilder().start().withLocale("es").build());
 * </pre>
 * <br></br>
 *
 * @author marlonlom, yuanuo
 */
public final class TimeAgo {
    /**
     * The enum Periods.
     *
     * @author marlonlom, yuanuo
     */
    protected enum Periods {
        NOW("ml.timeago.now", distance -> distance == 0L),
        ONEMINUTE_PAST("ml.timeago.oneminute.past", distance -> distance == 1L),
        XMINUTES_PAST("ml.timeago.xminutes.past", distance -> distance >= 2 && distance <= 44),
        ABOUTANHOUR_PAST("ml.timeago.aboutanhour.past", distance -> distance >= 45 && distance <= 89),
        XHOURS_PAST("ml.timeago.xhours.past", distance -> distance >= 90 && distance <= 1439),
        ONEDAY_PAST("ml.timeago.oneday.past", distance -> distance >= 1440 && distance <= 2519),
        XDAYS_PAST("ml.timeago.xdays.past", distance -> distance >= 2520 && distance <= 10079),
        ONEWEEK_PAST("ml.timeago.oneweek.past", distance -> distance >= 10080 && distance <= 20159),
        XWEEKS_PAST("ml.timeago.xweeks.past", distance -> distance >= 20160 && distance <= 43199),
        ABOUTAMONTH_PAST("ml.timeago.aboutamonth.past", distance -> distance >= 43200 && distance <= 86399),
        XMONTHS_PAST("ml.timeago.xmonths.past", distance -> distance >= 86400 && distance <= 525599),
        ABOUTAYEAR_PAST("ml.timeago.aboutayear.past", distance -> distance >= 525600 && distance <= 655199),
        OVERAYEAR_PAST("ml.timeago.overayear.past", distance -> distance >= 655200 && distance <= 914399),
        ALMOSTTWOYEARS_PAST("ml.timeago.almosttwoyears.past", distance -> distance >= 914400 && distance <= 1051199),
        XYEARS_PAST("ml.timeago.xyears.past", distance -> Math.round(distance / 525600F) > 1),
        ONEMINUTE_FUTURE("ml.timeago.oneminute.future", distance -> distance.intValue() == -1),
        XMINUTES_FUTURE("ml.timeago.xminutes.future", distance -> distance <= -2 && distance >= -44),
        ABOUTANHOUR_FUTURE("ml.timeago.aboutanhour.future", distance -> distance <= -45 && distance >= -89),
        XHOURS_FUTURE("ml.timeago.xhours.future", distance -> distance <= -90 && distance >= -1439),
        ONEDAY_FUTURE("ml.timeago.oneday.future", distance -> distance <= -1440 && distance >= -2519),
        XDAYS_FUTURE("ml.timeago.xdays.future", distance -> distance <= -2520 && distance >= -43199),
        ABOUTAMONTH_FUTURE("ml.timeago.aboutamonth.future", distance -> distance <= -43200 && distance >= -86399),
        XMONTHS_FUTURE("ml.timeago.xmonths.future", distance -> distance <= -86400 && distance >= -525599),
        ABOUTAYEAR_FUTURE("ml.timeago.aboutayear.future", distance -> distance <= -525600 && distance >= -655199),
        OVERAYEAR_FUTURE("ml.timeago.overayear.future", distance -> distance <= -655200 && distance >= -914399),
        ALMOSTTWOYEARS_FUTURE("ml.timeago.almosttwoyears.future", distance -> distance <= -914400 && distance >= -1051199),
        XYEARS_FUTURE("ml.timeago.xyears.future", distance -> Math.round(distance / 525600F) < -1);

        /**
         * The property key.
         */
        protected final String msgKey;
        /**
         * The predicate.
         */
        private final Predicate<Long> predicate;

        Periods(String msgKey, Predicate<Long> predicate) {
            this.msgKey = msgKey;
            this.predicate = predicate;
        }

        /**
         * Find by distance minutes periods.
         *
         * @param distanceMinutes the distance minutes
         * @return the periods
         */
        private static Periods findByDistanceMinutes(long distanceMinutes) {
            for (Periods period : Periods.values())
                if (period.predicate.test(distanceMinutes))
                    return period;
            return null;
        }
    }

    /**
     * Returns the 'time ago' formatted text using date time.
     *
     * @param time the date time for parsing
     * @return the 'time ago' formatted text using date time
     * @see #using(long, Messages)
     */
    public static String using(long time) {
        return using(time, Messages.DEFAULT);
    }

    /**
     * Returns the 'time ago' formatted text using date time.
     *
     * @param time     the date time for parsing
     * @param messages the resources for localizing messages
     * @return the 'time ago' formatted text using date time
     * @see Messages
     */
    public static String using(long time, Messages messages) {
        final long dim = getTimeDistanceInMinutes(time);
        final Periods period = Periods.findByDistanceMinutes(dim);
        if (period == null)
            return "";

        final StringBuilder result = new StringBuilder();
        switch (period) {
            case XMINUTES_PAST -> result.append(messages.getMessage(period.msgKey, dim));
            case XHOURS_PAST -> result.append(msg(messages, "ml.timeago.aboutanhour.past", period.msgKey, Math.round(dim / 60F)));
            case XDAYS_PAST -> result.append(msg(messages, "ml.timeago.oneday.past", period.msgKey, Math.round(dim / 1440F)));
            case XWEEKS_PAST -> result.append(msg(messages, "ml.timeago.oneweek.past", period.msgKey, Math.round(dim / 10080F)));
            case XMONTHS_PAST -> result.append(msg(messages, "ml.timeago.aboutamonth.past", period.msgKey, Math.round(dim / 43200F)));
            case XYEARS_PAST -> result.append(messages.getMessage(period.msgKey, Math.round(dim / 525600F)));
            case XMINUTES_FUTURE -> result.append(messages.getMessage(period.msgKey, Math.abs(dim)));
            case XHOURS_FUTURE -> {
                final int hours = Math.abs(Math.round(dim / 60f));
                result.append(hours == 24
                        ? messages.getMessage("ml.timeago.oneday.future")
                        : msg(messages, "ml.timeago.aboutanhour.future", period.msgKey, hours));
            }
            case XDAYS_FUTURE -> result.append(msg(messages, "ml.timeago.oneday.future", period.msgKey, Math.abs(Math.round(dim / 1440f))));
            case XMONTHS_FUTURE -> {
                final int months = Math.abs(Math.round(dim / 43200f));
                result.append(months == 12
                        ? messages.getMessage("ml.timeago.aboutayear.future")
                        : msg(messages, "ml.timeago.aboutamonth.future", period.msgKey, months));
            }
            case XYEARS_FUTURE -> result.append(messages.getMessage(period.msgKey, Math.abs(Math.round(dim / 525600f))));
            default -> result.append(messages.getMessage(period.msgKey));
        }
        return result.toString();
    }

    /**
     * Handle period key as plural string.
     *
     * @param resources the resources
     * @param periodKey the period key
     * @param value     the value
     * @return the string
     */
    private static String msg(Messages resources, String periodKey, String pluralKey, int value) {
        return value == 1 ? resources.getMessage(periodKey) : resources.getMessage(pluralKey, value);
    }

    /**
     * Returns the time distance in minutes.
     *
     * @param time the date time
     * @return the time distance in minutes
     */
    private static long getTimeDistanceInMinutes(long time) {
        final long timeDistance = System.currentTimeMillis() - time;
        return Math.round(timeDistance / 1000D / 60);
    }

    private TimeAgo() {
    }

    /**
     * The Class **Messages**. it contains a [ResourceBundle] for
     * loading and parsing localized messages related to the 'time ago' time syntax.
     * <p>
     * Usage:*
     * <p>
     * 1: Using default Locale:
     *
     * <pre>
     * Messages resources = new TimeAgo.MessagesBuilder().defaultLocale().build();
     * </pre>
     * <p>
     * 2: Using a specific Locale by language tag:
     *
     * <pre>
     * Messages resources = TimeAgo.MessagesBuilder().start().withLocale("es").build();
     * </pre>
     * <p>
     * <p>
     * *Tip: available languages for messages: spanish (es), english (en), german
     * (de), french (fr), italian (it), portuguese (pt) and more...*
     *
     * @author marlonlom, yuanuo
     */
    public static final class Messages {
        /**
         * The property path for MESSAGES.
         */
        private static final String MESSAGES = "org.appxi.timeago.messages";

        private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(MESSAGES);

        private static final Messages DEFAULT = new Messages();

        private ResourceBundle bundle;

        private Messages() {
            this.bundle = BUNDLE;
        }

        /**
         * Gets the property value.
         *
         * @param key the property key
         * @return the property value
         */
        public String getMessage(String key) {
            try {
                return bundle.getString(key);
            } catch (Exception e) {
                return key;
            }
        }

        /**
         * Gets the property value.
         *
         * @param key  the property key
         * @param args the property values
         * @return the property value
         */
        public String getMessage(String key, Object... args) {
            final String val = getMessage(key);
            try {
                return MessageFormat.format(val, args);
            } catch (Exception e) {
                return val;
            }
        }
    }

    /**
     * The Inner Class MessagesBuilder for *Messages*.
     *
     * @author marlonlom, yuanuo
     */
    public static final class MessagesBuilder {
        /**
         * The inner bundle.
         */
        private ResourceBundle innerBundle;

        /**
         * start a new MessagesBuilder, just same as 'new MessageBuilder()'
         *
         * @return
         */
        public static MessagesBuilder start() {
            return new MessagesBuilder();
        }

        /**
         * Build messages with the default locale.
         *
         * @return the builder
         */
        public MessagesBuilder defaultLocale() {
            this.innerBundle = ResourceBundle.getBundle(Messages.MESSAGES);
            return this;
        }

        /**
         * Build messages with the selected locale.
         *
         * @param locale the locale
         * @return the builder
         */
        public MessagesBuilder withLocale(Locale locale) {
            this.innerBundle = ResourceBundle.getBundle(Messages.MESSAGES, locale);
            return this;
        }

        /**
         * @param locale
         * @return
         * @see #withLocale(Locale)
         */
        public MessagesBuilder withLocale(String locale) {
            return withLocale(new Locale(locale));
        }

        /**
         * Sets the inner bundle.
         *
         * @param bundle the new inner bundle
         */
        public MessagesBuilder withBundle(ResourceBundle bundle) {
            this.innerBundle = bundle;
            return this;
        }

        /**
         * Builds the Messages instance.
         *
         * @return the time ago messages instance.
         */
        public Messages build() {
            final Messages messages = new Messages();
            if (null != this.innerBundle)
                messages.bundle = this.innerBundle;
            return messages;
        }
    }
}

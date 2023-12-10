package org.odk.collect.geo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.location.Location;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.odk.collect.maps.MapPoint;
import org.odk.collect.testshared.LocationTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class GeoUtilsTest {
    private final List<MapPoint> points = new ArrayList<>(Arrays.asList(
            new MapPoint(11, 12, 13, 14),
            new MapPoint(21, 22, 23, 24),
            new MapPoint(31, 32, 33, 34)
    ));

    @Test
    public void whenPointsAreNull_formatPoints_returnsEmptyString() {
        assertEquals(GeoUtils.formatPointsResultString(Collections.emptyList(), true), "");
        assertEquals(GeoUtils.formatPointsResultString(Collections.emptyList(), false), "");
    }

    @Test
    public void geotraces_areSeparatedBySemicolon_withoutTrialingSemicolon() {
        assertEquals(GeoUtils.formatPointsResultString(points, false),
                "11.0 12.0 13.0 14.0;21.0 22.0 23.0 24.0;31.0 32.0 33.0 34.0");
    }

    @Test
    public void geoshapes_areSeparatedBySemicolon_withoutTrialingSemicolon_andHaveMatchingFirstAndLastPoints() {
        assertEquals(GeoUtils.formatPointsResultString(points, true),
                "11.0 12.0 13.0 14.0;21.0 22.0 23.0 24.0;31.0 32.0 33.0 34.0;11.0 12.0 13.0 14.0");
    }

    @Test
    public void test_formatLocationResultString() {
        Location location = LocationTestUtils.createLocation("GPS", 1, 2, 3, 4);
        assertEquals(GeoUtils.formatLocationResultString(location), "1.0 2.0 3.0 4.0");
    }

    @Test
    public void capitalizesGps() {
        String input = "gps";
        assertEquals("GPS", GeoUtils.capitalizeGps(input));

        String locationProvider = "network";
        assertEquals("network", GeoUtils.capitalizeGps(locationProvider));

        String nullLocationProvider = null;
        assertNull(GeoUtils.capitalizeGps(nullLocationProvider));
    }
}

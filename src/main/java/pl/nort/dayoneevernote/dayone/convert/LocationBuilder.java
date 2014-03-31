/*
* Copyright 2014 Norbert Potocki
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package pl.nort.dayoneevernote.dayone.convert;

import com.dd.plist.NSDictionary;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builds Dayone's location object
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
class LocationBuilder {

    private static final String LOCATION_KEY = "Location";

    private static final String LONGITUDE_KEY = "Longitude";
    private static final String LATITUDE_KEY = "Latitude";
    private static final String REGION_KEY = "Region";
    private static final String RADIUS_KEY = "Radius";
    private static final String CENTER_KEY = "Center";

    private double longitude;
    private double latitude;

    LocationBuilder withLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    LocationBuilder withLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    NSDictionary build() {

        NSDictionary location = new NSDictionary();
        location.put(LATITUDE_KEY, latitude);
        location.put(LONGITUDE_KEY, longitude);
        location.put(REGION_KEY, buildRegion());

        return location;
    }

    private NSDictionary buildRegion() {

        NSDictionary region = new NSDictionary();
        NSDictionary center = new NSDictionary();

        center.put(LATITUDE_KEY, latitude);
        center.put(LONGITUDE_KEY, longitude);

        region.put(CENTER_KEY, center);
        region.put(RADIUS_KEY, 1.0d);

        return region;
    }

    public static NSDictionary addLocation(NSDictionary note, NSDictionary location) {
        checkNotNull(note);
        checkNotNull(location);

        note.put(LOCATION_KEY, location);
        return note;
    }

}

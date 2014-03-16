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
package pl.nort.dayoneevernote.note;

import com.google.common.collect.ImmutableSet;
import org.joda.time.DateTime;

/**
 * A factory for some default {@link pl.nort.dayoneevernote.note.Note}s
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class Notes {

    private static final String TITLE_DEFAULT = "";
    private static final String BODY_DEFAULT = "";
    private static final DateTime CREATION_TIME_DEFAULT = new DateTime(0);
    private static final Coordinates LOCATION_DEFAULT = new Coordinates(0, 0, 0);

    public static Note empty() {

        return new Note.Builder()
                .withTitle(TITLE_DEFAULT)
                .withBody(BODY_DEFAULT)
                .withCreationTime(CREATION_TIME_DEFAULT)
                .withLocation(LOCATION_DEFAULT)
                .withLabels(ImmutableSet.<String>of())
                .build();
    }

}

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

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Internal note representation
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class Note {

    private final String title;
    private final String body;
    private final DateTime creationTime;
    private final Coordinates location;

    public Note(String title, String body, DateTime creationTime, Coordinates location) {
        this.title = checkNotNull(title);
        this.body = checkNotNull(body);
        this.creationTime = checkNotNull(creationTime);
        this.location = checkNotNull(location);
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public Coordinates getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("title", title)
            .add("body", body)
            .add("creationTime", creationTime)
            .add("location", location)
            .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, body, creationTime, location);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Note other = (Note) obj;
        return Objects.equal(this.title, other.title) && Objects.equal(this.body, other.body) && Objects.equal(this.creationTime, other.creationTime) && Objects.equal(this.location, other.location);
    }

    public static class Builder {

        private static final String TITLE_DEFAULT = "";
        private static final String BODY_DEFAULT = "";
        private static final DateTime CREATION_TIME_DEFAULT = new DateTime(0);
        private static final Coordinates LOCATION_DEFAULT = new Coordinates(0, 0, 0);

        private String title = TITLE_DEFAULT;
        private String body = BODY_DEFAULT;
        private DateTime creationTime = CREATION_TIME_DEFAULT;
        private Coordinates location = LOCATION_DEFAULT;

        public Note build() {
            return new Note(title, body, creationTime, location);
        }

        public Builder withTitle(String title) {
            this.title = Objects.firstNonNull(title, TITLE_DEFAULT);
            return this;
        }

        public Builder withBody(String body) {
            this.body = Objects.firstNonNull(body, BODY_DEFAULT);
            return this;
        }

        public Builder withCreationTime(DateTime creationTime) {
            this.creationTime = Objects.firstNonNull(creationTime, CREATION_TIME_DEFAULT);
            return this;
        }

        public Builder withLocation(Coordinates coordinates) {
            this.location = Objects.firstNonNull(coordinates, LOCATION_DEFAULT);
            return this;
        }

        public Builder cloneOf(Note note) {
            return this.withBody(note.getBody())
                .withCreationTime(note.getCreationTime())
                .withTitle(note.getTitle())
                .withLocation(note.getLocation());
        }
    }
}

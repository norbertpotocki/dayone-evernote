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
package pl.nort.dayoneevernote.transformer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import pl.nort.dayoneevernote.note.Note;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Extracts date from a {@link pl.nort.dayoneevernote.note.Note} title and uses it as as creation date. Note title
 * must match both provided {@code titlePattern} and {@link org.joda.time.format.DateTimeFormat} pattern supplied in
 * {@code datePattern}. If either of them doesn't match, the creation time will not be modified.
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class ExtractDateFromTitleTransformer implements NoteTransformer {

    private final String datePattern;
    private final String titlePattern;

    public ExtractDateFromTitleTransformer(String titlePattern, String datePattern) {
        this.titlePattern = checkNotNull(titlePattern);
        this.datePattern = checkNotNull(datePattern);
    }

    @Override
    public Note apply(Note note) {

        Note.Builder builder = new Note.Builder().cloneOf(note);

        if(note.getTitle().matches(titlePattern)) {
            try {
                DateTime dt = DateTime.parse(note.getTitle(), DateTimeFormat.forPattern(datePattern).withZoneUTC());
                builder.withCreationTime(dt);
            } catch (IllegalArgumentException e) {
                // NOP
            }
        }

        return builder.build();
    }
}

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
package pl.nort.dayoneevernote.evernote.translate;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import pl.nort.dayoneevernote.note.Note;

/**
 * Transforms Evernote's {@link com.evernote.edam.type.Note} to our own {@link pl.nort.dayoneevernote.note.Note}
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class NoteFactory {

    public static Note fromEvernoteNote(com.evernote.edam.type.Note note) {

        Note.Builder builder = new Note.Builder();

        builder
            .withTitle(note.getTitle())
            .withBody(note.getContent())
            .withCreationTime(new DateTime(note.getCreated(), DateTimeZone.UTC));

        return builder.build();
    }

}

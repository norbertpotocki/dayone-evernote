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

import com.syncthemall.enml4j.ENMLProcessor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Component;
import pl.nort.dayoneevernote.note.Coordinates;
import pl.nort.dayoneevernote.note.Note;

import javax.inject.Inject;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Transforms Evernote's {@link com.evernote.edam.type.Note} to our own {@link pl.nort.dayoneevernote.note.Note}
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Component
public class NoteFactory {

    private final ENMLProcessor enmlProcessor;

    @Inject
    public NoteFactory(ENMLProcessor enmlProcessor) {
        this.enmlProcessor = checkNotNull(enmlProcessor);
    }

    public Note fromEvernoteNote(com.evernote.edam.type.Note note) throws Exception {

        Note.Builder builder = new Note.Builder();

        double x = note.getAttributes().getLongitude(),
            y = note.getAttributes().getLatitude(),
            z = note.getAttributes().getAltitude();

        builder
            .withTitle(note.getTitle())
            .withBody(enmlProcessor.noteToHTMLString(note, new HashMap<String, String>()))
            .withCreationTime(new DateTime(note.getCreated(), DateTimeZone.UTC))
            .withLocation(new Coordinates(x, y, z));

        return builder.build();
    }

}

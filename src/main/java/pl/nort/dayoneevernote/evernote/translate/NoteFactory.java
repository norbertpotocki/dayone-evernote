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

import com.evernote.edam.type.Notebook;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.syncthemall.enml4j.ENMLProcessor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Component;
import pl.nort.dayoneevernote.note.Coordinates;
import pl.nort.dayoneevernote.note.Note;

import javax.inject.Inject;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

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

    public Note fromEvernoteNote(com.evernote.edam.type.Note note, Notebook notebook) throws Exception {
        checkNotNull(note);
        checkNotNull(notebook);

        checkState(note.getNotebookGuid().equals(notebook.getGuid()), "notebook guid must match value from the note");

        double x = note.getAttributes().getLongitude(),
                y = note.getAttributes().getLatitude(),
                z = note.getAttributes().getAltitude();

        return new Note.Builder()
            .withTitle(Objects.firstNonNull(note.getTitle(), ""))
            .withBody(enmlProcessor.noteToHTMLString(note, new HashMap<String, String>()))
            .withCreationTime(new DateTime(note.getCreated(), DateTimeZone.UTC))
            .withLocation(new Coordinates(x, y, z))
            .withLabels(ImmutableSet.of(notebook.getName()))
            .build();
    }

}

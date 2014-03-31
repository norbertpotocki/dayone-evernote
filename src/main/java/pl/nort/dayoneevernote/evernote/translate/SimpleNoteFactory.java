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
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.syncthemall.enml4j.ENMLProcessor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import pl.nort.dayoneevernote.note.Coordinates;
import pl.nort.dayoneevernote.note.Note;

import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * {@link pl.nort.dayoneevernote.evernote.translate.NoteFactory} using {@link com.syncthemall.enml4j.ENMLProcessor}
 * to convert note content to XHTML and additional {@link com.google.common.base.Function} to preprocess the note.
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class SimpleNoteFactory implements NoteFactory {

    private final ENMLProcessor enmlProcessor;
    private final Function<com.evernote.edam.type.Note, com.evernote.edam.type.Note> transformer;

    public SimpleNoteFactory(ENMLProcessor enmlProcessor, Function<com.evernote.edam.type.Note, com.evernote.edam.type.Note> transformer) {
        this.enmlProcessor = checkNotNull(enmlProcessor);
        this.transformer = checkNotNull(transformer);
    }

    @Override
    public Note fromEvernoteNote(com.evernote.edam.type.Note note, Notebook notebook) throws Exception {
        checkNotNull(note);
        checkNotNull(notebook);

        checkState(note.getNotebookGuid().equals(notebook.getGuid()), "notebook guid must match value from the note");

        // Preprocess note
        note = transformer.apply(note);

        return new Note.Builder()
            .withTitle(Objects.firstNonNull(note.getTitle(), ""))
            .withBody(enmlProcessor.noteToHTMLString(note, new HashMap<String, String>()))
            .withCreationTime(new DateTime(note.getCreated(), DateTimeZone.UTC))
            .withLocation(getLocation(note))
            .withLabels(ImmutableSet.of(notebook.getName()))
            .build();
    }

    private Optional<Coordinates> getLocation(com.evernote.edam.type.Note note) {

        Optional<Coordinates> location = Optional.absent();

        if(note.getAttributes().isSetLatitude() && note.getAttributes().isSetLongitude()) {
            double x = note.getAttributes().getLongitude(),
                    y = note.getAttributes().getLatitude(),
                    z = note.getAttributes().getAltitude();

            location = Optional.of(new Coordinates(x, y, z));
        }

        return location;
    }

}

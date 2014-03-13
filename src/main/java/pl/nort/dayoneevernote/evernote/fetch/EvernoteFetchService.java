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
package pl.nort.dayoneevernote.evernote.fetch;

import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import org.springframework.stereotype.Component;
import pl.nort.dayoneevernote.evernote.translate.NoteFactory;
import pl.nort.dayoneevernote.exception.ConnectionException;
import pl.nort.dayoneevernote.note.Note;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fetches a set of notes from Evernote
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Component
public class EvernoteFetchService {

    private static final String SERVICE_NAME = "evernote";
    private static final int BATCH_SIZE = 100;

    private final ClientFactory clientFactory;

    @Inject
    public EvernoteFetchService(ClientFactory clientFactory) {
        this.clientFactory = checkNotNull(clientFactory);
    }

    public Set<Note> getNotes() {

        List<Note> notes = new LinkedList<Note>();

        try {
            NoteStoreClient noteClient = clientFactory.createNoteStoreClient();

            // Select all notes
            NoteFilter noteFilter = new NoteFilter();
            NoteList noteList;
            int firstPosInBatch = 0;

            do {
                noteList = noteClient.findNotes(noteFilter, firstPosInBatch, BATCH_SIZE);

                for(com.evernote.edam.type.Note note : noteList.getNotes()) {
                    notes.add(NoteFactory.fromEvernoteNote(note));
                }

                firstPosInBatch += BATCH_SIZE;
            } while(noteList.getTotalNotes() > firstPosInBatch);

        } catch (Exception e) {
            throw new ConnectionException(SERVICE_NAME, e.getMessage(), e);
        }

        return new HashSet<Note>(notes);
    }
}

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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.Notebook;

import pl.nort.dayoneevernote.evernote.auth.ClientFactoryFactory;
import pl.nort.dayoneevernote.evernote.translate.NoteFactory;
import pl.nort.dayoneevernote.exception.ConnectionException;
import pl.nort.dayoneevernote.fetch.Fetcher;
import pl.nort.dayoneevernote.note.Note;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fetches a set of notes from Evernote
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Component
public class BatchFetcher implements Fetcher {

    private static final String SERVICE_NAME = "evernote";
    private static final int BATCH_SIZE = 100;

    private final ClientFactoryFactory clientFactoryFactory;
    private final NoteFactory noteFactory;

    @Inject
    public BatchFetcher(ClientFactoryFactory clientFactoryFactory, NoteFactory noteFactory) {
        this.noteFactory = checkNotNull(noteFactory);
        this.clientFactoryFactory = checkNotNull(clientFactoryFactory);
    }

    @Override
    public Set<Note> getNotes() {

        List<Note> notes = new LinkedList<>();

        try {
            NoteStoreClient noteClient = clientFactoryFactory.getClientFactory().createNoteStoreClient();

            // Select all notes
            NoteFilter noteFilter = new NoteFilter();
            NoteList noteList;
            int firstPosInBatch = 0;

            do {
                // This does only fetch note metadata and omits body and attachments
                noteList = noteClient.findNotes(noteFilter, firstPosInBatch, BATCH_SIZE);

                for(com.evernote.edam.type.Note note : noteList.getNotes()) {
                    note = noteClient.getNote(note.getGuid(), true, false, false, false);
                    Notebook notebook = noteClient.getNotebook(note.getNotebookGuid());
                    notes.add(noteFactory.fromEvernoteNote(note, notebook));
                }

                firstPosInBatch += BATCH_SIZE;
            } while(noteList.getTotalNotes() > firstPosInBatch);

        } catch (Exception e) {
            throw new ConnectionException(SERVICE_NAME, e.getMessage(), e);
        }

        return new HashSet<>(notes);
    }
}

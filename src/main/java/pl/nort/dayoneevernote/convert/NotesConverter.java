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
package pl.nort.dayoneevernote.convert;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import pl.nort.dayoneevernote.fetch.Fetcher;
import pl.nort.dayoneevernote.note.Note;
import pl.nort.dayoneevernote.push.Pusher;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Converts notes between two different formats
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class NotesConverter implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(NotesConverter.class);

    private final Fetcher fetcher;
    private final Predicate<Note> filter;
    private final Function<Note, Note> transformer;
    private final List<Pusher> pushers;

    public NotesConverter(Fetcher fetcher, Predicate<Note> filter, Function<Note, Note> transformer, List<Pusher> pushers) {
        this.fetcher = checkNotNull(fetcher);
        this.filter = checkNotNull(filter);
        this.transformer = checkNotNull(transformer);
        this.pushers = checkNotNull(pushers);
    }

    @Override
    public void run() {
        Set<Note> notes = fetcher.getNotes();

        Iterable<Note> filteredNotes = Iterables.filter(notes, filter);
        Iterable<Note> transformedNotes = Iterables.transform(filteredNotes, transformer);

        LOG.info("Matched " + Iterables.size(transformedNotes) + " of " + notes.size() + " notes");

        for(Pusher pusher : pushers) {
            if(!pusher.push(transformedNotes)) {
                LOG.error("Import of some notes failed!");
            }
        }
    }
}

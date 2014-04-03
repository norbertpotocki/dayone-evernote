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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import pl.nort.dayoneevernote.fetch.Fetcher;
import pl.nort.dayoneevernote.note.Note;
import pl.nort.dayoneevernote.note.Notes;
import pl.nort.dayoneevernote.push.Pusher;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class NotesConverterTest {

    @Mock private Fetcher fetcher;
    private Predicate<Note> filter;
    private Function<Note, Note> transformer;
    private List<Pusher> pushers = ImmutableList.of(mock(Pusher.class), mock(Pusher.class));
    private Note note1 = Notes.empty(),
                 note2 = new Note.Builder().cloneOf(Notes.empty()).withTitle("Hello").build();
    private Set<Note> notes = ImmutableSet.of(note1, note2);

    @Captor private ArgumentCaptor<Iterable<Note>> captor;

    private NotesConverter converter;

    @Before
    public void setUp() throws Exception {
        filter = Predicates.alwaysTrue();
        transformer = Functions.identity();
        when(fetcher.getNotes()).thenReturn(notes);

        converter = new NotesConverter(fetcher, filter, transformer, pushers);
    }

    @Test
    public void shouldPushFetchedNotes() throws Exception {

        converter.run();

        for(Pusher pusher : pushers) {
            verify(pusher, times(1)).push(captor.capture());

            for(Note note : notes) {
                assertThat(captor.getValue()).contains(note);
            }
        }
    }

    @Test
    public void shouldFilterOutNotes() throws Exception {
        filter = Predicates.in(ImmutableSet.of(note1));
        converter = new NotesConverter(fetcher, filter, transformer, pushers);

        converter.run();

        for(Pusher pusher : pushers) {
            verify(pusher, times(1)).push(captor.capture());

            assertThat(captor.getValue()).contains(note1);
            assertThat(captor.getValue()).doesNotContain(note2);
        }
    }

    @Test
    public void shouldTransformNotes() throws Exception {
        transformer = new Function<Note, Note>() {
            @Override
            public Note apply(Note input) {
                return Notes.empty();
            }
        };

        converter = new NotesConverter(fetcher, filter, transformer, pushers);

        converter.run();

        for(Pusher pusher : pushers) {
            verify(pusher, times(1)).push(captor.capture());

            assertThat(captor.getValue()).containsOnly(note1);
        }
    }
}

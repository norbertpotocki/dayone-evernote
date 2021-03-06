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
package pl.nort.dayoneevernote.dayone.push;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import pl.nort.dayoneevernote.dayone.convert.NoteFactory;
import pl.nort.dayoneevernote.dayone.push.command.InsertNoteUsingCliCommand;
import pl.nort.dayoneevernote.note.Note;
import pl.nort.dayoneevernote.push.AbstractPusher;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Pushes note to Dayone using CLI
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Component
public class DayoneCliPusher extends AbstractPusher {

    private final InsertNoteUsingCliCommand insertNoteCommand = new InsertNoteUsingCliCommand();
    private final NoteFactory noteFactory;

    @Inject
    public DayoneCliPusher(NoteFactory noteFactory) {
        this.noteFactory = checkNotNull(noteFactory);
    }

    @Override
    public boolean push(Note note) {
        return insertNoteCommand.apply(noteFactory.fromNote(note));
    }

}

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
package pl.nort.dayoneevernote.dayone.convert;

import com.overzealous.remark.Remark;
import org.springframework.stereotype.Component;
import pl.nort.dayoneevernote.note.Note;

/**
 * Builds Dayone note from a {@link pl.nort.dayoneevernote.note.Note}
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Component
public class DayoneNoteFactory {

    private final Remark remark = new Remark();

    public Note fromNote(Note note) {

        Note.Builder builder = new Note.Builder();
        builder.cloneOf(note)
            .withBody(remark.convert(note.getBody()));

        return builder.build();
    }

}

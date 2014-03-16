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
package pl.nort.dayoneevernote.transformer;

import pl.nort.dayoneevernote.note.Note;

/**
 * Shifts creation date by a given number of hours (can be negative).
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class ShiftDateTransformer implements NoteTransformer {

    private final int shiftHours;

    public ShiftDateTransformer(int shiftHours) {
        this.shiftHours = shiftHours;
    }

    @Override
    public Note apply(Note note) {
        return new Note.Builder()
                .cloneOf(note)
                .withCreationTime(note.getCreationTime().plusHours(shiftHours))
                .build();
    }
}

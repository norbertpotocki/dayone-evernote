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
package pl.nort.dayoneevernote.filter;

import com.google.common.base.Predicate;
import pl.nort.dayoneevernote.note.Note;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Checks if the {@link pl.nort.dayoneevernote.note.Note} title matches the regexp query
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class TitleMatchPredicate implements Predicate<Note> {

    private final String pattern;

    public TitleMatchPredicate(String pattern) {
        this.pattern = checkNotNull(pattern);
    }

    @Override
    public boolean apply(Note note) {
        return note.getTitle().matches(pattern);
    }
}

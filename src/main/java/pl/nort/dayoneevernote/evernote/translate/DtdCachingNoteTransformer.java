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

import com.evernote.edam.type.Note;
import com.google.common.base.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Modifies {@link com.evernote.edam.type.Note}'s DTD URI to point to a locally cached file.
 * This speeds up initial DTD download and processing time from 45s to 0.5s.
 *
 * Warning: alters original note!
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class DtdCachingNoteTransformer implements Function<Note, Note> {

    private final String pathToLocalDTD;

    public DtdCachingNoteTransformer(String pathToLocalDTD) {
        this.pathToLocalDTD = checkNotNull(pathToLocalDTD);
    }

    @Override
    public Note apply(Note note) {

        note.setContent(note.getContent().replace("http://xml.evernote.com/pub/enml2.dtd", pathToLocalDTD));

        return note;
    }
}

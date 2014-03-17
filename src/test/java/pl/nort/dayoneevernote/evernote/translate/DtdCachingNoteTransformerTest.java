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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@RunWith(Parameterized.class)
public class DtdCachingNoteTransformerTest {

    protected static final String NOTE_CONTENT = "" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">\n" +
            "<en-note><div>Sample diary entry<br clear=\"none\"/></div></en-note>";

    private String pathToLocalDTD;
    private Function<Note, Note> transformer;
    private Note note;

    public DtdCachingNoteTransformerTest(String pathToLocalDTD) {
        this.pathToLocalDTD = pathToLocalDTD;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "enml2.dtd" }, { "dtd/other.dtd" }
        });
    }

    @Before
    public void setUp() throws Exception {
        transformer = new DtdCachingNoteTransformer(pathToLocalDTD);

        note = new Note();
        note.setContent(NOTE_CONTENT);
    }

    @Test
    public void shouldRedirectDTD() throws Exception {
        Note resultNote = transformer.apply(note);

        assertThat(resultNote.getContent()).containsIgnoringCase("<!DOCTYPE en-note SYSTEM \"" + pathToLocalDTD + "\">")
                .doesNotContain("<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">");
    }
}

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
import com.google.common.base.Functions;
import com.syncthemall.enml4j.ENMLProcessor;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class SimpleNoteFactoryIntegrationTest extends NoteFactoryTest {

    private static final ENMLProcessor ENML_PROCESSOR = new ENMLProcessor();

    @Before
    public void setUp() throws Exception {
        factory = new SimpleNoteFactory(ENML_PROCESSOR, Functions.<Note>identity());
    }

    @Test
    public void translatesBodyToHTML() throws Exception {
        pl.nort.dayoneevernote.note.Note resultNote = factory.fromEvernoteNote(evernoteNote, evernoteNotebook);

        assertThat(resultNote.getBody()).containsIgnoringCase("<body><div>Sample diary entry<br clear=\"none\"/></div></body>")
                .doesNotContain("<en-note>");
    }

    @Test
    public void setsNotebookNameAsLabel() throws Exception {
        pl.nort.dayoneevernote.note.Note resultNote = factory.fromEvernoteNote(evernoteNote, evernoteNotebook);

        assertThat(resultNote.getLabels()).contains(NOTEBOOK_NAME);
    }

    @Test
    public void throwsWhenNotebookGuidDoesntMatch() throws Exception {
        evernoteNotebook.setGuid(evernoteNotebook.getGuid() + "-jitter");

        thrown.expect(IllegalStateException.class);
        factory.fromEvernoteNote(evernoteNote, evernoteNotebook);
    }
}

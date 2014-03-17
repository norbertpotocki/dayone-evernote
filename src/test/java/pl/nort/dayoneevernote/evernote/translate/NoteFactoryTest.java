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

import com.evernote.edam.type.NoteAttributes;
import com.evernote.edam.type.Notebook;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.nort.dayoneevernote.note.Note;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public abstract class NoteFactoryTest {

    protected static final String NOTEBOOK_NAME = "Sample notebook";
    protected static final String NOTEBOOK_GUID = "guid-sample-notebook-xxx";
    protected static final String NOTEBOOK_CONTENT = "" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">\n" +
            "<en-note><div>Sample diary entry<br clear=\"none\"/></div></en-note>";

    @Rule public ExpectedException thrown = ExpectedException.none();

    protected NoteFactory factory;
    protected com.evernote.edam.type.Note evernoteNote;
    protected Notebook evernoteNotebook;

    @Before
    public void setUpNoteFactoryTest() throws Exception {
        evernoteNote = new com.evernote.edam.type.Note();
        evernoteNote.setContent(NOTEBOOK_CONTENT);
        evernoteNote.setAttributes(new NoteAttributes());
        evernoteNote.setNotebookGuid(NOTEBOOK_GUID);

        evernoteNotebook = new Notebook();
        evernoteNotebook.setGuid(NOTEBOOK_GUID);
        evernoteNotebook.setName(NOTEBOOK_NAME);
    }

    @Test
    public void translatesBodyToHTML() throws Exception {
        Note resultNote = factory.fromEvernoteNote(evernoteNote, evernoteNotebook);

        assertThat(resultNote.getBody()).containsIgnoringCase("<body><div>Sample diary entry<br clear=\"none\"/></div></body>")
                .doesNotContain("<en-note>");
    }

    @Test
    public void setsNotebookNameAsLabel() throws Exception {
        Note resultNote = factory.fromEvernoteNote(evernoteNote, evernoteNotebook);

        assertThat(resultNote.getLabels()).contains(NOTEBOOK_NAME);
    }

    @Test
    public void throwsWhenNotebookGuidDoesntMatch() throws Exception {
        evernoteNotebook.setGuid(evernoteNotebook.getGuid() + "-jitter");

        thrown.expect(IllegalStateException.class);
        factory.fromEvernoteNote(evernoteNote, evernoteNotebook);
    }
}

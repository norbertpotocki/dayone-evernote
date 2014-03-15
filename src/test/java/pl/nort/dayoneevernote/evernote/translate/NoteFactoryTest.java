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
import com.evernote.edam.type.NoteAttributes;
import com.syncthemall.enml4j.ENMLProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class NoteFactoryTest {

    @Spy private ENMLProcessor enmlProcessor = new ENMLProcessor();
    private NoteFactory factory;
    private Note evernoteNote;

    @Before
    public void setUp() throws Exception {
        factory = new NoteFactory(enmlProcessor);

        evernoteNote = new Note();
        evernoteNote.setContent("");
    }

    @Test
    public void translatesBodyToHTML() throws Exception {

        String sampleEnml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">\n" +
                "<en-note><div>Sample diary entry<br clear=\"none\"/></div></en-note>";

        evernoteNote = new Note();
        evernoteNote.setAttributes(new NoteAttributes());
        evernoteNote.setContent(sampleEnml);

        pl.nort.dayoneevernote.note.Note resultNote = factory.fromEvernoteNote(evernoteNote);

        assertThat(resultNote.getBody()).containsIgnoringCase("<body><div>Sample diary entry<br clear=\"none\"/></div></body>")
                .doesNotContain("<en-note>");
    }
}

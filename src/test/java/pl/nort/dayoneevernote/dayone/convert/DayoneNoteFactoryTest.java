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

import org.joda.time.DateTime;
import org.junit.Test;
import pl.nort.dayoneevernote.note.Note;
import pl.nort.dayoneevernote.note.Notes;
import pl.nort.dayoneevernote.translate.ExternalNoteFactory;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class DayoneNoteFactoryTest {

    private static final String NOTE_BODY = "" +
            "<?xml version='1.0' encoding='UTF-8'?><!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/><meta name=\"exporter-version\" content=\"ENML4J 1.0.0\"/><meta name=\"altitude\" content=\"0.000000\"/><meta name=\"author\" content=\"globtroter-test\"/><meta name=\"created\" content=\"Wed Mar 12 10:20:52 PDT 2014\"/><meta name=\"latitude\" content=\"0.000000\"/><meta name=\"longitude\" content=\"0.000000\"/><meta name=\"updated\" content=\"Thu Mar 13 12:13:42 PDT 2014\"/><title>2014/11/01</title></head><body><div>Sample diary entry</div></body></html>";

    private ExternalNoteFactory<Note> factory = new DayoneNoteFactory();
    private Note note = new Note.Builder()
            .cloneOf(Notes.empty())
            .withTitle("testTitle")
            .withCreationTime(new DateTime())
            .withBody(NOTE_BODY)
            .build();

    @Test
    public void shouldKeepTitleUnchanged() throws Exception {
        Note result = factory.fromNote(note);

        assertThat(result.getTitle()).isEqualTo(note.getTitle());
    }

    @Test
    public void shouldKeepCreationTimeUnchanged() throws Exception {
        Note result = factory.fromNote(note);

        assertThat(result.getCreationTime()).isEqualTo(note.getCreationTime());
    }

    @Test
    public void shouldConvertBody() throws Exception {
        Note result = factory.fromNote(note);

        assertThat(result.getBody()).isEqualTo("Sample diary entry");
    }
}

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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.nort.dayoneevernote.note.Note;

import java.util.Arrays;
import java.util.Collection;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@RunWith(Parameterized.class)
public class ExtractDateFromTitleTransformerTest extends NoteTransformerTest {

    private static final DateTime DEFAULT_DATE = new DateTime(2014, 3, 15, 22, 0, DateTimeZone.UTC);

    private String titlePattern;
    private String datePattern;
    private String title;
    private DateTime creationDate;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "", "", "", DEFAULT_DATE },
                { "", "yyyy/MM/dd HH:mm", "2014/01/01", DEFAULT_DATE },
                { "[1-2][0-9]{3}/[01][0-9]/[0-3][0-9]", "yyyy/MM/dd",
                        "2013/99/31", DEFAULT_DATE },
                { "[1-2][0-9]{3}/[01][0-9]/[0-3][0-9]", "yyyy/MM/dd",
                        "2013/12/31 - test", DEFAULT_DATE},
                { "[1-2][0-9]{3}/[01][0-9]/[0-3][0-9]", "yyyy/MM/dd HH:mm",
                        "2013/12/31", DEFAULT_DATE},
                { "[1-2][0-9]{3}/[01][0-9]/[0-3][0-9]", "yyyy/MM/dd",
                        "2013/12/31", new DateTime(2013, 12, 31, 0, 0, DateTimeZone.UTC)},
                { "[1-2][0-9]{3}/[01][0-9]/[0-3][0-9] [0-2][0-9]:[0-5][0-9]", "yyyy/MM/dd HH:mm",
                        "2014/01/01 00:00", new DateTime(2014, 1, 1, 0, 0, DateTimeZone.UTC)},
                { "[1-2][0-9]{3}/[01][0-9]/[0-3][0-9] [0-2][0-9]:[0-5][0-9]", "yyyy/MM/dd HH:mm",
                        "2014/01/02 21:12", new DateTime(2014, 1, 2, 21, 12, DateTimeZone.UTC)}
            }
        );
    }

    public ExtractDateFromTitleTransformerTest(String titlePattern, String datePattern, String title, DateTime creationDate) {
        this.titlePattern = titlePattern;
        this.datePattern = datePattern;
        this.title = title;
        this.creationDate = creationDate;
    }

    @Before
    public void setUp() throws Exception {
        transformer = new ExtractDateFromTitleTransformer(titlePattern, datePattern);
        sourceNote = new Note.Builder()
                .cloneOf(sourceNote)
                .withTitle(title)
                .withCreationTime(DEFAULT_DATE)
                .build();
    }

    @Test
    public void shouldExtractDate() throws Exception {
        resultNote = transformer.apply(sourceNote);

        assertThat(resultNote.getCreationTime()).isEqualTo(creationDate);
    }
}

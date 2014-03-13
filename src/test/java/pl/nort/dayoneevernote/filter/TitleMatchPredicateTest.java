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
public class TitleMatchPredicateTest {

    private String pattern;
    private String title;
    private boolean matches;

    private Predicate<Note> predicate;

    public TitleMatchPredicateTest(String pattern, String title, boolean matches) {
        this.pattern = pattern;
        this.title = title;
        this.matches = matches;
    }

    @Parameterized.Parameters(name = "{index} - \"{0}\" vs \"{1}\"")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "", "abc", false},
                { "", "", true},
                { "Diary entry .*", "Diary entry 123", true},
                { "[Jj]im.*|[Jj]ane.*", "Jim's diary", true},
                { "[1-2][0-9]{3}/[01][0-9]/[0-3][0-9]", "", false},
                { "[1-2][0-9]{3}/[01][0-9]/[0-3][0-9]", "2013/31/12", false},
                { "[1-2][0-9]{3}/[01][0-9]/[0-3][0-9]", "2013/12/31", true}
        });
    }

    @Test
    public void shouldMatch() throws Exception {
        predicate = new TitleMatchPredicate(pattern);

        Note note = new Note.Builder().withTitle(title).build();

        assertThat(predicate.apply(note)).isEqualTo(matches);
    }
}

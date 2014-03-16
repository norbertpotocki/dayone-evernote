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
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.nort.dayoneevernote.note.Note;
import pl.nort.dayoneevernote.note.Notes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@RunWith(Parameterized.class)
public class LabelMatchPredicateTest {

    private String pattern;
    private Set<String> labels;
    private boolean matches;

    private Predicate<Note> predicate;

    public LabelMatchPredicateTest(String pattern, Set<String> labels, boolean matches) {
        this.pattern = pattern;
        this.labels = labels;
        this.matches = matches;
    }

    @Parameterized.Parameters(name = "{index} - \"{0}\" vs \"{1}\"")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"", ImmutableSet.of(), false},
                {"", ImmutableSet.of(""), true},
                {"Diary", ImmutableSet.of("Work", "Diary"), true},
                {"Diary", ImmutableSet.of("Work", "Diary2"), false},
                {"[Jj]im.*|[Jj]ane.*", ImmutableSet.of("Work", "Jim's diary"), true},
                {".*", ImmutableSet.of("Work March", "Work April"), true},
                {".*April", ImmutableSet.of("Work March", "Work April"), true},
                {".*April", ImmutableSet.of("Work March", "Work May"), false},
                {".*", ImmutableSet.of(""), true}
        });
    }

    @Test
    public void shouldMatch() throws Exception {
        predicate = new LabelMatchPredicate(pattern);

        Note note = new Note.Builder()
                .cloneOf(Notes.empty())
                .withLabels(labels)
                .build();

        assertThat(predicate.apply(note)).isEqualTo(matches);
    }
}

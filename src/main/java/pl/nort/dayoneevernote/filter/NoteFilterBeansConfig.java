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
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.nort.dayoneevernote.note.Note;

/**
 * Configure some default filters
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Configuration
public class NoteFilterBeansConfig {

    @Bean @Primary
    public Predicate<Note> dateTitleFilterStrategy() {
        return new TitleMatchPredicate("[1-2][0-9]{3}/[01][0-9]/[0-3][0-9].*");
    }

    @Bean
    public Predicate<Note> allTitlesExceptDuplicatesFilterStrategy() {
        return Predicates.not(new TitleMatchPredicate(".*[Dd]uplicate.*"));
    }

}

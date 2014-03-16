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

import com.google.common.base.Function;
import com.google.common.base.Functions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.nort.dayoneevernote.note.Note;

/**
 * Configuration of default {@link pl.nort.dayoneevernote.transformer.NoteTransformer}s
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Configuration
public class NoteTransformerBeansConfig {

    @Bean
    public Function<Note, Note> defaultTransformerChain() {
        Function<Note, Note> extractDateFromTitle =
                new ExtractDateFromTitleTransformer("[1-2][0-9]{3}/[01][0-9]/[0-3][0-9]", "yyyy/MM/dd");

        Function<Note, Note> dropTitle = new DropTitleTransformer();

        Function<Note, Note> shiftHours = new ShiftDateTransformer(19);

        return Functions.compose(shiftHours,
                Functions.compose(dropTitle, extractDateFromTitle));
    }

}

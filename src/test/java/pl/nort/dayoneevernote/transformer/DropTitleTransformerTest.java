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

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class DropTitleTransformerTest extends NoteTransformerTest {

    @Before
    public void setUp() throws Exception {
        transformer = new DropTitleTransformer();
    }

    @Test
    public void shouldDropTitle() throws Exception {
        resultNote = transformer.apply(sourceNote);

        assertThat(resultNote.getTitle()).isEmpty();
    }

    @Test
    public void shouldNotModifyFieldsExceptTitle() throws Exception {
        resultNote = transformer.apply(sourceNote);

        assertThat(resultNote.getBody()).isEqualTo(sourceNote.getBody());
        assertThat(resultNote.getCreationTime()).isEqualTo(sourceNote.getCreationTime());
        assertThat(resultNote.getLocation()).isEqualTo(sourceNote.getLocation());
    }

}

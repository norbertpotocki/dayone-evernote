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
package pl.nort.dayoneevernote.note;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class BuilderTest {

    private Note.Builder builder;

    @Before
    public void setUp() throws Exception {
        builder = new Note.Builder();
    }

    @Test
    public void shouldConvertNullTitleToEmpty() throws Exception {
        builder.withTitle(null);
        assertThat(builder.build().getTitle()).isNotNull();
    }

    @Test
    public void shouldConvertNullBodyToEmpty() throws Exception {
        builder.withBody(null);
        assertThat(builder.build().getBody()).isNotNull();
    }

    @Test
    public void shouldConvertNullToZero() throws Exception {
        builder.withCreationTime(null);
        assertThat(builder.build().getCreationTime()).isEqualTo(new DateTime(0));
    }
}

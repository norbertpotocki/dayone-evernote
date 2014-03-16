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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@RunWith(Parameterized.class)
public class ShiftDateTransformerTest extends NoteTransformerTest {

    private int shiftHours;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 1 }, { 8 }, { -1 }, { -8 }, { 1000000 }, { -1000000 }
        });
    }

    public ShiftDateTransformerTest(int shiftHours) {
        this.shiftHours = shiftHours;
    }

    @Before
    public void setUp() throws Exception {
        transformer = new ShiftDateTransformer(shiftHours);
    }

    @Test
    public void shouldShiftCreationDate() throws Exception {
        resultNote = transformer.apply(sourceNote);

        assertThat(resultNote.getCreationTime()).isEqualTo(sourceNote.getCreationTime().plusHours(shiftHours));
    }
}

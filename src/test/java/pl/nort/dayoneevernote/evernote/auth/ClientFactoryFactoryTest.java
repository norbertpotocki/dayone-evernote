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
package pl.nort.dayoneevernote.evernote.auth;

import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.nort.dayoneevernote.exception.ConfigurationException;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class ClientFactoryFactoryTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    private ClientFactoryFactory factory;

    @Test
    public void shouldThrowOnInvalidServiceName() throws Exception {
        thrown.expect(ConfigurationException.class);
        new ClientFactoryFactory(mock(TokenFactory.class), "NONEXISTENT_ENDPOINT");
    }

    @Test
    public void shouldIgnoreEndpointNameCase() throws Exception {
        new ClientFactoryFactory(mock(TokenFactory.class), "production");
    }

    @Test
    public void shouldAskForTokenForEachFactory() throws Exception {
        TokenFactory tokenFactory = mock(TokenFactory.class);
        when(tokenFactory.getToken()).thenReturn(UUID.randomUUID().toString());

        factory = new ClientFactoryFactory(tokenFactory, "production");

        int calls = 5;

        for(int i = 0; i < calls; i++) {
            factory.getClientFactory();
        }

        verify(tokenFactory, atLeast(calls)).getToken();
    }
}

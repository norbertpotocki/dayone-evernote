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
package pl.nort.dayoneevernote.evernote.fetch;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.evernote.clients.ClientFactory;
import com.evernote.edam.error.EDAMUserException;

import pl.nort.dayoneevernote.evernote.auth.ClientFactoryFactory;
import pl.nort.dayoneevernote.evernote.translate.NoteFactory;
import pl.nort.dayoneevernote.exception.ConnectionException;
import pl.nort.dayoneevernote.fetch.Fetcher;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class BatchFetcherTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Mock private ClientFactoryFactory clientFactoryFactory;
    @Mock private NoteFactory noteFactory;
    private Fetcher service;

    @Before
    public void setUp() throws Exception {
        service = new BatchFetcher(clientFactoryFactory, noteFactory);
    }

    @Test
    public void getNotesShouldWrapClientException() throws Exception {
        ClientFactory clientFactory = mock(ClientFactory.class);
        when(clientFactory.createNoteStoreClient()).thenThrow(new EDAMUserException());

        when(clientFactoryFactory.getClientFactory()).thenReturn(clientFactory);

        thrown.expect(ConnectionException.class);
        service.getNotes();
    }
}

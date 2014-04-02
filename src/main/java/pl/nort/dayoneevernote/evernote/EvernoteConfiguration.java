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
package pl.nort.dayoneevernote.evernote;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.edam.type.Note;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.syncthemall.enml4j.ENMLProcessor;

import pl.nort.dayoneevernote.evernote.translate.DtdCachingNoteTransformer;
import pl.nort.dayoneevernote.evernote.translate.NoteFactory;
import pl.nort.dayoneevernote.evernote.translate.SimpleNoteFactory;

/**
 * Evernote configuration
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Configuration
public class EvernoteConfiguration {

    @Value("${evernote.authToken:youForgotToFillEvernoteAuthToken}")
    private String authToken;

    @Value("${evernote.useCachedDTD:false}")
    private boolean useCachedDTD;

    @Value("${evernote.service:production}")
    private String service;

    @Bean
    public ClientFactory clientFactory() {
        EvernoteService environment = EvernoteService.valueOf(service);

        EvernoteAuth evernoteAuth = new EvernoteAuth(environment, authToken);

        return new ClientFactory(evernoteAuth);
    }

    @Bean
    public NoteFactory simpleNoteFactory() {
        Function<Note, Note> transformer = useCachedDTD ? new DtdCachingNoteTransformer("dtd/enml2.dtd") :
                Functions.<Note>identity();

        ENMLProcessor enmlProcessor = new ENMLProcessor();

        return new SimpleNoteFactory(enmlProcessor, transformer);
    }

}

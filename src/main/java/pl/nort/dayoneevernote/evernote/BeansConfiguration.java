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

import com.evernote.edam.type.Note;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.syncthemall.enml4j.ENMLProcessor;

import pl.nort.dayoneevernote.evernote.auth.ClientFactoryFactory;
import pl.nort.dayoneevernote.evernote.auth.ConstantTokenFactory;
import pl.nort.dayoneevernote.evernote.auth.OAuthTokenFactory;
import pl.nort.dayoneevernote.evernote.translate.DtdCachingNoteTransformer;
import pl.nort.dayoneevernote.evernote.translate.NoteFactory;
import pl.nort.dayoneevernote.evernote.translate.SimpleNoteFactory;

/**
 * Evernote beans configuration
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Configuration
public class BeansConfiguration {

    @Value("${evernote.useCachedDTD:false}")
    private boolean useCachedDTD;

    @Value("${evernote.service:production}")
    private String service;

    @Value("${evernote.useDevelopersToken:false}")
    private boolean useDevelopersToken;

    // For constant token
    @Value("${evernote.authToken:youForgotToFillEvernoteAuthToken}")
    private String authToken;

    // For OAuth
    @Value("${evernote.apiKey:youForgotToFillEvernoteApiKey}")
    private String apiKey;

    @Value("${evernote.apiSecret:youForgotToFillEvernoteApiSecret}")
    private String apiSecret;

    @Bean
    public ClientFactoryFactory clientFactoryFactory() {
        if(useDevelopersToken) {
            return new ClientFactoryFactory(new ConstantTokenFactory(authToken), service);
        }

        return new ClientFactoryFactory(new OAuthTokenFactory(apiKey, apiSecret), service);
    }

    @Bean
    public NoteFactory simpleNoteFactory() {
        Function<Note, Note> transformer = useCachedDTD ? new DtdCachingNoteTransformer("dtd/enml2.dtd") :
                Functions.<Note>identity();

        ENMLProcessor enmlProcessor = new ENMLProcessor();

        return new SimpleNoteFactory(enmlProcessor, transformer);
    }

}

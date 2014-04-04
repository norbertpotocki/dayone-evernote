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

import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;

import pl.nort.dayoneevernote.exception.ConfigurationException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for {@link com.evernote.clients.ClientFactory}. Allows credentials rotation.
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class ClientFactoryFactory {

    private final EvernoteService endpoint;
    private final TokenFactory tokenFactory;

    public ClientFactoryFactory(TokenFactory tokenFactory, String endpointName) {
        this.tokenFactory = checkNotNull(tokenFactory);

        try {
            this.endpoint = EvernoteService.valueOf(endpointName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("Invalid endpointName: {}", endpointName);
        }
    }

    public ClientFactory getClientFactory() {
        return new ClientFactory(tokenFactory.getToken(endpoint));
    }
}

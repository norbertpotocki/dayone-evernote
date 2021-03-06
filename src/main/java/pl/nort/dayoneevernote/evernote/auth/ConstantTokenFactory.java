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

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link pl.nort.dayoneevernote.evernote.auth.TokenFactory} always returning the same value.
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class ConstantTokenFactory implements TokenFactory {

    private final String token;

    public ConstantTokenFactory(String token) {
        this.token = checkNotNull(token);
    }

    @Override
    public EvernoteAuth getToken(EvernoteService endpoint) {
        return new EvernoteAuth(endpoint, token);
    }
}

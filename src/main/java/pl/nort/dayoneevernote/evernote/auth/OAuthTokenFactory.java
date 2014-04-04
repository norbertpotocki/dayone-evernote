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

import java.util.EnumMap;
import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.EvernoteApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link TokenFactory} using OAuth protocol to fetch token
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class OAuthTokenFactory implements TokenFactory {

    private static final Logger LOG = LoggerFactory.getLogger(OAuthTokenFactory.class);

    private static final EnumMap<EvernoteService, Class<? extends EvernoteApi>> providerClassRepository;

    static {
        providerClassRepository = new EnumMap<>(EvernoteService.class);
        providerClassRepository.put(EvernoteService.PRODUCTION, EvernoteApi.class);
        providerClassRepository.put(EvernoteService.SANDBOX, EvernoteApi.Sandbox.class);
    }

    private final String apiKey;
    private final String apiSecret;

    public OAuthTokenFactory(String apiKey, String apiSecret) {
        this.apiKey = checkNotNull(apiKey);
        this.apiSecret = checkNotNull(apiSecret);
    }

    @Override
    public EvernoteAuth getToken(EvernoteService endpoint) {

        OAuthService service = new ServiceBuilder()
            .provider(providerClassRepository.get(endpoint))
            .apiKey(apiKey)
            .apiSecret(apiSecret)
            .callback("http://127.0.0.1")
            .build();

        Scanner in = new Scanner(System.in);

        Token requestToken = service.getRequestToken();
        System.out.println("Now go and authorize us here:");
        System.out.println(service.getAuthorizationUrl(requestToken));
        System.out.println("Then paste the verifier here");
        System.out.print(">> ");
        Verifier verifier = new Verifier(in.nextLine());
        System.out.println();

        LOG.info("received verifier {}", verifier);

        Token accessToken = service.getAccessToken(requestToken, verifier);
        LOG.info("got accessToken {}", accessToken);

        return EvernoteAuth.parseOAuthResponse(endpoint, accessToken.getRawResponse());
    }
}

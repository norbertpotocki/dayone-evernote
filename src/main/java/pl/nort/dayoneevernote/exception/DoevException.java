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
package pl.nort.dayoneevernote.exception;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * Abstract {@link java.lang.RuntimeException} being a root to exception hierarchy
 * and providing some extra formatting methods.
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public abstract class DoevException extends RuntimeException {

    public DoevException(String message, Object... params) {
        this(MessageFormatter.arrayFormat(message, params));
    }

    private DoevException(FormattingTuple ft) {
        super(ft.getMessage(), ft.getThrowable());
    }

}

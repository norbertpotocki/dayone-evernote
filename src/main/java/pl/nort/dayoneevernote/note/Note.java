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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Internal note representation
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class Note {

    private final String title;
    private final String boyd;
    private final DateTime creationTime;

    public Note(String title, String boyd, DateTime creationTime) {
        this.title = checkNotNull(title);
        this.boyd = checkNotNull(boyd);
        this.creationTime = checkNotNull(creationTime);
    }

    public String getTitle() {
        return title;
    }

    public String getBoyd() {
        return boyd;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }
}

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
package pl.nort.dayoneevernote.dayone.push;

import com.dd.plist.NSArray;
import com.dd.plist.NSDate;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.nort.dayoneevernote.dayone.convert.DayoneNoteFactory;
import pl.nort.dayoneevernote.note.Note;
import pl.nort.dayoneevernote.push.AbstractPusher;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Writes notes directly to Dayone journal files under specified journal path
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class DayoneJournalPusher extends AbstractPusher {

    private static final Logger LOG = LoggerFactory.getLogger(DayoneJournalPusher.class);

    private final DayoneNoteFactory dayoneNoteFactory;
    private final String journalPath;

    public DayoneJournalPusher(String journalPath, DayoneNoteFactory dayoneNoteFactory) {
        this.journalPath = checkNotNull(journalPath);
        this.dayoneNoteFactory = checkNotNull(dayoneNoteFactory);
    }

    @Override
    public boolean push(Note note) {

        Note dayoneNote = dayoneNoteFactory.fromNote(note);

        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();

        NSDate creationDate;
        try {
            creationDate = new NSDate(dayoneNote.getCreationTime().toString("yyyy-MM-dd'T'HH:mm:ss'Z'Z"));
        } catch (ParseException e) {
            return false;
        }

        String body = (Strings.isNullOrEmpty(dayoneNote.getTitle()) ? "" : dayoneNote.getTitle() + "\n\n")
                + dayoneNote.getBody();

        NSDictionary root = new NSDictionary();

        root.put("Creation Date", creationDate);
        root.put("Entry Text", body);
        root.put("Starred", false);
        root.put("Tags", new NSArray(0));
        root.put("Time Zone", "America/Vancouver");
        root.put("UUID", uuid);

        try {
            PropertyListParser.saveAsXML(root, new File(journalPath + "/" + uuid + ".doentry"));
        } catch (IOException e) {
            LOG.error("Failed to save note: " + dayoneNote, e);
            return false;
        }

        return true;
    }

}

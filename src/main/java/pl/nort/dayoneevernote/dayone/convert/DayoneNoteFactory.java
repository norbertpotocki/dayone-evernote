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
package pl.nort.dayoneevernote.dayone.convert;

import com.dd.plist.NSArray;
import com.dd.plist.NSDate;
import com.dd.plist.NSDictionary;
import com.google.common.base.Strings;
import com.overzealous.remark.Remark;
import org.springframework.stereotype.Component;
import pl.nort.dayoneevernote.exception.ConversionException;
import pl.nort.dayoneevernote.note.Note;
import pl.nort.dayoneevernote.translate.ExternalNoteFactory;

import java.text.ParseException;
import java.util.UUID;

/**
 * Builds Dayone note from a {@link pl.nort.dayoneevernote.note.Note}
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Component
public class DayoneNoteFactory implements ExternalNoteFactory<NSDictionary> {

    public static final String CREATION_DATE_KEY = "Creation Date";
    public static final String BODY_KEY = "Entry Text";
    public static final String UUID_KEY = "UUID";
    public static final String STARRED_KEY = "Starred";
    public static final String TIMEZONE_KEY = "Time Zone";

    private final Remark remark = new Remark();

    @Override
    public NSDictionary fromNote(Note note) {

        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();

        NSDate creationDate;
        try {
            creationDate = new NSDate(note.getCreationTime().toString("yyyy-MM-dd'T'HH:mm:ss'Z'Z"));
        } catch (ParseException e) {
            throw new ConversionException(note.getCreationTime().toString(), e);
        }

        // Convert body to Remark
        String remarkBody = remark.convert(note.getBody());

        String body = (Strings.isNullOrEmpty(note.getTitle()) ? "" : note.getTitle() + "\n\n")
                + remarkBody;

        NSDictionary root = new NSDictionary();

        root.put(CREATION_DATE_KEY, creationDate);
        root.put(BODY_KEY, body);
        root.put(STARRED_KEY, false);
        root.put("Tags", new NSArray(0));
        root.put(TIMEZONE_KEY, "America/Vancouver");
        root.put(UUID_KEY, uuid);

        LocationBuilder.addLocation(root, new LocationBuilder()
                .withLongitude(note.getLocation().getX())
                .withLatitude(note.getLocation().getY())
                .build());

        return root;
    }

}

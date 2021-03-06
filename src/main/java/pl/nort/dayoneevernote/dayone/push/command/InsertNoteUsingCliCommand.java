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
package pl.nort.dayoneevernote.dayone.push.command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import com.dd.plist.NSDictionary;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

import pl.nort.dayoneevernote.dayone.convert.NoteFactory;
import pl.nort.dayoneevernote.exception.ConnectionException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Adds a new entry based on a {@link com.dd.plist.NSDictionary} to Dayone using CLI
 *
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
public class InsertNoteUsingCliCommand implements Predicate<NSDictionary> {

    private static final String CLI_APP = "dayone";
    private static final String CLI_COMMAND = "new";
    private static final String CREATION_DATE_PARAMETER = "--date=%s";

    @Override
    public boolean apply(NSDictionary note) {
        checkNotNull(note);

        ProcessBuilder pb = new ProcessBuilder(prepareShellCommand(note));

        try {
            System.out.println("Writing note to Dayone using CLI: " + note);
            System.out.println("Using command: " + pb.command());

            Process process = pb.start();
            pb.redirectErrorStream(true);

            Writer writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writeNote(note, writer);
            writer.close();

            if (process.waitFor() != 0) {

                StringBuilder out = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;

                while ((line = br.readLine()) != null) {
                    out.append(line).append('\n');
                    System.err.println(line);
                }

                return false;
            }

        } catch (Exception e) {
            throw new ConnectionException("Dayone", e.getMessage(), e);
        }

        return true;
    }

    private void writeNote(NSDictionary note, Writer writer) throws IOException {
        writer.write(note.get(NoteFactory.BODY_KEY).toString());
    }

    private List<String> prepareShellCommand(NSDictionary note) {
        return ImmutableList.of(CLI_APP,
                String.format(CREATION_DATE_PARAMETER, note.get(NoteFactory.CREATION_DATE_KEY).toString()),
                CLI_COMMAND);
    }
}

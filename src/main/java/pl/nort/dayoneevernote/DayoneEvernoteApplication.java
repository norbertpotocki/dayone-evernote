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
package pl.nort.dayoneevernote;

import com.google.common.collect.Iterables;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.nort.dayoneevernote.evernote.fetch.EvernoteFetchService;
import pl.nort.dayoneevernote.filter.TitleMatchPredicate;
import pl.nort.dayoneevernote.note.Note;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class DayoneEvernoteApplication implements CommandLineRunner {

    @Inject
    private EvernoteFetchService fetchService;

    @Override
    public void run(String... args) {
        Set<Note> notes = fetchService.getNotes();

        Iterable<Note> filteredNotes = Iterables.filter(notes, new TitleMatchPredicate("[1-2][0-9]{3}/[01][0-9]/[0-3][0-9]"));

        System.out.println("Matched " + Iterables.size(filteredNotes) + " of " + notes.size() + " notes");
    }


    public static void main(String[] args) {
        SpringApplication.run(DayoneEvernoteApplication.class, args);
    }
}

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

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

import pl.nort.dayoneevernote.convert.NotesConverter;
import pl.nort.dayoneevernote.fetch.Fetcher;
import pl.nort.dayoneevernote.note.Note;
import pl.nort.dayoneevernote.push.Pusher;

/**
 * @author <a href="mailto:norbert.potocki@gmail.com">Norbert Potocki</a>
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class DayoneEvernoteApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DayoneEvernoteApplication.class);

    @Inject private Fetcher fetcher;
    @Inject private Predicate<Note> filter;
    @Inject private Function<Note, Note> transformer;
    @Inject @Named("stdoutPusher") private Pusher consolePusher;
    @Inject @Named("dayoneJournalPusher") private Pusher pusher;

    @Override
    public void run(String... args) {
        Runnable command = new NotesConverter(fetcher, filter, transformer, ImmutableList.of(consolePusher, pusher));

        command.run();
    }


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DayoneEvernoteApplication.class);
        app.setShowBanner(false);
        app.run(args);
    }
}

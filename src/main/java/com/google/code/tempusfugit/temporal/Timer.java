/*
 * Copyright (c) 2009-2015, toby weston & tempus-fugit committers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.tempusfugit.temporal;

import com.google.code.tempusfugit.ClassInvariantViolation;
import com.google.code.tempusfugit.concurrency.annotations.Not;
import com.google.code.tempusfugit.concurrency.annotations.ThreadSafe;

import java.util.Date;

import static com.google.code.tempusfugit.temporal.Duration.millis;

@Not(ThreadSafe.class)
public final class Timer implements StopWatch {

    private final Clock clock;

    private Date started;
    private Date stopped;

    /**
     * Constructs and starts a stop watch.
     * @since 1.2
     * */
    public Timer(Clock clock) {
        Date now = clock.now();
        this.clock = clock;
        this.started = now;
        this.stopped = now;
    }

    @Deprecated
    public Date getStartDate() {
        return started;
    }

    @Override
    public void reset() {
        started = clock.now();
    }

    @Override
    public void lap() {
        stopped = clock.now();
    }

    @Override
    public Duration elapsedTime() {
        if (stopped.getTime() < started.getTime())
            throw new ClassInvariantViolation("please start the stop watch before stopping it");
        return millis(stopped.getTime() - started.getTime());
    }

}
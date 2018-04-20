/*
 * Copyright (c) 2017 Alex de Kruijff <alex.de.kruijff@MazarineBlue.org>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.mazarineblue.subscribers;

import org.mazarineblue.subscribers.RecorderSubscriber;
import org.mazarineblue.subscribers.SkipEventsSubscriber;
import de.bechte.junit.runners.context.HierarchicalContextRunner;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mazarineblue.eventdriven.Processor;
import org.mazarineblue.eventdriven.util.TestInvoker;
import org.mazarineblue.eventnotifier.Event;
import org.mazarineblue.eventnotifier.events.TestEvent;
import org.mazarineblue.eventnotifier.util.TestPredicate;
import org.mazarineblue.utilities.util.TestHashCodeAndEquals;

@RunWith(HierarchicalContextRunner.class)
public class HashCodeAndEqualsTest {

    @SuppressWarnings("PublicInnerClass")
    public class RecorderSubscriberHCAE
            extends TestHashCodeAndEquals<RecorderSubscriber> {

        @Override
        protected RecorderSubscriber getObject() {
            Processor processor = Processor.newInstance();
            TestInvoker invoker = new TestInvoker(processor);
            TestPredicate<Event> stopCondition = new TestPredicate<Event>(false);
            RecorderSubscriber recorder = new RecorderSubscriber(invoker, stopCondition);
            processor.addLink(recorder);
            recorder.eventHandler(new TestEvent());
            return recorder;
        }

        @Override
        protected RecorderSubscriber getDifferentObject() {
            return new RecorderSubscriber(null, null);
        }

        @Test
        public void hashCode_DifferentInvoker() {
            int a = getObject().hashCode();
            int b = getRecorderWithDifferentInvoker().hashCode();
            assertNotEquals(a, b);
        }

        @Test
        public void equals_DifferentInvoker() {
            Object a = getObject();
            Object b = getRecorderWithDifferentInvoker();
            assertFalse(a.equals(b));
        }

        private RecorderSubscriber getRecorderWithDifferentInvoker() {
            return new RecorderSubscriber(new TestInvoker().setIdentifier("other"), null);
        }

        @Test
        public void hashCode_RecoderWithDifferentStopCondition() {
            int a = getObject().hashCode();
            int b = getRecorderWithDifferentStopCondition().hashCode();
            assertNotEquals(a, b);
        }

        @Test
        public void equals_DifferentStopCondition() {
            Object a = getObject();
            Object b = getRecorderWithDifferentStopCondition();
            assertFalse(a.equals(b));
        }

        private RecorderSubscriber getRecorderWithDifferentStopCondition() {
            return new RecorderSubscriber(new TestInvoker(), new TestPredicate<Event>(true).setIdentifier("other"));
        }

        @Test
        public void hashCode_DifferentRecording() {
            int a = getObject().hashCode();
            int b = createDifferentRecording().hashCode();
            assertNotEquals(a, b);
        }

        @Test
        public void equals_DifferentRecording() {
            Object a = getObject();
            RecorderSubscriber recorder = createDifferentRecording();
            assertFalse(a.equals(recorder));
        }

        private RecorderSubscriber createDifferentRecording() {
            Processor processor = Processor.newInstance();
            TestInvoker invoker = new TestInvoker(processor);
            TestPredicate<Event> stopCondition = new TestPredicate<Event>(false);
            RecorderSubscriber recorder = new RecorderSubscriber(invoker, stopCondition);
            processor.addLink(recorder);
            recorder.eventHandler(new TestEvent());
            return recorder;
        }
    }

    @SuppressWarnings("PublicInnerClass")
    public class SkipEventsSubscriberHCAE
            extends TestHashCodeAndEquals<SkipEventsSubscriber> {

        @Override
        protected SkipEventsSubscriber getObject() {
            Processor processor = Processor.newInstance();
            TestInvoker invoker = new TestInvoker(processor);
            TestPredicate<Event> stopCondition = new TestPredicate<Event>(false);
            SkipEventsSubscriber recorder = new SkipEventsSubscriber(invoker, stopCondition);
            processor.addLink(recorder);
            recorder.eventHandler(new TestEvent());
            return recorder;
        }

        @Override
        protected SkipEventsSubscriber getDifferentObject() {
            return new SkipEventsSubscriber(null, null);
        }

        @Test
        public void hashCode_DifferentInvoker() {
            int a = getObject().hashCode();
            int b = getSkipEventsWithDifferentInvoker().hashCode();
            assertNotEquals(a, b);
        }

        @Test
        public void equals_DifferentInvoker() {
            Object a = getObject();
            Object b = getSkipEventsWithDifferentInvoker();
            assertFalse(a.equals(b));
        }

        private SkipEventsSubscriber getSkipEventsWithDifferentInvoker() {
            return new SkipEventsSubscriber(new TestInvoker().setIdentifier("other"), null);
        }

        @Test
        public void hashCode_RecoderWithDifferentStopCondition() {
            int a = getObject().hashCode();
            int b = getSkipEventsWithDifferentStopCondition().hashCode();
            assertNotEquals(a, b);
        }

        @Test
        public void equals_DifferentStopCondition() {
            Object a = getObject();
            Object b = getSkipEventsWithDifferentStopCondition();
            assertFalse(a.equals(b));
        }

        private SkipEventsSubscriber getSkipEventsWithDifferentStopCondition() {
            return new SkipEventsSubscriber(new TestInvoker(), new TestPredicate<Event>(true).setIdentifier("other"));
        }

        @Test
        public void hashCode_DifferentRecording() {
            int a = getObject().hashCode();
            int b = createDifferentRecording().hashCode();
            assertNotEquals(a, b);
        }

        @Test
        public void equals_DifferentRecording() {
            Object a = getObject();
            SkipEventsSubscriber recorder = createDifferentRecording();
            assertFalse(a.equals(recorder));
        }

        private SkipEventsSubscriber createDifferentRecording() {
            Processor processor = Processor.newInstance();
            TestInvoker invoker = new TestInvoker(processor);
            TestPredicate<Event> stopCondition = new TestPredicate<Event>(false);
            SkipEventsSubscriber recorder = new SkipEventsSubscriber(invoker, stopCondition);
            processor.addLink(recorder);
            recorder.eventHandler(new TestEvent());
            return recorder;
        }
    }
}

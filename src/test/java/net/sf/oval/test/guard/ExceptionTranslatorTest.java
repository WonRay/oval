/*********************************************************************
 * Copyright 2005-2018 by Sebastian Thomschke and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package net.sf.oval.test.guard;

import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.exception.ConstraintsViolatedException;
import net.sf.oval.exception.ExceptionTranslatorJDKExceptionsImpl;
import net.sf.oval.guard.Guard;
import net.sf.oval.guard.Guarded;

/**
 *
 * @author Sebastian Thomschke
 */
public class ExceptionTranslatorTest extends TestCase {
    @Guarded
    public static final class TestEntity {
        public void setName(@SuppressWarnings("unused") @NotNull(message = "NULL") final String name) {
            //...
        }

        public void throwCheckedException() throws InvocationTargetException {
            throw new InvocationTargetException(null);
        }
    }

    public void testExceptionTranslator() {
        final Guard guard = new Guard();
        TestGuardAspect.aspectOf().setGuard(guard);

        assertNull(guard.getExceptionTranslator());

        try {
            final TestEntity t = new TestEntity();
            t.setName(null);
        } catch (final ConstraintsViolatedException ex) {
            assertEquals(ex.getMessage(), "NULL");
        }

        try {
            final TestEntity t = new TestEntity();

            guard.setExceptionTranslator(new ExceptionTranslatorJDKExceptionsImpl());
            try {
                t.setName(null);
                fail();
            } catch (final IllegalArgumentException ex) {
                assertEquals(ex.getMessage(), "NULL");
            }

            try {
                t.throwCheckedException();
                fail();
            } catch (final InvocationTargetException ex) {
                // expected
            }

        } finally {
            guard.setExceptionTranslator(null);
        }
    }
}

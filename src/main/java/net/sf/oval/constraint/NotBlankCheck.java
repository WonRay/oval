/*********************************************************************
 * Copyright 2005-2018 by Sebastian Thomschke and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package net.sf.oval.constraint;

import net.sf.oval.ConstraintTarget;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

/**
 * @author Sebastian Thomschke
 */
public class NotBlankCheck extends AbstractAnnotationCheck<NotBlank> {
    private static final long serialVersionUID = 1L;

    @Override
    protected ConstraintTarget[] getAppliesToDefault() {
        return new ConstraintTarget[] { ConstraintTarget.VALUES };
    }

    @Override
    public boolean isSatisfied(final Object validatedObject, final Object valueToValidate, final OValContext context, final Validator validator) {
        if (valueToValidate == null)
            return true;

        final String str = valueToValidate.toString();

        final int l = str.length();
        for (int i = 0; i < l; i++) {
            final char ch = str.charAt(i);
            if (!(Character.isSpaceChar(ch) || Character.isWhitespace(ch)))
                return true;
        }

        return false;
    }
}

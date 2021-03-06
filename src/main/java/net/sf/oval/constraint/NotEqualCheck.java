/*********************************************************************
 * Copyright 2005-2020 by Sebastian Thomschke and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package net.sf.oval.constraint;

import static net.sf.oval.Validator.*;

import java.util.Map;

import net.sf.oval.ConstraintTarget;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

/**
 * @author Sebastian Thomschke
 */
public class NotEqualCheck extends AbstractAnnotationCheck<NotEqual> {
   private static final long serialVersionUID = 1L;

   private boolean ignoreCase;
   private String testString;
   private transient String testStringLowerCase;

   @Override
   public void configure(final NotEqual constraintAnnotation) {
      super.configure(constraintAnnotation);
      setIgnoreCase(constraintAnnotation.ignoreCase());
      setTestString(constraintAnnotation.value());
   }

   @Override
   protected Map<String, String> createMessageVariables() {
      final Map<String, String> messageVariables = getCollectionFactory().createMap(2);
      messageVariables.put("ignoreCase", Boolean.toString(ignoreCase));
      messageVariables.put("testString", testString);
      return messageVariables;
   }

   @Override
   protected ConstraintTarget[] getAppliesToDefault() {
      return new ConstraintTarget[] {ConstraintTarget.VALUES};
   }

   public String getTestString() {
      return testString;
   }

   private String getTestStringLowerCase() {
      if (testStringLowerCase == null && testString != null) {
         testStringLowerCase = testString.toLowerCase(Validator.getLocaleProvider().getLocale());
      }
      return testStringLowerCase;
   }

   public boolean isIgnoreCase() {
      return ignoreCase;
   }

   @Override
   public boolean isSatisfied(final Object validatedObject, final Object valueToValidate, final OValContext context, final Validator validator) {
      if (valueToValidate == null)
         return true;

      if (ignoreCase)
         return !valueToValidate.toString().toLowerCase(Validator.getLocaleProvider().getLocale()).equals(getTestStringLowerCase());

      return !valueToValidate.toString().equals(testString);
   }

   public void setIgnoreCase(final boolean ignoreCase) {
      this.ignoreCase = ignoreCase;
      requireMessageVariablesRecreation();
   }

   public void setTestString(final String testString) {
      this.testString = testString;
      requireMessageVariablesRecreation();
   }
}

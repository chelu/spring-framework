/*
 * Copyright 2002-2014 the original author or authors.
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

package org.springframework.aop.aspectj;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.springframework.aop.support.DelegatePerTargetObjectIntroductionInterceptor;
import org.springframework.aop.support.DelegatePerTargetObjectIntroductionInterceptor.DelegateFactory;


/**
 * Implements AspectJ annotation-style behavior for the DeclareMixin annotation.
 * 
 * @author Jose Luis Martin
 * @since 4.0
 */
public class DeclareMixinAdvisor extends DeclareParentsAdvisor {

	/**
	 * Create a new advisor for this DeclareMix method.
	 * @param method declare mix method
	 * @param aspectMetadata the aspect metadata
	 * @param typePattern type pattern the introduction is restricted to
	 */
	public DeclareMixinAdvisor(Method method, Object aspect, String typePattern) {
		super(method.getReturnType(), typePattern, null, createAspect(method, aspect));
	}

	
	private static Advice createAspect(final Method method, final Object aspect) {
		DelegateFactory delegateFactory = new DelegateFactory() {

			@Override
			public Object createNewDelegate(Object targetObject) throws Exception {
				return method.invoke(aspect, targetObject);
				
			}
		};
			
		return new DelegatePerTargetObjectIntroductionInterceptor(delegateFactory, method.getReturnType());
	}

}

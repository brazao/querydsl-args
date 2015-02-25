package me.yanaga.querydsl.args.core.single;

/*
 * #%L
 * querydsl-args-core
 * %%
 * Copyright (C) 2014 - 2015 Edson Yanaga
 * %%
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
 * #L%
 */

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.StringPath;
import me.yanaga.querydsl.args.core.TestConfig;
import me.yanaga.querydsl.args.core.model.Person;
import me.yanaga.querydsl.args.core.model.QPerson;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = TestConfig.class)
public class StringArgumentTest extends AbstractTransactionalTestNGSpringContextTests {

	@PersistenceContext
	private EntityManager entityManager;

	@BeforeMethod
	public void setUp() {
		Person person = new Person();
		person.setOneString("abcdef");
		person.setAnotherString("uvwxyz");
		entityManager.persist(person);
	}

	@Test
	public void testAppendDefaultOneArgument() {
		StringArgument argument = StringArgument.of("cd");
		BooleanBuilder builder = new BooleanBuilder();
		argument.append(builder, QPerson.person.oneString);
		Person result = new JPAQuery(entityManager).from(QPerson.person).where(builder).uniqueResult(QPerson.person);
		assertThat(result.getOneString()).isEqualTo("abcdef");
	}

	@Test
	public void testAppendDefaultOneArgumentWithNoResult() {
		StringArgument argument = StringArgument.of("yanaga");
		BooleanBuilder builder = new BooleanBuilder();
		argument.append(builder, QPerson.person.oneString);
		Person result = new JPAQuery(entityManager).from(QPerson.person).where(builder).uniqueResult(QPerson.person);
		assertThat(result).isNull();
	}

	@Test
	public void testAppendStartsWithTwoArguments() {
		StringArgument argument = StringArgument.of("a");
		BooleanBuilder builder = new BooleanBuilder();
		argument.append(builder, StringPath::startsWith, QPerson.person.oneString, QPerson.person.anotherString);
		Person result = new JPAQuery(entityManager).from(QPerson.person).where(builder).uniqueResult(QPerson.person);
		assertThat(result.getAnotherString()).isEqualTo("uvwxyz");
	}

}

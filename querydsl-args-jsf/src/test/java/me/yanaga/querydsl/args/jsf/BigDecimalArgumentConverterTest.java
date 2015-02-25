package me.yanaga.querydsl.args.jsf;

/*
 * #%L
 * querydsl-args-jsf
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

import me.yanaga.querydsl.args.core.single.BigDecimalArgument;
import org.testng.annotations.Test;

import javax.faces.convert.ConverterException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BigDecimalArgumentConverterTest {

	private final BigDecimalArgumentConverter converter = new BigDecimalArgumentConverter();

	@Test
	public void testGetAsObject() throws Exception {
		Object object = converter.getAsObject(null, null, "123.00");
		assertThat(object).isInstanceOf(BigDecimalArgument.class);
		assertThat(object.toString()).isEqualTo("123.00");
	}

	@Test
	public void testGetAsObjectWithEmpty() throws Exception {
		Object object = converter.getAsObject(null, null, "");
		assertThat(object).isInstanceOf(BigDecimalArgument.class);
		assertThat(object.toString()).isEqualTo("");
	}

	@Test(expectedExceptions = ConverterException.class)
	public void testGetAsObjectWithInvalidInput() throws Exception {
		converter.getAsObject(null, null, "abc");
	}

	@Test
	public void testGetAsString() throws Exception {
		assertThat(converter.getAsString(null, null, BigDecimalArgument.of(new BigDecimal("123.321")))).isEqualTo("123.321");
	}

}

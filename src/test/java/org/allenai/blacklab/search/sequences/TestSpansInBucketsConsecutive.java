/*******************************************************************************
 * Copyright (c) 2010, 2012 Institute for Dutch Lexicology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.allenai.blacklab.search.sequences;

import java.io.IOException;

import org.allenai.blacklab.search.lucene.SpansStub;

import org.apache.lucene.search.spans.Spans;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestSpansInBucketsConsecutive {
	private SpansInBuckets hpd;

	@Before
	public void setUp() {
		int[] doc = { 1, 1, 2, 2, 2, 2 };
		int[] start = { 1, 2, 3, 4, 6, 7 };
		int[] end = { 2, 3, 4, 5, 7, 8 };
		Spans spans = new SpansStub(doc, start, end);
		hpd = new SpansInBucketsConsecutive(spans);
	}

	@Test
	public void testListInterface() throws IOException {
		Assert.assertTrue(hpd.next());
		//List<Hit> l = hpd.getHits();
		Assert.assertEquals(1, hpd.doc());
		Assert.assertEquals(2, hpd.bucketSize());
		Assert.assertEquals(1, hpd.start(0));
		Assert.assertEquals(2, hpd.end(0));
		Assert.assertEquals(2, hpd.start(1));
		Assert.assertEquals(3, hpd.end(1));

		Assert.assertTrue(hpd.next());
		//l = hpd.getHits();
		Assert.assertEquals(2, hpd.doc());
		Assert.assertEquals(2, hpd.bucketSize());
		Assert.assertEquals(3, hpd.start(0));
		Assert.assertEquals(4, hpd.end(0));

		Assert.assertTrue(hpd.next());
		//l = hpd.getHits();
		Assert.assertEquals(2, hpd.bucketSize());
		Assert.assertEquals(6, hpd.start(0));
		Assert.assertEquals(7, hpd.end(0));

		Assert.assertFalse(hpd.next());
	}

}

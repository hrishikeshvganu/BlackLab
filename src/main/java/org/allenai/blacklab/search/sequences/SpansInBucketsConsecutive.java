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

import org.apache.lucene.search.spans.Spans;

/**
 * Wrap a Spans to retrieve consecutive matches.
 *
 * This is used for repetition regex operators such as * and +.
 */
class SpansInBucketsConsecutive extends SpansInBucketsAbstract {
	public SpansInBucketsConsecutive(Spans source) {
		super(source);
	}

	@Override
	protected void gatherHits() throws IOException {
		int lastEnd = source.start();
		while (more && source.doc() == currentDoc && source.start() == lastEnd) {
			addHitFromSource();
			lastEnd = source.end();
			more = source.next();
		}
	}

}

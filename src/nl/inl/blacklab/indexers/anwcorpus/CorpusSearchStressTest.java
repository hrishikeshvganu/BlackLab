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
package nl.inl.blacklab.indexers.anwcorpus;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import nl.inl.blacklab.search.Hit;
import nl.inl.blacklab.search.Hits;
import nl.inl.blacklab.search.HitsWindow;
import nl.inl.blacklab.search.Searcher;
import nl.inl.blacklab.search.TextPattern;
import nl.inl.blacklab.search.TextPatternTerm;
import nl.inl.blacklab.search.grouping.HitPropertyRightContext;
import nl.inl.util.PropertiesUtil;
import nl.inl.util.Timer;
import nl.inl.util.XmlUtil;

/**
 * Simple test program to demonstrate corpus search functionality.
 */
public class CorpusSearchStressTest {
	public static void main(String[] args) throws IOException {
		// Read property file
		Properties properties = PropertiesUtil.getFromResource("anwcorpus.properties");

		// Where to create the index and UTF-16 content
		File indexDir = PropertiesUtil.getFileProp(properties, "indexDir", "index", null);

		// Create the BlackLab searcher object
		Searcher searcher = new Searcher(indexDir);
		try {
			// Perform the actual tests
			performTestSearches(searcher);
		} finally {
			searcher.close();
		}
	}

	private static void performTestSearches(Searcher searcher) {
		Timer timer = new Timer();

		TextPattern pattern = new TextPatternTerm("die");

		Hits hits = searcher.find("contents", pattern);
		System.out.println(hits.size() + " hits found; sorting...");

		// Heavy shit
		hits.sort(new HitPropertyRightContext());

		displayConcordances(searcher, new HitsWindow(hits, 0, 10));

		System.out.println(timer.elapsedDescription() + " elapsed");
		System.out.flush();
	}

	private static void displayConcordances(Searcher searcher, HitsWindow window) {
		window.findConcordances(true);
		for (Hit hit : window) {
			String left = XmlUtil.xmlToPlainText(hit.conc[0]);
			String hitText = XmlUtil.xmlToPlainText(hit.conc[1]);
			String right = XmlUtil.xmlToPlainText(hit.conc[2]);
			System.out.printf("[%05d:%06d] %45s[%s]%s\n", hit.doc, hit.start, left, hitText, right);
		}
	}
}
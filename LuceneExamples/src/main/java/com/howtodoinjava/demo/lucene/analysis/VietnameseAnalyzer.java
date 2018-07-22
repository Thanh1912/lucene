/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.howtodoinjava.demo.lucene.analysis;

import org.apache.lucene.analysis.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author duydo
 */
public class VietnameseAnalyzer extends StopwordAnalyzerBase {

    public static final CharArraySet VIETNAMESE_STOP_WORDS_SET;

    static {
        final List<String> stopWords = Arrays.asList(

        );
        final CharArraySet stopSet = new CharArraySet(stopWords, false);
        VIETNAMESE_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
    }


    /**
     * Returns an unmodifiable instance of the default stop words set.
     *
     * @return default stop words set.
     */
    public static CharArraySet getDefaultStopSet() {
        return DefaultSetHolder.DEFAULT_STOP_SET;
    }

    /**
     * Atomically loads the DEFAULT_STOP_SET in a lazy fashion once the outer class
     * accesses the static final set the first time.
     */
    private static class DefaultSetHolder {
        static final CharArraySet DEFAULT_STOP_SET = VIETNAMESE_STOP_WORDS_SET;
    }

    /**
     * Builds an analyzer with the default stop words: {@link #getDefaultStopSet}.
     */
    public VietnameseAnalyzer() {
        this(DefaultSetHolder.DEFAULT_STOP_SET);
    }

    /**
     * Builds an analyzer with the default stop words
     */
    public VietnameseAnalyzer(CharArraySet stopWords) {
        super(stopWords);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final Tokenizer tokenizer = new VietnameseTokenizer();
        TokenStream tokenStream = new LowerCaseFilter(tokenizer);
        tokenStream = new StopFilter(tokenStream, stopwords);
        return new TokenStreamComponents(tokenizer, tokenStream);
    }
}

/* Copyright 2016 Google Inc.
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
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private static ArrayList<String> wordList = new ArrayList<>();
    private static HashSet<String> wordSet = new HashSet<>();
    private static HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private static HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();

    public AnagramDictionary(Reader reader) throws IOException {
        System.out.println("inside Anagram Dict");
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);

            if (wordSet.contains(word)) {
                String sortedWord = sortLetters(word);

                if (lettersToWord.containsKey(sortedWord)) {
                    lettersToWord.get(sortedWord).add(word);
                } else {
                    ArrayList<String> newList = new ArrayList<>();
                    newList.add(word);
                    lettersToWord.put(sortedWord, newList);
                }

                if (sizeToWords.containsKey(word.length())) {
                    sizeToWords.get(word.length()).add(word);
                } else {
                    ArrayList<String> nList = new ArrayList<>();
                    nList.add(word);
                    sizeToWords.put(word.length(), nList);
                }
            }
        }
        System.out.println(wordSet.toString());
        System.out.println(lettersToWord.toString());
    }

    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word) && !word.contains(base)) {
            return true;
        }
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String sortedWrod = sortLetters(targetWord);
        result = lettersToWord.get(sortedWrod);
        return result;
    }

    // Helper function to sort string
    public String sortLetters(String word) {
        String tempWord = word;
        char[] tempCharArray = tempWord.toCharArray();
        Arrays.sort(tempCharArray);
        return new String(tempCharArray);
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();

        for (char c = 'a'; c <= 'z'; c++) {
            ArrayList<String> temp;
            temp = (lettersToWord.get(sortLetters(word + c)));

            if (temp != null){
                result.addAll(temp);
            }
        }
//        result = lettersToWord.get(sortLetters(word));
        System.out.println(result);
        return result;
    }

    public String pickGoodStarterWord() {

        int wordLength = DEFAULT_WORD_LENGTH;

        Random r = new Random();

        while (true) {
            for (int i = wordLength; i <= MAX_WORD_LENGTH; i++) {
                ArrayList<String> lengthWords = sizeToWords.get(i);
                int randomNumber = r.nextInt(lengthWords.size());
                System.out.println(randomNumber);
                String word = lengthWords.get(randomNumber);

                if (getAnagrams(word).size() >= MIN_NUM_ANAGRAMS) {
                    return word;
                }
            }
        }

    }
}

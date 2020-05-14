package com.twb.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author twb
 *
 */
public class BcryptClient {

	private static Logger logger = LoggerFactory.getLogger(BcryptClient.class);
	
	public static void main(String[] args) throws IOException {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		logger.info("Enter the word to Bcrypt :");
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		
		String word = buf.readLine();
		
		String bcryptWord = encoder.encode(word);
		
		logger.info("Encrypt Word : "+bcryptWord);
		logger.info("match Word : "+encoder.matches(word, bcryptWord));

		List<Integer> list = Arrays.asList(2, 6, 8, 9, 8);
		List supList = list.stream().filter(e -> e>7).collect(Collectors.toList());
        int a = list.stream().filter(e -> e>7).findFirst().get();
		IntSummaryStatistics stats = list.stream().mapToInt(x -> x).summaryStatistics();
		System.out.println("Highest number in List : " + stats.getMax());
		System.out.println("Lowest number in List : " + stats.getMin());
		System.out.println("Sum of all numbers : " + stats.getSum());
		System.out.println("Average of all numbers : " + stats.getAverage());


	}

}

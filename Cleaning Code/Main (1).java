package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

	public static String text;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Formatter output;
		
		try{
			  Path   p=Paths.get("out.txt").toAbsolutePath();
			output = new Formatter(p.toString(), "utf-8");
			
		Scanner	input = new Scanner(Paths.get("input.txt"),"utf-8");
		
		while (input.hasNextLine()) {
			String line = input.nextLine();
			//String lineout = new String();
		//	List<String>	filtered = Arrays.asList(line.split("\\s"));
			
			 line = line.replaceAll("((www\\.[^\\s]+)|(https?://[^\\s]+))", " ");// remove url

	            //remove user names
			 line = line.replaceAll("@[^\\s]+", " ");// remove @

			 //remove # from hash tag
			 line = line.replaceAll("#[^\\s]+", "");
			 
			 // remove #

	            
			 line = line.replaceAll("\\p{Punct}+", " ");//remove punctuation
			 line = line.replaceAll("RT", " ");
		
			 line = Main.removeEmojiAndSymbol(line);
		
	      
					output.format("%s\n",line);
		}
		
		
		input.close();
		output.close();
		// System.out.println(text);
		}
		catch(NoSuchElementException excp) {
			System.err.println(excp.getMessage());
		} catch (FormatterClosedException excp){
			System.err.println(excp.getMessage());
		} catch (IllegalStateException excp){
			System.err.println(excp.getMessage());
		}
	

	}
	// removeEmojiAndSymbol function to remove emojis or you can use emoji4j library  
	public static	String removeEmojiAndSymbol( String content) {
	    String utf8tweet = "";
	    try {
	        byte[] utf8Bytes = content.getBytes( "UTF-8");
	 
	        utf8tweet = new String( utf8Bytes, "UTF-8");
	    } catch (UnsupportedEncodingException e ) {
	        e.printStackTrace();
	    }
	   Pattern unicodeOutliers = Pattern.compile(
	            
	        		"(?:[\\u2700-\\u27bf]|" +

        "(?:[\\ud83c\\udde6-\\ud83c\\uddff]){2}|" +
        "[\\ud800\\udc00-\\uDBFF\\uDFFF]|[\\u2600-\\u26FF])[\\ufe0e\\ufe0f]?(?:[\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]|[\\ud83c\\udffb-\\ud83c\\udfff])?" +

        "(?:\\u200d(?:[^\\ud800-\\udfff]|" +

        "(?:[\\ud83c\\udde6-\\ud83c\\uddff]){2}|" +
        "[\\ud800\\udc00-\\uDBFF\\uDFFF]|[\\u2600-\\u26FF])[\\ufe0e\\ufe0f]?(?:[\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]|[\\ud83c\\udffb-\\ud83c\\udfff])?)*|" +

        "[\\u0023-\\u0039]\\ufe0f?\\u20e3|\\u3299|\\u3297|\\u303d|\\u3030|\\u24c2|[\\ud83c\\udd70-\\ud83c\\udd71]|[\\ud83c\\udd7e-\\ud83c\\udd7f]|\\ud83c\\udd8e|[\\ud83c\\udd91-\\ud83c\\udd9a]|[\\ud83c\\udde6-\\ud83c\\uddff]|[\\ud83c\\ude01-\\ud83c\\ude02]|\\ud83c\\ude1a|\\ud83c\\ude2f|[\\ud83c\\ude32-\\ud83c\\ude3a]|[\\ud83c\\ude50-\\ud83c\\ude51]|\\u203c|\\u2049|[\\u25aa-\\u25ab]|\\u25b6|\\u25c0|[\\u25fb-\\u25fe]|\\u00a9|\\u00ae|\\u2122|\\u2139|\\ud83c\\udc04|[\\u2600-\\u26FF]|\\u2b05|\\u2b06|\\u2b07|\\u2b1b|\\u2b1c|\\u2b50|\\u2b55|\\u231a|\\u231b|\\u2328|\\u23cf|[\\u23e9-\\u23f3]|[\\u23f8-\\u23fa]|\\ud83c\\udccf|\\u2934|\\u2935|[\\u2190-\\u21ff]",
	            Pattern.UNICODE_CASE |
	            Pattern.CANON_EQ |
	            Pattern.CASE_INSENSITIVE
	        );
	   //Pattern unicodeOutliers =   Pattern.compile("U+1F3FB–U+1F3FF");
	   
	    Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(utf8tweet);
	 
	    utf8tweet = unicodeOutlierMatcher.replaceAll(" ");
	    return utf8tweet;
	}
}

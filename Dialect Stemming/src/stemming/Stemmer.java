/*
 * Eman Alnkhilan- java code is based on rule based algorithm for Gulf dialect
 * 
 */


package stemming;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class Stemmer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//String fin = "files/WLF.txt";
		 
		
		   Scanner input;
		   Formatter output;
		   //String  fout = "files/out.txt";
		  // File newFile = new File("files", "out.txt");
		 //  BufferedWriter output = null;
		   try{
			  // newFile.createNewFile();
			 
	            //writer.write("Hello world!");
			  Path   p=Paths.get("output.txt").toAbsolutePath();
		// FileWriter out	=new FileWriter(p.toString());
			// out.write("Hello world!");
			   output = new Formatter(p.toString(), "utf-8");
		   input = new Scanner(Paths.get("WLF"),"utf-8");
		   
		   //naw= non Arabic words
		   List<String> naw=Arrays.asList("بشت", "تجوري", "دلاغ" ,"ابجور" ,"أبجور", "دريول", "فريزر" , "بصم"  ,"بلكون ", "بخت", "تليفون" , "تلفزيون"  ,"خوش" ,  "روج" , "ساندويش", "سندويش"  );// non Arabic words without prefix & suffix 
			List<String> stopwordsList = Arrays.asList("ام","أم","بدون"	,"عشرة",	"على",	"مقابل"	,"حاليا"	,"التى"	,"حوالى"	,"الماضي",
		            "انا",	"ب",	"عند"	,"اذا",	"هذا",	"امس"	,"ايام",	"انها",
					"انها",	"مافيه",	"لان"	,"عندما"	,"احد"	,"هاذا",	"السابق"	,"خلال","جميع",
					"شي"	,"مافي",	"اثر"	,"عليه",	"برس"	,"بن",	"التي"	,"الذين"	,"الوقت",
					"ع",	"انا"	,"ا"	,"عاما",	"بشكل",	"الا"	,"الان"	,"اول",
					"له"	,"بكرا",	"لانهم",	"عن",	"حتى",	"كم",	"اما",	"امام"	,"ضمن",
					"لها"	,"واذا",	"انها"	,"عام",	"اعلنت",	"وغير"	,"اكد",	" الذي"	,"انه",
					"لو"	,"ي","ا",	"عليها",	"باسم"	,"به","اكثر",	"الاول",	"المقبل",
					"اللي"	,"لمن"	,"لماذا",	"سنوات",	"صباح",	"ان"	,"ثلاثة"	,"ذلك",	"و",
					"ليش"	,"لكل",	"مافي",	"سنة"	,"شخصا" ,	"اف"	,"ايضا"	,"بين"	,"الى",
					"مثل"	,"لهاذا" ,	"عشر"	,"تم"	,"اطار"	,"او"	,"الذاتي"	,"دون",	"كان","كن",
					"مره" ,"راح"	,"عدم",	"اعادة"	,"اجل"	,"حيث",	"الذى",	
					"يا"	,"انك"	,"عدة"	,"بعد"	,"اخرى"	,"بها"	,"الثاني"	,"حين",	"قوة",
					"يقول"	,"ان"	, "عدد"	,"ف",	"اربعة"	,"اي",	"الاخيرة"	,"حول"	,"كما",
					"يقولون"	,"بل",	"فان"	,"كان"	,"هذه"	,"كانت"	,"في",	"من",	"لها",
					"مع",	"واحد"	,"قبل"	,"لدى"	,"وان"	,"واوضح"	,"كل"	,"هو",	"يكون",
					"مساء"	,"واضاف"	,"قال",	"نحو"	,"واكد",	"مايو"	,"له",	"هي"	,"وقالت",
					"هذا"	,"واضافت"	,"وكانت",	"وقف"	,"فيها"	,"يمكن"	,"مليار"	,"لوكالة",	
					"منذ"	,"هناك"	,"فيه",	"ومن"	, "منها"	,"مليون"	,"لكن"	,"وفي"	,
					"وقد" ,	"وقال"	,"كلم",	"وهو"	,"وهي",	"يوم"	,"نهاية"	,"لقاء"	,"ومع","مع",
					"نفسه"	, "وكان"	,"زيارة"	,	"ثم",	"ايار"	,"الاولى"	,"اليوم"	,"هل","اين","متى","أين","تحت","فوق","بين","بينما","فبينما",
					"لهذا"	,"بكره"	,"بعض"	,"بان"	,"صفر"	,"الثانية","الف","ما","يا","يومياً","كل","إذا","علي","عليك","عليهم","ماذا","قط",
					"عليكم","فجرا","فجر","عشان","علشان","فقط","بإذن","بس","عصر","عصرا","ظهر","ظهرا","الجمعه","الخميس","الاربعاء","الأربعاء","الثلاثاء","الأثنين","الاثنين","الاحد","الأحد","السبت");
			 List<Character> vowels= Arrays.asList ('ا','ي','و');
			 List<String> nostemming=Arrays.asList("اللهٍ","اللهَ","اللهُ","الله"); // words must not to be stemmed 	
		   int lettercount=0;
		   String word=null;
		   while (input.hasNextLine()) {
			   String line = input.nextLine(); 
			   if(!line.isEmpty()){
			   List<String> tokens = Arrays.asList(line.split("\\s"));
			// if word is <=3 then it is stemmed
			   for(int i=0; i<tokens.size();++i){
				  
				    lettercount= tokens.get(i).length();
				    
				    word=tokens.get(i);
				    if (!stopwordsList.contains(word)) {
				    	if(lettercount<=3){
				    		if(naw.contains(word)){// to check the word is Arabic or not
				    			 System.out.println(word+" "+"ليست من أصل عربي");	
				    		}
				    		else{
				    		 //System.out.println(word+" "+"هو الجذر");
				    		 output.format( word+" ");}
				    		 
				    	}
				    if (lettercount>3 && !nostemming.contains(word)){
				    	// to remove prefix
				    	if(word.startsWith("ولل")){
				    		String pre="ولل";
				    		word=word.substring ( pre.length ( ) );
				    	}
				    	else if(word.startsWith("لل")){
				    		String pre="لل";
				    		word=word.substring ( pre.length ( ) );
				    						    	}
				    	else if(word.startsWith("وال")){
				    		String pre="وال";
				    		word=word.substring ( pre.length ( ) );
				    	}
				    	else if(word.startsWith("بال")){
				    		String pre="بال";
				    		word=word.substring ( pre.length ( ) );
				    	}
				    	else if(word.startsWith("ال")){
				    		String pre="ال";
				    		word=word.substring ( pre.length ( ) );
				    	}
				    	else if( word.startsWith("وش")){
				    		 String pre="وش";
					    		word=word.substring ( pre.length ( ) );
			    		 }
				    	else if(word.startsWith("اش")){
			    			 String pre="اش";
					    		word=word.substring ( pre.length ( ) );
			    		 }
				    	
			    		 
				    	 if(word.length()<=3){
				    		// System.out.println(word+" "+"هو الجذر");
				    		 output.format( word+" "); }
				    		// to remove suffix				        
				    	 else if(word.length()>3 ){
				    		  if(word.endsWith( "الكم")){
					    			String suf="الكم";
					    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
					    		}
				    		  else if(word.endsWith( "ونهم")){
					    			String suf="ونهم";
					    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
					    		}
				    		  else if(word.endsWith( "تني")){
					    			String suf="تني";
					    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
					    		}
					    		else if(word.endsWith( "ونه")){
					    			String suf="ونه";
					    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
					    		}
					    		else if(word.endsWith( "الك")){
					    			String suf="الك";
					    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
					    		}
					    		else if(word.endsWith( "تهم")){
					    			String suf="تهم";
					    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
					    		}
					    		else if(word.endsWith( "تهن")){
					    			String suf="تهن";
					    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
					    		}
				    		  else if(word.endsWith("ات")){
				    			String suf="ات";
					    		//word=word.substring ( suf.length ( ) );
					    		word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith("ون")){
				    			String suf="ون";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		
				    		else if(word.endsWith("كم")){
				    			String suf="كم";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith("وك")){
				    			String suf="وك";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		
				    		else if(word.endsWith( "ته")){
				    			String suf="ته";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith( "هم")){
				    			String suf="هم";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		
				    		else if(word.endsWith( "تي")){
				    			String suf="تي";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith( "ني")){
				    			String suf="ني";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith( "وا")){
				    			String suf="وا";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith( "ها")){
				    			String suf="ها";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith( "نا")){
				    			String suf="نا";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith("ك")){
				    			String suf="ك";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith( "ه")){
				    			String suf="ه";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith( "ة")){
				    			String suf="ة";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		
				    		else if(word.endsWith( "ي")){
				    			String suf="ي";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith( "و")){
				    			String suf="و";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		else if(word.endsWith( "ت")){
				    			String suf="ت";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		  
				    		else if(word.endsWith( "اً")){
				    			String suf="اً";
				    			word=word.substring ( 0, word.length ( ) - suf.length ( ) );
				    		}
				    		 if (word.length()<=3){
				    			 
				    			 if(naw.contains(word)){// to check the word is Arabic or not
					    			 System.out.println(word+" "+"ليست من أصل عربي");	
					    		}
				    			 else{
				    				// /System.out.println(word+" "+"هو الجذر");
				    				 if(vowels.contains(word.charAt(word.length()-1))){
		    							 word = new StringBuilder(word).deleteCharAt(word.length()-1).toString();}
				    				 output.format( word+" ");
				    			 }
				    		 }
				    		 if(word.length()>3 ){
				    		if(naw.contains(word)){// to check the word is Arabic or not
				    			 System.out.println(word+" "+"ليست من أصل عربي");	
				    		}
				    		if(!naw.contains(word))	{
				    			 if(word.startsWith("با")){
				    				 String pre="با";
				    				 word=word.substring ( pre.length ( ) );
					    		 }
				    			 else if(word.startsWith("وا")){
					    			 String pre="وا";
				    				 word=word.substring ( pre.length ( ) );
					    		 }
				    			 
				    			 else if(word.startsWith("مت")){
					    			 String pre="مت";
				    				 word=word.substring ( pre.length ( ) );
					    		 }
				    			 else if(word.startsWith("ان")){
					    			 String pre="ان";
				    				 word=word.substring ( pre.length ( ) );
					    		 }
				    			 
				    			 else if(word.startsWith("ما")){
					    			 String pre="ما";
				    				 word=word.substring ( pre.length ( ) );
					    		 }
				    			 else if(word.startsWith("من")){
					    			 String pre="من";
				    				 word=word.substring ( pre.length ( ) );
					    		 }
				    			 else if(word.startsWith("يت")){
					    			 String pre="يت";
				    				 word=word.substring ( pre.length ( ) );
					    		 }
				    			 else if(word.startsWith("ا")){
					    			 String pre="ا";
				    				 word=word.substring ( pre.length ( ) );
					    		 }
				    			
				    			 else if(word.startsWith("ت")){
					    			 String pre="ت";
				    				 word=word.substring ( pre.length ( ) );
					    		 }
				    			 else if(word.startsWith("ي")){
					    			 String pre="ي";
				    				 word=word.substring ( pre.length ( ) );
					    		 }
				    			 else if(word.startsWith("م")){
					    			 String pre="م";
				    				 word=word.substring ( pre.length ( ) );
					    		 }
				    			 
					    		 if (word.length()<=3){
					    			// System.out.println(word+" "+"هو الجذر");
					    			 output.format( word+" ");
					    		 }
					    		 if(word.length()>3 ){
					    			
					    			 char[] ch=word.toCharArray();
					    			
					    				 if(ch[0]=='م'&& ch[2]=='ت'){
					    					
					    					 word = new StringBuilder(word).deleteCharAt(0).toString();
					    					 word = new StringBuilder(word).deleteCharAt(1).toString();
					    				 }
					    				 else if(ch[0]=='ا'&& ch[2]=='ت'){
					    					 word = new StringBuilder(word).deleteCharAt(0).toString();
					    					 word = new StringBuilder(word).deleteCharAt(1).toString();
					    				 }
					    				 else if(ch[0]=='ن'&& ch[2]=='ت'){
					    					 word = new StringBuilder(word).deleteCharAt(0).toString();
					    					 word = new StringBuilder(word).deleteCharAt(1).toString();					    				 }
					    				 else if(ch[0]=='ت'&& ch[2]=='ت'){
					    					 word = new StringBuilder(word).deleteCharAt(0).toString();
					    					 word = new StringBuilder(word).deleteCharAt(1).toString();
					    				 } 
					    				 
					    				 if (word.length()<=3){
							    			// System.out.println(word+" "+"هو الجذر");
							    			 output.format( word+" ");
							    		 } 
					    				// System.out.println(word);
					    				 if(word.length()>3){
					    					
					    					/*					    					 
					    					   to remove vowels from a word, If we have a vowel with non-vowels neighbored then 
                                                deletes it. 
                                               If we have two consecutive vowel letters then we have to delete one of them according to the following 
                                             order (ا then و and then ي)
                                                                                                      
					    					  */
					    					 for(int j=1; j<word.length();++j){ // we start from second letter in a word because vowels are not in beginning   
					    						 char[] letter=word.toCharArray(); 
					    					 if(vowels.contains(letter[j])&& j!=word.length()-1){
					    							 if(!vowels.contains(letter[j+1])){// to check if we have only one vowel 
					    								//if(!vowels.contains(letter[j+2])){
					    								//letter[j]="";	
					    						 word = new StringBuilder(word).deleteCharAt(j).toString();
					    						j=j-1;
					    						
					    							  //}
					    								}
					    							      
					    								else if (vowels.contains(letter[j+1])){// if we have two consecutive vowel letters 
					    									if(letter[j]=='ا'){
					    										 word = new StringBuilder(word).deleteCharAt(j).toString();
					    										 j=j-1;
					    									}
					    									else if(letter[j]=='و' && letter[j+1]=='ا'){
					    										word = new StringBuilder(word).deleteCharAt(j+1).toString();
					    										j=j-1;
					    									}
					    									else if (letter[j]=='و' && letter[j+1]=='ي'){
					    										word = new StringBuilder(word).deleteCharAt(j).toString();
					    										j=j-1;
					    									}
					    									else if (letter[j]=='ي'){
					    										word = new StringBuilder(word).deleteCharAt(j+1).toString();
					    										j=j-1;
					    									 }
							    						    }
					    					             }
					    					 if(word.length()<=3){
					    						    if(vowels.contains(word.charAt(word.length()-1))){
					    							 word = new StringBuilder(word).deleteCharAt(word.length()-1).toString();}
					    						// output.format( word+" ");
					    						 j=word.length();
					    							break;
					    							}
					    							//System.out.println(word+" "+"هو الجذر");
					    						
					    					    
					    				       } 
					    					 
					    					 
					    						 // to remove duplicate letters like in "رججل"  to be "رجل" 
					    						 Set<Character> set=new LinkedHashSet<Character>();
					    						 for(char c:word.toCharArray())
					    						 {
					    						     set.add(Character.valueOf(c));
					    						 }
					    						 StringBuilder sb = new StringBuilder();
					    						 for (Character character : set) {
					    						     sb.append(character);
					    						 }
					    						 word=sb.toString();
					    						 //System.out.println(word);
					    						 output.format(word+" ");
					    					
					    					 
					    					 }
					    		         }
				    		        }
				    		    }
				    	   }
				      }
				   
			      }
			   
		       }
			  }
			   output.format( "\n");
		   }
		   input.close();
		   output.close();
		   }
		   catch (IOException ioException){
				System.err.println(ioException.getMessage());
				System.exit(-1);
			} catch(NoSuchElementException excp) {
				System.err.println(excp.getMessage());
			} catch (FormatterClosedException excp){
				System.err.println(excp.getMessage());
			} catch (IllegalStateException excp){
				System.err.println(excp.getMessage());
			}

	}

}

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class TagTest {

	@SuppressWarnings({ "unused", "null", "rawtypes" })
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		int nb_doc=1,nb_in_doc=0,total_number=0;
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// step 1 : create a tagged corpus
		
		
		while (nb_doc<=51)
		{
			String sample, corpus = "";
			String tagged;
			String[] arrList_tagged;
			ArrayList<Word_Tag> arrList_Word_Tag = new ArrayList<Word_Tag>();
			ArrayList<Sentences> arryList_sentences = new ArrayList<Sentences>();
			ArrayList<String> Time_expressions_list = new ArrayList<String>();
			ArrayList<String> sentences = new ArrayList<String>();
			String[] split;
			String[] split_time_expression;
			int i = 0;
		FileInputStream filestream = new FileInputStream("C:/Users/dahmri/Desktop/TEST_FOR_Corpus/doc"+nb_doc+".txt");
		DataInputStream in1 = new DataInputStream(filestream);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
		while ((sample = br1.readLine()) != null) {

			corpus = corpus + " " + sample;

		}
		
		MaxentTagger tagger = new MaxentTagger("Tagger/arabic.tagger");
		tagged = tagger.tagString(corpus);
		

		// Step 2 : separate the tag from the word and put them on a list
		arrList_tagged = tagged.split(" ");
		while (arrList_tagged.length != i) {
			split = arrList_tagged[i].split("/");
			Word_Tag A_Word_Tag = new Word_Tag();
			A_Word_Tag.Tag = split[1];
			A_Word_Tag.Word = split[0];
			A_Word_Tag.Word = clean(A_Word_Tag.Word);
			A_Word_Tag.word_position();
			arrList_Word_Tag.add(i, A_Word_Tag);

			i++;
		}

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Step 3 : detect a short time expression dans le corpus.
		FileInputStream fstream = new FileInputStream("C:/Users/dahmri/workspace/Tgger test/Reg_time_expressions/Reg_time_expressions.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		int n = 0;
		while ((sample = br.readLine()) != null) 
		{
			Pattern p = Pattern.compile(sample);
			Matcher m = p.matcher(corpus);
			
			
			while (m.find()) 
			{
				String S = m.group();
				
				if (!Time_expressions_list.contains(S)) Time_expressions_list.add(S);

			}

		}
		
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// step 4 : from the Time_expressions_list we detect Time_expressions in
		// arrList_Word_Tag
		String arrList_Word;
		i = 0;
		int j = 0;
		while (Time_expressions_list.size() != i) 
		{

			String Time_expression = Time_expressions_list.get(i);
			j = 0;
			split_time_expression = Time_expression.split(" ");
			int lenght = split_time_expression.length;
			Word_Tag word = new Word_Tag();

			while (arrList_Word_Tag.size() != j) 
			{
				arrList_Word = arrList_Word_Tag.get(j).Word;
				arrList_Word = clean(arrList_Word);
				if (lenght == 1) 
				{
					// System.out.println("i am in length=1");

					if (arrList_Word.equals(Time_expression))
						arrList_Word_Tag.get(j).Time = true;

				}

				if (lenght > 1) 
				{
					int c = 0, rep = 1;
					// System.out.println("i am in length >1");
					//System.out.println(lenght);
					while (lenght > c) 
					{
						arrList_Word = arrList_Word_Tag.get(j + c).Word;
						if (!arrList_Word.equals(split_time_expression[c]))
						{
							// System.out.println("i am in length > 1 and not equals");
							rep = 0;
							c = lenght + 1;
						}

						c++;
					}

					if (rep == 1) 
					{
						c = 0;
						// System.out.println("i am in length > 1 and  equals");
						// c=0;
						String new_Word = "";

						while (lenght > c) 
						{

							new_Word = new_Word + " " + arrList_Word_Tag.get(j + c).Word;
							//System.out.println(arrList_Word_Tag.get(j + c).Word);

							// arrList_Word_Tag.get(j+c).Word="";
							// arrList_Word_Tag.get(j+c).Tag="NN";
							// arrList_Word_Tag.get(j+c).Time=false;

							c++;
						}
						
						c=0;
						while (lenght > c+1) 
						{
							//System.out.println(arrList_Word_Tag.get(j + lenght-c-1).Word);
							arrList_Word_Tag.remove(j + (lenght-1)-c);
							c++;
						}
						
						word.Time = true;
						word.Word = new_Word;
						word.Tag = "Time";
						//word.Tag = arrList_Word_Tag.get(j).Tag;
						arrList_Word_Tag.set(j, word);

					}

				}

				j++;
			}

			i++;
		}
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		int k = 0;

		/*
		 * while (arrList_Word_Tag.size()!=k){
		 * 
		 * if (arrList_Word_Tag.get(k).Time==true)
		 * System.out.println(arrList_Word_Tag
		 * .get(k).Word+"---"+arrList_Word_Tag.get(k).Tag);
		 * 
		 * //System.out.println(arrList_Word_Tag.get(k).Word); k++;}
		 */

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// step 5 : detect sentences in arrList_Word_Tag
		k = 0;
		i = 0;
		String sentence = "";
		int first = 0;
		int sentence_number = 1;
		int wp = 1;
		int add = 0;
		while (arrList_Word_Tag.size() != k) 
		{
			// System.out.println(arrList_Word_Tag.get(k).Word+"---"+arrList_Word_Tag.get(k).word_position);
			if (arrList_Word_Tag.get(k).word_position == null) 
			{

				//if (arrList_Word_Tag.get(k).Time == false) sentence = sentence + " " + arrList_Word_Tag.get(k).Word;
				//else sentence = sentence + " <>" + arrList_Word_Tag.get(k).Word+ "<> ";
				// System.out.println(arrList_Word_Tag.get(k).Word);

				if (first == 0) 
				{
					arrList_Word_Tag.get(k).word_position = "FW";
					first++;
				}

				else 
				{
					arrList_Word_Tag.get(k).word_position = Integer.toString(wp);
					wp++;
				}

			}

			else if ((arrList_Word_Tag.get(k).word_position == "SEOS")) 
			{
				arrList_Word_Tag.get(k - 1).word_position = "LW";
				add = 1;
			}

			else 
			{ // it means that word_position=="LW"

				add = 1;
			}

			if (arrList_Word_Tag.size() == k + 1) 
			{
				arrList_Word_Tag.get(k).word_position = "LW";
				add = 1;
			}

			if (add == 1) 
			{
				first = 0;
				wp = 1;
				add = 0;
			}

			k++;
		}

		if (arrList_Word_Tag.get(arrList_Word_Tag.size() - 1).Word.equals("")) arrList_Word_Tag.get(arrList_Word_Tag.size() - 1).word_position = "LW";
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// step 6 : creat the structre arryList_sentences
		i = 0;
		k = 0;
		j = 0;
		int nb_word = 0;
		int nb_short_time_expression = 0;
		Sentences s = new Sentences();
		add = 0;
		while (arrList_Word_Tag.size() != k) 
		{
			if (arrList_Word_Tag.get(k).word_position != "SEOS")
			{
				s.nb_word++;
				s.list_of_WordTag.add(arrList_Word_Tag.get(k));
				if (arrList_Word_Tag.get(k).Time == true)
				{
					s.nb_short_time_expression++;
					s.time_expression_index[j] = i;
					j++;
				}
				if (arrList_Word_Tag.get(k).word_position == "LW") add = 1;

				i++;
			}

			if (add == 1) 
			{
				arryList_sentences.add(s);
				s = new Sentences();
				i = 0;
				j = 0;
				add = 0;
			}

			k++;
		}

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		 * int p=0; k=0; while (arryList_sentences.size()!=k){ i=0; j=0;
		 * 
		 * System.out.println(
		 * "--------------------------------------------------------------");
		 * 
		 * //
		 * System.out.println(arryList_sentences.get(k).list_of_WordTag.get(i)
		 * .Word); while (j< arryList_sentences.get(k).nb_short_time_expression)
		 * { p=arryList_sentences.get(k).time_expression_index[j];
		 * System.out.println
		 * (arryList_sentences.get(k).list_of_WordTag.get(p).Word); j++;}
		 * 
		 * 
		 * 
		 * 
		 * System.out.println(
		 * "--------------------------------------------------------------");
		 * k++;}
		 */
       /*
		k = 0;
		while (arrList_Word_Tag.size() != k) {

			if (arrList_Word_Tag.get(k).word_position != "SEOS") {
				if (arrList_Word_Tag.get(k).word_position == "LW") {
					System.out.println(arrList_Word_Tag.get(k).Word + "---"
							+ arrList_Word_Tag.get(k).Tag + "---"
							+ arrList_Word_Tag.get(k).Time + "---"
							+ arrList_Word_Tag.get(k).word_position);
					System.out.println("\n");
				}

				else
					System.out.println(arrList_Word_Tag.get(k).Word + "---"
							+ arrList_Word_Tag.get(k).Tag + "---"
							+ arrList_Word_Tag.get(k).Time + "---"
							+ arrList_Word_Tag.get(k).word_position);
				// System.out.println(arrList_Word_Tag.get(k).Word);
			}
			k++;
		}
        */
		// step 7 : detecte long time expression on a sentence

		k = 0;
		while (arryList_sentences.size() != k) 
		{
			// System.out.println("--------------------------------------------------------------"+k);

			if (arryList_sentences.get(k).nb_short_time_expression != 0) 
			{

				lookfor(0, 0, k, arryList_sentences);
				int t = 0;
				while (t != arryList_sentences.get(k).TEP.size()) 
				{
					int b = arryList_sentences.get(k).TEP.get(t).begin;
					int e = arryList_sentences.get(k).TEP.get(t).end;
					String ss = "";
					int c = arryList_sentences.get(k).time_expression_index[b];
					int d = arryList_sentences.get(k).time_expression_index[e];
					// System.out.println(c+"  ___  "+d);
					if (c - d == 0)
					{
						if (is_found(arryList_sentences.get(k).list_of_WordTag.get(c).Word) == true) // short time expression alone ----> it is not a time expressions if it is alone
						{    //System.out.println("  _IS FOUND = TRUE__  ");
							arryList_sentences.get(k).time_expression_index[b] = -1;
							arryList_sentences.get(k).TEP.get(t).begin = -1;
							arryList_sentences.get(k).TEP.get(t).end = -1;
						}
						else {ss = arryList_sentences.get(k).list_of_WordTag.get(c).Word;System.out.println(ss);nb_in_doc++; total_number++;}
						
					} 
					else 
					{
						while (c <= d) {
							ss = ss+ " "+ arryList_sentences.get(k).list_of_WordTag.get(c).Word;
							c = c + 1;
						}
						System.out.println(ss);
						nb_in_doc++; 
						total_number++;
					}
					
					
					
					t++;
				}

			}

			k++;
		}

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		/*
		int nbs1=0,nbs2 = 0,t=0;k=0; String display = null;
		while (nbs1 != arryList_sentences.size()) 
		{    nbs2=0;t=0;
		    if (arryList_sentences.get(k).TEP.size()!=0)
		    {
			while (nbs2 != arryList_sentences.get(k).TEP.size()) 
			{
			   int begin=arryList_sentences.get(k).TEP.get(t).begin;
			   int end=arryList_sentences.get(k).TEP.get(t).end;
			   display=null;
			   if (begin!=-1 & end !=-1)
			   {
			   while (begin <= end) 
			    {
				   display = display+ " "+ arryList_sentences.get(k).list_of_WordTag.get(begin).Word;
				   begin++;
				}
			   System.out.println(display);
			   }
			   nbs2++;t++;
			 }
		    }
			nbs1++;k++;
		}
		
		
		
		*/
		System.out.println("in doc "+nb_doc+"  we found : "+nb_in_doc);
		nb_doc++;
		nb_in_doc=0;
	}
		System.out.println("the total is : "+total_number);
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static String clean(String s) {

		if (s.endsWith(" "))
			s = s.replace(" ", "");
		if (s.startsWith(" "))
			s = s.replace(" ", "");

		return s;
	}

	public static boolean is_found(String s) throws IOException 
	{
		FileInputStream fstream = new FileInputStream("C:/Users/dahmri/workspace/Tgger test/Reg_time_expressions/short_Time_expressions_file.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		int n = 0;
		String line;
		while ((line = br.readLine()) != null) {

			Pattern p = Pattern.compile(line);
			Matcher m = p.matcher(s);

			if (m.find() == true)
				return true;

		}

		return false;
	}

	public static void lookfor(int index, int begin, int position,ArrayList<Sentences> sen) 
	{
		Time_expression_position e = new Time_expression_position();
		if (index + 1 != sen.get(position).nb_short_time_expression) 
		{
			int d = sen.get(position).time_expression_index[index + 1]- sen.get(position).time_expression_index[index];

			// end = index+1;
			// System.out.println(d);
			if (d == 1) lookfor(index + 1, begin, position, sen);
			
			else if (d == 2) 
			{
				int p = sen.get(position).time_expression_index[index] + 1;
				// System.out.println(sen.get(position).list_of_WordTag.get(p).Word);
				if (sen.get(position).list_of_WordTag.get(p).Tag.equals("IN")||sen.get(position).list_of_WordTag.get(p).Tag.equals("CC")) lookfor(index + 1, begin, position, sen);
				 else 
				 {
					e.begin = begin;
					e.end = index;
					sen.get(position).TEP.add(e);
					lookfor(index + 1, index + 1, position, sen);
				 }
			} 
			
			else 
			{
				// System.out.println(begin+"    "+index+1);
				e.begin = begin;
				e.end = index;
				sen.get(position).TEP.add(e);
				lookfor(index + 1, index + 1, position, sen);

			}

		}

		else 
		{
			e.begin = begin;
			e.end = index;
			sen.get(position).TEP.add(e);
			return;
		}

	}

}

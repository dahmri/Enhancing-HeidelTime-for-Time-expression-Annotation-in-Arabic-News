import java.util.ArrayList;


public class Word_Tag {

	String Word=null;
	String Tag=null;
	boolean Time= false;
	String word_position=null;
	
	
	
	public  Word_Tag() {
		
		
		
		
	}
	
	public void  word_position() {
		ArrayList<String> punctuation = new ArrayList <String> () ;
		punctuation.add("؟");
		punctuation.add("..");
		punctuation.add("...");
		punctuation.add("....");
		punctuation.add(",");
		punctuation.add(";");
		punctuation.add("!");
		punctuation.add("?");
		punctuation.add("،");
		punctuation.add(".");
		//System.out.println(this.Word+"  "+this.Time);
		if (punctuation.contains(this.Word)) this.word_position="SEOS";
		else if ( this.Word.endsWith(".") ||this.Word.endsWith(",")||this.Word.endsWith(";") ||this.Word.endsWith("!")||this.Word.endsWith("?")||this.Word.endsWith("،")||this.Word.endsWith("؟")||this.Word.endsWith("،"))
		{
		this.Word=this.Word.replace(".","");
		this.Word=this.Word.replace(",","");
		this.Word=this.Word.replace(";","");
		this.Word=this.Word.replace("!","");
		this.Word=this.Word.replace("\\?","");
		this.Word=this.Word.replace("،","");
		this.Word=this.Word.replace("،","");
		this.Word=this.Word.replace("؟","");
		this.word_position="LW";
		//System.out.println(this.Word+"  "+this.Time);
		}
		
	
		else this.word_position=null;
		
	}
	
	
	
	
	
}

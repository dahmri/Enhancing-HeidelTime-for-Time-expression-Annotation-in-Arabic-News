import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Noeud {

	
	Noeud Tab_Noeud[]=new Noeud[43];
	int Niveau;
	Boolean fin=false;
	int nbrElement=0;
	int idf=-2;
	
	Noeud(){
		
	}
	
	
	Noeud(char A){
		
		
		this.Tab_Noeud[this.charToInt(A)]=new Noeud();
		
	}

	
	
	
	int charToInt(char A){
		 if (A=='$')return 30;// marque de chaine vide et le 30 est libre
		 
		 //mettre les Alif sous la meme longueur d'onde
		 if((A=='إ')||(A=='ٱ')||(A=='أ')||(A=='آ'))
			 A='ا';
		 
		 // mettre les ya sous la meme longureur d'onde
		 if((A=='ئ')||(A=='ى'))
			 A='ي';
		 
		 if((A-1569>0)&&(A-1569<42)) return (int) (A-1569); 
		 else return 0;//le 0 est utilisé pour les affixe comme espace
	}

	
	int AjoutMot(String A, Noeud N, int position){
		int Ok =0;
			
		if(position==A.length())
		{
			N.fin=true;
			//ajouter la 
		return Ok;
		}
		
		if(charToInt(A.charAt(position))!=0){
			if(position<A.length()){
				N.Niveau=position;
				if(N.Tab_Noeud[charToInt(A.charAt(position))]!=null){
					//System.out.println("ce noeud existe deja "+A.charAt(position));
				}else{
					//System.out.println("creation du noued num"+charToInt(A.charAt(position))+" de la lettre "+A.charAt(position));
					N.Tab_Noeud[charToInt(A.charAt(position))]=new Noeud();
					
				}
					AjoutMot(A,N.Tab_Noeud[N.charToInt(A.charAt(position))],position+1);
			}
			
			
		}
		else
			AjoutMot(A,N,position+1);
		
		
		
		
		
		
		return -1;
	}
	

	
	void RemplirStructure(String fichier,Noeud Racine){
		
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			ligne=br.readLine();
			
			while(ligne!=null){
			//System.out.println(" je fais monter ler mot "+ligne);
				Racine.AjoutMot(ligne,Racine, 0);
				this.nbrElement++;
				ligne=br.readLine();
				}
			
			
			br.close(); 
		    } 		
		
		catch (Exception e){
			System.out.println(e.toString());
		}
		
		
	}
	
	
	

	
	boolean RechercheExacte(String S, Noeud D,int position){


		
	boolean B=false;
	
	
	if((position==S.length())&&(D.fin==true)){
		return true;
	}
	
	
	if((position<S.length())&&(D.Tab_Noeud[charToInt(S.charAt(position))]!=null)){
		B=RechercheExacte(S,D.Tab_Noeud[charToInt(S.charAt(position))],position+1);
	}
	
	
	return B;
}
	
	
	
	
}

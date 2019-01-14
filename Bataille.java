import java.util.Random;

public class Bataille{


    // * * * * * * * * * * * * * * *
    // Fonctions données pour le TP
    
    public static Random rand = new Random () ;
    
    public static int randRange ( int a , int b ) {
	return rand . nextInt (b - a ) + a ;
    }

    public static String readString () {
	return System.console().readLine();
    }
    
    public static boolean isInt(String s){
	return s.matches("\\d+");
    }
    public static int readInt(){
	while (true){
	    String s=readString();
	    if(isInt(s)) return Integer.parseInt(s);
	}
    }

    public static void printGrid(int[][]tab){
	System.out.print("   A B C D E F G H I J");
	for(int i=0;i<tab.length;i++){
	    System.out.println();
	    for(int j=0;j<tab[i].length;j++){
		if(j==0 && i!=9)
		    System.out.print(i+1+"  ");
		if(j==0 && i==9)
		    System.out.print(i+1+" ");
		System.out.print(tab[i][j]+" ");
	    }
	}
	System.out.println("\n");
    }

    public static void printVsGrid(char[][]tab){
	System.out.print("   A B C D E F G H I J");
	for(int i=0;i<tab.length;i++){
	    System.out.println();
	    for(int j=0;j<tab[i].length;j++){
		if(j==0 && i!=9)
		    System.out.print(i+1+"  ");
		if(j==0 && i==9)
		    System.out.print(i+1+" ");
		System.out.print(tab[i][j]+" ");
	    }
	}
	System.out.println("\n");
    }

    // * * * * * * * * * * * * * * *
    // Création des grilles de jeu
    
    public static int[][] gridComp= new int [10][10];

    public static void gridComp(){
	for(int i=0;i<gridComp.length;i++)
	    for(int j=0;j<gridComp[i].length;j++)
		gridComp[i][j]=0;
    }

    public static int[][] gridPlay= new int [10][10];

    public static void gridPlay(){
	for(int i=0;i<gridPlay.length;i++)
	    for(int j=0;j<gridPlay[i].length;j++)
		gridPlay[i][j]=0;
    }

    // * * * * * * * * * * * * * * *
    // Teste la position d'un navire
    
    public static boolean posOk(int[][]grille, int l, int c, int d, int t){

	if(d==1){
	    for(int i=c;i<grille.length;i++){
		if(grille[l][i]!=0)
		    return false;
		t--;
	    }
	    if(t<=0)
		return true;
	    return false;
	}
	
	if(d==2){
	    for(int i=l;i<grille.length;i++){
		if(grille[i][c]!=0)
		    return false;
		t--;
	    }
	    if(t<=0)
		return true;
	    return false;
	}
	return false;
        	
    }

    // * * * * * * * * * * * * * * * * * * * * * * * * 
    // Place aléatoirement les navires de l'ordinateur
    
    public static void initGridComp (){

        gridComp();
	int t=5,l,c,d;
	int num=1;
	boolean bool;
	boolean go=false;
	while(t>1){
	    l=randRange(0,10);
	    c=randRange(0,10);
	    d=randRange(1,3);
	    bool=posOk(gridComp,l,c,d,t);
	    if(bool){
		if(d==1)
		    for(int j=c;j<c+t;j++)
			gridComp[l][j]=num;
	
		if(d==2)
		    for(int i=l;i<l+t;i++)
			gridComp[i][c]=num;
		
		if(t!=3 || go)
		    t--;
		else
		    go=true;
		num++;
	    }
	}
    }

    // * * * * * * * * * * * * * * * * * * * * * * * * * *
    // Place interactivement les navires de l'utilisateur
    
    public static void initGridPlay (){

	gridPlay();
	printGrid(gridPlay);
	
	int t=5,l,c,d;
	int num=1;
	String nomBateau="";
        boolean bool;
	boolean go=false;
	
	while(t>1){
	    
	    if(num==1)
		nomBateau="porte-avion";
	    if(num==2)
		nomBateau="croiseur";
	    if(num==3)
		nomBateau="contre-torpilleur";
	    if(num==4)
		nomBateau="sous-marin";
	    if(num==5)
		nomBateau="torpilleur";
		
	    System.out.println("Rentre une ligne pour le "+nomBateau+" (entre 1 et 10) :");
	    l=readInt()-1;	    
	    
	    System.out.println("Rentre une colonne pour le "+nomBateau+" (entre A et J) :");
	    // Conversion d un lettre String en int:
	    //   on stocke la String
	    //   on prend son premier (et seul) caractere avec charAt
	    //   on prend le code de ce caractere (un int)
	    //   on ramene le code dans la fourchette qui nous interesse pour notre ligne (0 a 9)
	    String colonne=readString();
	    char colonn=colonne.charAt(0);
	    c=(int)colonn-65;
	    
	    System.out.println("Tu veux le placer horizontal (1) ou vertical (2) ?");
	    d=readInt();

	    if(l>=0 && l<10 && c>=0 && c<10 && d>0 && d<3)
		// evite l outOfBound dans le posOk si le joueur rentre de mauvaises coordonnees
		bool=posOk(gridPlay,l,c,d,t);
	    else
		bool=false;

	    if(bool){
		if(d==1)
		    for(int j=c;j<c+t;j++)
			gridPlay[l][j]=num;
	
		if(d==2)
		    for(int i=l;i<l+t;i++)
			gridPlay[i][c]=num;

		System.out.println("Ton "+nomBateau+" est en place!"); 
		printGrid(gridPlay);
		
		num++;
		if(t!=3 || go)
		    t--;
		else
		    go=true;
	    }
	    else
		System.out.println("Je n'ai pas pu placer ton "+nomBateau+" aux coordonnees indiquees. Propose-moi d autres coordonnees");
	}
	
    }

    public static boolean hasDrowned(int[][] grid, int num){
	
	if(num<1 || num>5)
	    return false;

	for(int i=0;i<grid.length;i++)
	    for(int j=0;j<grid[i].length;j++)
		if(grid[i][j]==num)
		    return false;
	
	return true;

    }

    public static char oneMove(int[][] grid, int ligne, int colonne){
	
	int num=grid[ligne][colonne];
	if(grid[ligne][colonne]==0 || grid[ligne][colonne]==6){
	    System.out.println("A l eau.\n");
	    String pause=readString();
	    return '0';
	}
	else if(grid[ligne][colonne]<6){
	    grid[ligne][colonne]=6;
	    if(hasDrowned(grid,num)){
		System.out.println("Coulé !!\n");
		String pause=readString();
	    }
	    else{
		System.out.println("Touché !\n");
		String pause=readString();
	    }
	    if(grid==gridPlay){
		printGrid(gridPlay);
		String pause=readString();
	    }
	    return '6';
	}
	return 'a';
	
    }
    
    public static int[] playComp(){
	int[] res=new int[2];
	res[0]=randRange(0,10);
	res[1]=randRange(0,10);
	return res;
    }

    public static boolean isOver(int[][] grid){
	for(int i=0;i<grid.length;i++)
	    for(int j=0;j<grid[i].length;j++)
		if(grid[i][j]>0 && grid[i][j]<6)
		    return false;
	return true;

    }

    public static void play(){

	System.out.println("\nBienvenue dans le jeu de bataille navale !\n");
	initGridComp();
	initGridPlay();
	
	char[][] vsGrid=new char[10][10];
	for(int i=0;i<vsGrid.length;i++)
	    for(int j=0;j<vsGrid[i].length;j++)
		vsGrid[i][j]='?';
	
        boolean firstPlayer=false;
	
	System.out.println("Pile ou face?");
	String rep=readString();
	while(!rep.equals("pile") && !rep.equals("face")){
	    System.out.println("Réponds par 'pile' ou 'face'");
	    rep=readString();
	}
	int hasard=randRange(1,3);
	if(hasard==1){
	    if(rep.equals("pile")){

		System.out.println("\nTu commences. Choisis une ligne (1 à 10) et une colonne (A à J) pour tirer\n");
		// bug: je demandais de print vsGrid (tabb de char) avec
		// printGrid qui prend en charge des tabb d'int. J'ai fait une
		// copie adaptée de printGrid (printVsGrid).
		printVsGrid(vsGrid);
		System.out.print("ligne: ");
		int l=readInt()-1;
		System.out.print("colonne: ");
		int c=10;
		while(c<0 || c>9){
		    String colonne=readString();
		    char colonn=colonne.charAt(0);
		    c=(int)colonn-65;
		}
		vsGrid[l][c]=oneMove(gridComp,l,c);
		
	    }
	    else if(rep.equals("face")){

		System.out.println("\nJe commence!\n");
		int l=randRange(0,10);
		int c=randRange(0,10);
		// bug: je demandais de print deux éléments en les séparant
		// d'une virgule comme en python! Ca déclenchait un sacré
		// bordel, bien fait pour moi. J'ai remplacé les virgules par
		// des '+'.
		System.out.println("ligne "+l+" et colonne "+(char)(c+65));
		oneMove(gridPlay,l,c);
		firstPlayer=true;
		
	    }
	}
	else{
	    if(rep.equals("pile")){

		System.out.println("\nJe commence!\n");
		int l=randRange(0,10);
		int c=randRange(0,10);
		System.out.println("ligne "+l+" et colonne "+(char)(c+65));
		oneMove(gridPlay,l,c);
		firstPlayer=true;
		
	    }
	    else if(rep.equals("face")){

		System.out.println("\nTu commences. Choisis une ligne (1 à 10) et une colonne (A à J) pour tirer\n");
		printVsGrid(vsGrid);
	        System.out.print("ligne: ");
		int l=readInt()-1;
		System.out.print("colonne: ");
		int c=10;
		while(c<0 || c>9){
		    String colonne=readString();
		    char colonn=colonne.charAt(0);
		    c=(int)colonn-65;
		}
		vsGrid[l][c]=oneMove(gridComp,l,c);
		
	    }
	}
	
	boolean gameOver=false;

	if(!firstPlayer){
	    while(!gameOver){

		int l=randRange(0,10);
		int c=randRange(0,10);
		System.out.println("ligne "+l+" et colonne "+(char)(c+65));
		oneMove(gridPlay,l,c);
		gameOver=isOver(gridPlay);
		
		if(!gameOver){

		    System.out.println("A toi de tirer\n");
		    printVsGrid(vsGrid);
		    System.out.print("ligne: ");
		    l=readInt()-1;
		    System.out.print("colonne: ");
		    c=10;
		    while(c<0 || c>9){
			String colonne=readString();
			char colonn=colonne.charAt(0);
			c=(int)colonn-65;
		    }
		    vsGrid[l][c]=oneMove(gridComp,l,c);
		    gameOver=isOver(gridComp);
		    if(gameOver)
			System.out.println("Pfeuh! La chance du débutant. On en refait une?");
		    
		}
	    }
	}
	else{
	    while(!gameOver){

		System.out.println("A toi de tirer\n");
		printVsGrid(vsGrid);
		System.out.print("ligne: ");
		int l=readInt()-1;
		System.out.print("colonne: ");
		int c=10;
		while(c<0 || c>9){
		    String colonne=readString();
		    char colonn=colonne.charAt(0);
		    c=(int)colonn-65;
		}
		vsGrid[l][c]=oneMove(gridComp,l,c);
		gameOver=isOver(gridComp);
		
		if(!gameOver){

		    l=randRange(0,10);
		    c=randRange(0,10);
		    System.out.println("ligne "+l+" et colonne "+(char)(c+65));
		    oneMove(gridPlay,l,c);
		    gameOver=isOver(gridPlay);
		    
		}
	    }
	}
	
    }
    
    public static void main(String[] args){

	play();
	
    }
}

import java.sql.Connection;
//Creare un programma Java che si colleghi allo schema WORLD e che permetta le seguenti operazioni:
//1. Trovare tutte le città di un dato "countrycode"
//2. Data una nazione, trovare la città più popolosa
//3. Data una forma di governo tra quelle presenti nel database (suggerimento: select distinct....),
//indicare lo stato con estensione territoriale più grande.

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InterrogazioneDatabase {
	static Scanner scanner=new Scanner(System.in);
public static void main(String[] args) throws ClassNotFoundException, SQLException {
	Class.forName("com.mysql.cj.jdbc.Driver");
	String password ="95asroma";
	String username = "root";
	String url = "jdbc:mysql://localhost:3306/world?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false";
	Connection connessione = DriverManager.getConnection(url, username, password);
	while(true) {
	menu();
	int scelta=scanner.nextInt();
	scanner.nextLine();
	switch (scelta) {
	case 1:{
		trovaCittaDaCountrycode(connessione);
		break;}
case 2:{
	trovaCittaPiuPopolosa(connessione);

		break;}
case 3:{
	trovaNazionePiuGrande(connessione);

	break;}
	default:
		scanner.close();
		System.exit(0);
		break;
	}
		
	

		
			
	
	}
}

private static void trovaCittaDaCountrycode(Connection connessione) throws SQLException {
	PreparedStatement prepareStatement = connessione
			.prepareStatement("select * from city where CountryCode =  ? ;");
	System.out.println("Inseriscimi lo stato di interesse");
	prepareStatement.setString(1, scanner.nextLine());
	ResultSet risultatoQuery = prepareStatement.executeQuery();
	while (risultatoQuery.next()) {
		int id = risultatoQuery.getInt(1);
		String nome = risultatoQuery.getString("Name");
		System.out.println(id + " " + nome);
	}
}
private static void trovaCittaPiuPopolosa(Connection connessione) throws SQLException {
	PreparedStatement prepareStatement = connessione
			.prepareStatement("select *, Max(Population) from city where CountryCode =  ? ;");
	System.out.println("Inseriscimi lo stato di cui vuoi sapere la città più popolosa");
	prepareStatement.setString(1, scanner.nextLine());
	ResultSet risultatoQuery = prepareStatement.executeQuery();
	while (risultatoQuery.next()) {
		int id = risultatoQuery.getInt(1);
		String nome = risultatoQuery.getString("Name");
		int Pop= risultatoQuery.getInt(5);
		System.out.println(id + " " + nome+"-"+Pop);
	}
}
private static void trovaNazionePiuGrande(Connection connessione) throws SQLException {
	PreparedStatement prepareStatement = connessione
			.prepareStatement("select distinct GovernmentForm from country;");
	ResultSet executeQuery = prepareStatement.executeQuery();
	List<String> elencoFormeDiGoverno = new ArrayList<String>();
	int cnt = 0;
	System.out.println("##########################################");
	System.out.println("Ecco le forme di governo");
	while (executeQuery.next()) {
		String formaDiGoverno = executeQuery.getString(1);
		elencoFormeDiGoverno.add(formaDiGoverno);
		System.out.println(cnt + ". " + formaDiGoverno);
		cnt++;
	}
	System.out.println("Indica di quale forma di governo vuoi vedere lo stato più grande");
	int posizione = scanner.nextInt();
	scanner.nextLine();
	String stato = elencoFormeDiGoverno.get(posizione);
	PreparedStatement altroStatement = connessione.prepareStatement("select *, MAX(SurfaceArea)  kmq from country where GovernmentForm = ?");
	altroStatement.setString(1, stato);
	ResultSet executeQuery2 = altroStatement.executeQuery();
	
	while (executeQuery2.next()) {
		String formaDiGoverno = executeQuery2.getString("name");
		String superficie = executeQuery2.getString("kmq");
		System.out.println(superficie + " - " + formaDiGoverno);
	}
}
private static void menu() {

	System.out.println("Fai la tua scelta");
	System.out.println("1. stampa tutte le città di una nazione");
	System.out.println("2. stampa la città più popolosa di una nazione");
	System.out.println("3. stampa lo stato più grande data una forma di governo");
	System.out.println("0. ESCI");

}

}

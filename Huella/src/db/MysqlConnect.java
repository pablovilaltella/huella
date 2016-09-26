package db;

import com.mysql.jdbc.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Properties;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
/**
 * @desc A singleton database access class for MySQL
 */
public final class MysqlConnect {
    public Connection conn;
    private Statement statement;
    public static MysqlConnect db;
    private MysqlConnect() {
    	
    	// Traigo el config
    	Properties prop = new Properties();
    	InputStream input = null;

    	try {

    		String filename = "resources/config.properties";
    		input = MysqlConnect.class.getClassLoader().getResourceAsStream(filename);
    		if(input==null){
    	            System.out.println("Sorry, unable to find " + filename);
    		    return;
    		}

    		// load a properties file
    		prop.load(input);

    		// get the property value and print it out
    		/*System.out.println(prop.getProperty("server"));
    		System.out.println(prop.getProperty("driver"));
    		System.out.println(prop.getProperty("database"));
    		System.out.println(prop.getProperty("dbuser"));
    		System.out.println(prop.getProperty("dbpassword"));
    		System.out.println(prop.getProperty("options"));*/

    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				input.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}

    	
        String url= "jdbc:mysql://" + prop.getProperty("server") + ":3306/";
        String driver = prop.getProperty("driver");
        String dbName = prop.getProperty("database");
        String userName = prop.getProperty("dbuser");
        String password = prop.getProperty("dbpassword");
        String options = prop.getProperty("options");
        String connection = url+dbName+"?"+options;
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(connection,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    /**
     *
     * @return MysqlConnect Database connection object
     */
    public static synchronized MysqlConnect getDbCon() {
        if ( db == null ) {
            db = new MysqlConnect();
        }
        return db;
 
    }
    /**
     *
     * @param query String The query to be executed
     * @return a ResultSet object containing the results or null if not available
     * @throws SQLException
     */
    public ResultSet query(String query) throws SQLException{
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }
    /**
     * Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */
    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;
 
    }
    
    /**
     * Devuelve el usuario 
     * @param user String
     * @param pass String
     * @return ResultSet 
     */
    public ResultSet getUser(String user, String pass){
    	   	
    	String query ="Select usuario,clave from usuario where usuario = '" + user + "'" + "and clave = '" + pass + "'";    	
    	
    	ResultSet res = null;
		try {
			res = this.query(query);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return res;
    }
    
    /**
     * Devuelve el usuario 
     * @param user String
     * @param pass String
     * @return ResultSet 
     */
    public boolean validateUser(String user, String pass){
    	
    	boolean correcto = false;
    	//String query ="Select usuario,clave from usuario where usuario = '" + user + "'" + "and clave = '" + pass + "'";    	
    	String query ="Select usuario,clave from usuario where usuario = '" + user + "'";
    	
    	ResultSet res = null;
		try {
			res = this.query(query);
			if (res.next()){
				String storedPassword = res.getString("clave"); 
				correcto = validatePassword(pass, storedPassword);				
			}
		} catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
    	return correcto;
    }
    
    
    public void saveUser() throws NoSuchAlgorithmException, InvalidKeySpecException
    {
    	// TODO: Hay que hacer un crear usuario y pass con hash para los usuarios
    	
    	
    	/* HACER INSERT DIRECTAMENTE SI ES POR UNICA VEZ */
    	try {
    		// the mysql insert statement
    	      String query = " insert into usuario (usuario, clave)"
    	        + " values (?, ?)";

    	      String originalPassword = "clave4321"; // CLAVE ADMIN
    	      // create the mysql insert preparedstatement
    	      PreparedStatement preparedStmt = conn.prepareStatement(query);
    	      
    	      String generatedSecuredPasswordHash = generateStorngPasswordHash(originalPassword);
    	      System.out.println(generatedSecuredPasswordHash);

    	      preparedStmt.setString (1, "Admin");
    	      preparedStmt.setString (2, generatedSecuredPasswordHash);
    	      //res.updateString("clave", generatedSecuredPasswordHash);
    	      //res.updateRow();

    	      // execute the preparedstatement
    	      preparedStmt.execute();
    	      
    	      //conn.close();
    	    /*  Statement stmt = conn.createStatement(
    	        ResultSet.TYPE_SCROLL_SENSITIVE,
    	        ResultSet.CONCUR_UPDATABLE);
    	      ResultSet res = getUser("admin", "admin");
    	
    	
			if (res.next()){*/
			//}
		} catch (SQLException e) {

			e.printStackTrace();
		}
    	
    }
    
    private static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();
         
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }
     
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
     
    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
    
    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);
         
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();
         
        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    
    
    public boolean saveFinger(int idPersona, byte[] dedo, String indiceDedo){

    	try {
    		  // the mysql insert statement
    	      String query = " insert into huellas (id_persona, huella, nro_dedo)"
    	        + " values (?, ?, ?)";

    	      // create the mysql insert preparedstatement
    	      PreparedStatement preparedStmt = conn.prepareStatement(query);
    	          	      
    	      System.out.println();

    	      preparedStmt.setInt(1, idPersona);
    	      preparedStmt.setBytes (2, dedo);
    	      preparedStmt.setString(3, indiceDedo);

    	      // execute the preparedstatement
    	      preparedStmt.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		}
    	return true;
    }
    
   /**
    * Devuelvo todos los tipos de documentos
    * @return ResultSet 
    */
	public ResultSet getTipoDocumentos() {
		
		String query ="Select id_tipo_documento, descripcion, codigo from tipo_documento";    	
    	
    	ResultSet res = null;
		try {
			res = this.query(query);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return res;	
	}
	
	/**
	* Devuelvo verdadero si existe la persona
	* @return boolean 
	*/
	public boolean existePersona(String numeroDoc, String tipoDoc) {
		
		boolean existe = false;

		try {
			ResultSet idTipo = getIndiceTipoDocumento(tipoDoc);
			idTipo.next();
			String query ="Select id_persona id_tipo_documento from persona_documento where numero = '" + numeroDoc + "' and id_tipo_documento = '" + idTipo.getInt(1) + "'";    	
			
			ResultSet resultado = null;
			resultado = this.query(query);
			if (resultado.next()){
				existe = true;								
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return existe;		
	}
	
	/**
	 * Devuelve el idTipoDocuemnto
	 * @param String nombre
	 * @return ResultSet
	 */
	public ResultSet getIndiceTipoDocumento(String nombre){
		
		String query ="Select id_tipo_documento from tipo_documento where codigo = '" + nombre + "'";    	
    	
    	ResultSet resultado = null;
		try {
			resultado = this.query(query);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	* Guardo la persona
	* @return idPersona 
	*/
	public int guardarPersona(String apellido, String nombre, String profesion, String selectedItem, String nro, String calle, String nroDire, String dpto, String piso) {
		int result = 0;
		try {
			conn.setAutoCommit(false);
			// Inserto la persona
			String queryPersona = " insert into persona (nombre, apellido, profesion)"
	        + " values (?, ?, ?)";
			
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(queryPersona,Statement.RETURN_GENERATED_KEYS);

			preparedStmt.setString(1, nombre);
			preparedStmt.setString(2, apellido);
			preparedStmt.setString(3, profesion);
	
			// execute the preparedstatement
			preparedStmt.execute();
			
			ResultSet key = preparedStmt.getGeneratedKeys();

			key.next();
			System.out.println(key.getInt(1));
			System.out.println(selectedItem);
			ResultSet clave = getIndiceTipoDocumento(selectedItem);
			clave.next();
			
			// Inserto el DNI			
			String queryDoc = " insert into persona_documento (id_persona, id_tipo_documento, numero)"
			        + " values (?, ?, ?)";
					
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmtDoc = conn.prepareStatement(queryDoc);

			preparedStmtDoc.setInt(1, key.getInt(1));
			preparedStmtDoc.setInt(2, clave.getInt(1));
			preparedStmtDoc.setString(3, nro);
			
			// execute the preparedstatement
			preparedStmtDoc.execute();
			
			// Inserto la direccion
			String queryDireccion = " insert into direccion (id_persona, calle, numero, dpto, piso)"
			        + " values (?, ?, ?, ? ,?)";
					
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmtDireccion = conn.prepareStatement(queryDireccion);

			preparedStmtDireccion.setInt(1, key.getInt(1));
			preparedStmtDireccion.setString(2, calle);
			preparedStmtDireccion.setInt(3, Integer.parseInt(nroDire));
			preparedStmtDireccion.setString(4, dpto);
			preparedStmtDireccion.setString(5, piso);
			
			// execute the preparedstatement
			preparedStmtDireccion.execute();

			conn.commit();
			conn.setAutoCommit(true);
			result = key.getInt(1);

	} catch (SQLException e) {

		e.printStackTrace();
	}
		return result;
		
	}
	
	/**
	 * Guardar el movimiento
	 * @param String tipoMovimiento
	 * @param int idHuella
	 */
	public void guardarMovimiento(String tipoMovimiento, int idHuella){
		try {
			conn.setAutoCommit(false);
			// Inserto la persona
			String queryPersona = " insert into movimiento (id_huella, tipo, fecha)"
	        + " values (?, ?, ?)";
			
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(queryPersona,Statement.RETURN_GENERATED_KEYS);

			Date fechaEvento = (Date) new java.util.Date();
			
			preparedStmt.setInt(1, idHuella);
			preparedStmt.setString(2, tipoMovimiento);
			preparedStmt.setDate(3, fechaEvento);
	
			// execute the preparedstatement
			preparedStmt.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * Devuelvo todas las huellas
	 * @return ResultSet
	 */
	public ResultSet getAllFingers(){
		
		String query = "Select id_huella, id_persona, huella, nro_dedo from huellas where huella is not null;";    	
    	
    	ResultSet resultado = null;
		try {
			resultado = this.query(query);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return resultado;
		
	}
	
}
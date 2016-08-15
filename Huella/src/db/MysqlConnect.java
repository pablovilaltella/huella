package db;

import com.mysql.jdbc.Connection;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
/**
 * @desc A singleton database access class for MySQL
 * @author Ramindu
 */
public final class MysqlConnect {
    public Connection conn;
    private Statement statement;
    public static MysqlConnect db;
    private MysqlConnect() {
        String url= "jdbc:mysql://localhost:3306/";
        String dbName = "huella";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "huella";
        String password = "Hu3ll41403";
        String options = "autoReconnect=true&useSSL=true";
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
}
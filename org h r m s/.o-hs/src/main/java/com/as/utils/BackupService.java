//package com.as.utils;
//
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.nio.ByteBuffer;
//import java.nio.CharBuffer;
//import java.nio.charset.Charset;
//import java.nio.charset.CharsetEncoder;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.zip.Deflater;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
//public class BackupService {
//	
//    private static ResultSet res;
//    private static Connection con;
//    private Statement st;
//    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
//  
//    public void generate(String Database,String user,String Password) {
//
//        String backuppath = "D:/Alligate Systems/Auto_Backup/";;
//        BackupService b = new BackupService();
//        try {
//        	Files.createDirectories(Paths.get(backuppath));
//            StringBuffer data = b.getData("localhost", "3306", user, Password, Database);
//            //StringBuffer To Byte
//            Charset charset = StandardCharsets.UTF_8;
//            CharsetEncoder encoder = charset.newEncoder();
//            CharBuffer buffer = CharBuffer.wrap(data);
//            ByteBuffer bytes = encoder.encode(buffer);
//            // Zip
//            File filedst = new File(backuppath + "\\" + "OHIMS_DB_BK" +"("+ formatter.format(new Date()) +").zip");
//            FileOutputStream dest = new FileOutputStream(filedst);
//            ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(dest));
//            zip.setMethod(ZipOutputStream.DEFLATED);
//            zip.setLevel(Deflater.BEST_COMPRESSION);
//            zip.putNextEntry(new ZipEntry(Database+".sql"));
//            zip.write(bytes.array());
//            zip.close();
//            dest.close();
//            
//           System.out.println("Backup Completed: "+formatter.format(new Date()));
//        } catch (Exception ex) {
//        	System.out.println("Backup Error: "+formatter.format(new Date()));
//            ex.printStackTrace();
//        }
//        
//    }
//
//    public StringBuffer getData(String host, String port, String user, String password, String db) {
//        String Mysqlpath = getMysqlBinPath(user, password, db);
//        InputStream in = null;
//        BufferedReader br = null;
//        List<String> buffer = null;
//        try {
//            Process run = Runtime.getRuntime().exec(Mysqlpath + "mysqldump --host=" + host + " --port=" + port + " --user=" + user + " --password=" + password + " --compact --complete-insert --extended-insert " + "--skip-comments --skip-triggers " + db);        
//	        in = run.getInputStream();
//	        br = new BufferedReader(new InputStreamReader(in));
//            buffer = new ArrayList<String>();
//	       String line = null;
//	       while ((line = br.readLine()) != null) {
//	    	   buffer.add(line);
//	       }
//        } catch (Exception ex) {
//        }finally {
//        	try {
//        		br.close();
//                in.close();
//			} catch (Exception e) {
//			}
//		}
//        return ListToString.Bind(buffer);
//    }
//
//    public String getMysqlBinPath(String user, String password, String db) {
//    	String a = "";
//    	try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, user, password);
//            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            res = st.executeQuery("select @@basedir");
//            while (res.next()) {
//                a = res.getString(1);
//            }
//            a = a + "bin\\";
//        	System.out.println("Backup Bin Path: "+a);
//        } catch (Exception e) {
//        }finally {
//        	try {
//        		con.close();
//        		st.close();
//        		res.close();
//			} catch (Exception e) {
//			}
//		}
//        return a;
//    }
//    
//}

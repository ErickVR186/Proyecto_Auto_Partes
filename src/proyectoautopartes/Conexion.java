package proyectoautopartes;

import java.sql.*;
import javax.swing.JOptionPane;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import java.io.*;
import java.net.URL;

/**
 *
 * @author Locos
 */
public class Conexion {
    
    public static String[] leerxml(){
        String [] variables =new String [4];
        try{
        URL url = new URL("file:conexion.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream())); 
        String entrada, cadena="";
        while((entrada=br.readLine())!=null){
            cadena = cadena + entrada;
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db =dbf.newDocumentBuilder();
        InputSource archivo = new InputSource();
        archivo.setCharacterStream(new StringReader(cadena));
        
        Document documento = db.parse(archivo);
        documento.getDocumentElement().normalize();
        
        NodeList nodoLista = documento.getElementsByTagName("Elemento");
        for(int s=0; s<nodoLista.getLength();s++){
            Node primerNodo = nodoLista.item(s);
            if(primerNodo.getNodeType()==Node.ELEMENT_NODE){
                Element primerElemento =(Element) primerNodo;
                NodeList primerNombreElementoLista = primerElemento.getElementsByTagName("VALOR");
                Element primerNombreElemento = (Element) primerNombreElementoLista.item(0);
                NodeList primerNombre =primerNombreElemento.getChildNodes();
                variables[s]=((Node) primerNombre.item(0)).getNodeValue();
            }
            
        }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No se pudo leer el XML"); 
        }
        return variables;
    }

    Connection con;
    int valor;
    public Connection conectar(int valor1){
        this.valor= valor1;
        
        String servi, puerto, name, pass; 
        String[] retorna = new String[4];
        retorna =leerxml();
        servi=retorna[0];
        name=retorna[1];
        pass=retorna[2];
        puerto=retorna[3];
        
        try {
             con=DriverManager.getConnection("jdbc:mysql://"+ servi +":" + puerto +"/autos_refracciones"+"?&user="+ name +"&password="+ pass);   
            if(valor==0){
                JOptionPane.showMessageDialog(null, "Conexión valida");
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Conexión no valida" + ex);
        }
        
       return con; 
         
    }
    
}

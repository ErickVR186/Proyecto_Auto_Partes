/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautopartes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Locos
 */
public class OlvidaContra extends javax.swing.JFrame implements ActionListener{
    Conexion con = new Conexion();
    Connection conexion = con.conectar(1);
    JLabel usuario, contra, tipo, leyenda;
    JTextField txt1, txt2;
    JButton Cambiar, Borrar, IniciarS;
    
    public OlvidaContra(){
        setLayout(null);
        usuario = new JLabel(); usuario.setText("Usuario: ");
        txt1 = new JTextField(); 
        contra = new JLabel(); contra.setText("Nueva contraseña: ");
        txt2 = new JTextField();  
        leyenda = new JLabel(); 
        leyenda.setText("<html><center> Debe tener entre 8 a 15 caracteres, una letra mayuscula y minuscula, <p> al menos un numero y otro caracter especial.</html>"); 
                       
        Cambiar = new JButton();  Cambiar.setText("Cambiar");
        Borrar = new JButton();  Borrar.setText("Borrar");
        IniciarS = new JButton();  IniciarS.setText("Iniciar Sesión");
        
        usuario.setBounds(10,20,150, 30);
        txt1.setBounds(150, 20, 220, 30);
        contra.setBounds(10,70,180,30);
        txt2.setBounds(150, 70, 220, 30);
        leyenda.setBounds(10,110,700,30);
        Cambiar.setBounds(80, 160, 90, 30);
        Borrar.setBounds(230, 160, 90, 30);
        IniciarS.setBounds(20, 220,120,30);
                
        add(usuario); add(txt1);  add(contra); add(txt2);   
        add(Cambiar); add(Borrar);  add(IniciarS); add(leyenda);
        
        Cambiar.addActionListener(this);
        Borrar.addActionListener(this);
        IniciarS.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==IniciarS){
            this.dispose();
            ProyectoAutoPartes ventana1 = new ProyectoAutoPartes(1);
            ventana1.setTitle("Proyecto");
            ventana1.setSize(430,290);
            ventana1.setLocationRelativeTo(null);
            ventana1.setVisible(true);
            ventana1.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        if(e.getSource()==Cambiar){
            char letras;
            boolean numero = false;
            boolean mayus = false;
            boolean minus = false;
            boolean caracteresp = false;
            boolean cantidad = false;
            String cant="";
            String may ="";
            String min ="";
            String num ="";
            String caract ="";
            
            String textoIntroducido=(txt2.getText());
            if(textoIntroducido.length()>=8){
                cantidad = true;
            }
                for(int i=0; i<textoIntroducido.length(); i++){
                    letras = textoIntroducido.charAt(i);
                    if(Character.isDigit(letras)){
                        numero = true;
                    }
                    else if(Character.isUpperCase(letras)){
                        mayus = true;
                        
                    }
                    else if(Character.isLowerCase(letras)){
                        minus = true;
                        
                    }
                    else if((letras>=58 && letras<=64)||(letras>=32 && letras<=47)||(letras>=91 && letras<=96)){
                        caracteresp = true;
                    }
                }
                if(cantidad==false){
                cant="entre 8 a 15 caracteres, ";
                }
                if(numero==false){
                    num = "al menos un numero, ";
                }
                if(mayus==false){
                    may="una letra mayuscula, ";
                }
                if(minus==false){
                    min ="una letra minuscula ";
                }
                if(caracteresp==false){
                    caract = "y otro caracter especial.";
                }
                leyenda.setText("<html><center>Debe tener "+cant+may+min+num+caract+"<p></html>");
                if((cantidad&&numero&&mayus&& minus&&caracteresp)==true){
                    leyenda.setText("Contraseña segura");
                    String contraseña = ObtenerHASHMD5(textoIntroducido);
                    try{

                    String query="UPDATE usuarios SET CONTRASENIA=? , MD5=? WHERE NOMBRE=?";

                    PreparedStatement guardar=conexion.prepareStatement(query);

                    guardar.setString(1,txt2.getText());

                    guardar.setString(2,contraseña);

                    guardar.setString(3,txt1.getText());

                    guardar.executeUpdate();

                    JOptionPane.showMessageDialog(null,"La contraseña de "+txt1.getText()+ " ha sido cambiada !!");

                    limpiarProducto();

                    txt1.requestFocus();

                    }catch(Exception ex){

                        System.out.println("Error"+ ex);

                    }
                }
        }
    }
    private static String ObtenerHASHMD5(String textoEntrada) {
        if (textoEntrada.equals("")) {
            return "";
        } else {
            try {
                MessageDigest HashMD5 = MessageDigest.getInstance("MD5");
                byte[] mensajeMatriz = HashMD5.digest(textoEntrada.getBytes());
                BigInteger numero = new BigInteger(1, mensajeMatriz);
                StringBuilder hashMD5Salida = new StringBuilder(numero.toString(16));
 
                while (hashMD5Salida.length() < 32) {
                    hashMD5Salida.insert(0, "0");
                }
                return hashMD5Salida.toString();
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Error al obtener el hash: " + e.getMessage());
                return "";
            }
        }
    }
    public void limpiarProducto(){

        txt1.setText("");

        txt2.setText("");

    }
    

}

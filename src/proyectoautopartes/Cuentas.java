/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautopartes;

import java.awt.event.*;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

/**
 *
 * @author Locos
 */
public class Cuentas extends JFrame implements ActionListener{
    Conexion con = new Conexion();
    Connection conexion = con.conectar(1);
    JLabel usuario, contra, tipo, leyenda;
    JTextField txt1, txt2, txt3;
    JButton Registrar, Borrar, IniciarS;
    JComboBox desplegable;
    
    
    public Cuentas(){
        setLayout(null);
        String[] opciones = {"Vendedor", "Administrador"};
        usuario = new JLabel(); usuario.setText("Nombre de usuario: ");
        txt1 = new JTextField(); 
        contra = new JLabel(); contra.setText("Contraseña: ");
        txt2 = new JTextField();  
        tipo = new JLabel(); tipo.setText("Tipo de cuenta: ");
        leyenda = new JLabel(); 
        leyenda.setText("<html><center><p style=\"width:300px\">Debe tener entre 8 a 15 caracteres, una letra mayuscula y minuscula, al menos un numero y otro caracter especial.</p></html>"); 
        
        
        desplegable = new JComboBox<String>(opciones);   
                
        Registrar = new JButton();  Registrar.setText("Registrar");
        Borrar = new JButton();  Borrar.setText("Borrar");
        IniciarS = new JButton();  IniciarS.setText("Iniciar Sesión");
        
        usuario.setBounds(10,20,150, 30);
        txt1.setBounds(150, 20, 220, 30);
        contra.setBounds(10,70,180,30);
        txt2.setBounds(150, 70, 220, 30);
        leyenda.setBounds(10,110,700,30);
        tipo.setBounds(10,155,150,30);
        desplegable.setBounds(150, 155, 220, 30);
        Registrar.setBounds(80, 205, 90, 30);
        Borrar.setBounds(230, 205, 90, 30);
        IniciarS.setBounds(10, 290,120,30);
        
        
        add(usuario); add(txt1);  add(contra); add(txt2);add(tipo); add(desplegable);  
        add(Registrar); add(Borrar);  add(IniciarS); add(leyenda);
       
        Registrar.addActionListener(this);
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
        if(e.getSource()==Registrar){
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
                leyenda.setText("<html><center><p style=\"width:300px\">Debe tener "+cant+may+min+num+caract+"</p></html>");
                if((cantidad&&numero&&mayus&& minus&&caracteresp)==true){
                    String VerCuenta=""; 
                    String nameusu = txt1.getText();
                    int compru=0;     
                    try{
                        Statement leer= conexion.createStatement();
                        ResultSet resultado=leer.executeQuery("SELECT NOMBRE FROM usuarios ");
                        while(resultado.next()){
                            VerCuenta=resultado.getString(1);
                            if(nameusu.equals(VerCuenta)){
                                compru = 1;
                            }  
                        }
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Error: "+ ex);
                    } 
                    if(compru==0){
                        leyenda.setText("<html><center>Contraseña seguro</html>");
                        String contraseña = ObtenerHASHMD5(textoIntroducido);
                        String inf = desplegable.getSelectedItem().toString();

                        try{

                        String query="Insert INTO usuarios (NOMBRE,CONTRASENIA,MD5,CUENTA) values(?,?,?,?)";

                        PreparedStatement guardar=conexion.prepareStatement(query);

                        guardar.setString(1,txt1.getText());

                        guardar.setString(2,txt2.getText());

                        guardar.setString(3,contraseña);

                        guardar.setString(4,inf);

                        guardar.executeUpdate();

                        JOptionPane.showMessageDialog(null,"El usuario "+txt1.getText()+ " ha sido registrado en el sistema!!");

                        limpiarProducto();

                        txt1.requestFocus();

                        }catch(Exception ex){

                        System.out.println("Error"+ ex);

                        }
                    }   
                    else if(compru==1){
                        limpiarProducto();
                        JOptionPane.showMessageDialog(null,"El usuario "+txt1.getText()+ " ya ha sido registrado anteriormente!!");
                        leyenda.setText("<html><center><p style=\"width:300px\">Debe tener entre 8 a 15 caracteres, una letra mayuscula y minuscula, al menos un numero y otro caracter especial.</p></html>");
                    }
                }
        }   
    }
    
    public void limpiarProducto(){

        txt1.setText("");

        txt2.setText("");

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
}

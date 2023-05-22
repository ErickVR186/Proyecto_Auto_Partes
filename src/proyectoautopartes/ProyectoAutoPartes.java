package proyectoautopartes;

import java.awt.Color;
import java.awt.EventQueue;
import java.math.BigInteger;
import java.security.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ProyectoAutoPartes extends javax.swing.JFrame implements ActionListener{
    
    int valor=0;
    JLabel lbl1, lbl2, lbl3;
    JTextField txt1, txt2;
    JButton ingresar, borrar, newcuenta, olvcontra;
         
    public ProyectoAutoPartes(int valor1){
        //int valor=valor1;
        this.valor=valor1;
        
        /*if(valor==0){
            Conexion con = new Conexion();
            Connection conexion = con.conectar(0);
        }*/
        setLayout(null);
        lbl1 = new JLabel(); lbl1.setText("Ingresa el usuario: ");  
        txt1 = new JTextField(); 
        lbl2 = new JLabel(); lbl2.setText("Ingresa una contraseña: ");
        txt2 = new JPasswordField();    
        lbl3 = new JLabel(); lbl3.setText("¿No tienes una cuenta? ");
        
        borrar = new JButton();  borrar.setText("Borrar");
        ingresar = new JButton();  ingresar.setText("Ingresar");
        olvcontra = new JButton();  olvcontra.setText("¿Olvidaste tu contraseña? ");
        newcuenta = new JButton();  newcuenta.setText("Registrate");
        
        lbl1.setBounds(10,20,150,30);
        txt1.setBounds(160, 20, 235, 30);
        lbl2.setBounds(10,70,150,30);
        txt2.setBounds(160, 70, 235, 30);
        lbl3.setBounds(100, 115, 150, 30);
        newcuenta.setBounds(235, 115, 95, 30);
        newcuenta.setOpaque(false);
        newcuenta.setContentAreaFilled(false);
        newcuenta.setBorderPainted(false);
        newcuenta.setForeground(Color.BLUE);
        olvcontra.setBounds(100, 135, 200, 30);
        olvcontra.setOpaque(false);
        olvcontra.setContentAreaFilled(false);
        olvcontra.setBorderPainted(false);
        olvcontra.setForeground(Color.BLUE);
        
        ingresar.setBounds(40, 180, 150, 30);
        borrar.setBounds(230, 180, 150, 30);
               
        add(lbl1); add(txt1);  add(lbl2); add(txt2); add(lbl3); add(newcuenta);
        add(olvcontra); add(ingresar);  add(borrar);
        ingresar.addActionListener(this);
        borrar.addActionListener(this);
        newcuenta.addActionListener(this);
        olvcontra.addActionListener(this);
        
    }
        
        
        
    public void actionPerformed(ActionEvent e){
        Conexion con = new Conexion();
        Connection conexion = con.conectar(this.valor);
        System.out.println(this.valor);
        String usu1="";
        String md5="";
        String tipo="";
        if(e.getSource()==ingresar){
            try{
                Statement leer= conexion.createStatement();
                ResultSet resultado=leer.executeQuery("SELECT * FROM usuarios where NOMBRE='"+txt1.getText()+"'");
                while(resultado.next()){
                    usu1=(resultado.getString(1));
                    md5=(resultado.getString(3));
                    tipo=(resultado.getString(4));
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Error: "+ ex);
            }
            System.out.println(usu1+md5+tipo);
            String textoIntroducido=(txt2.getText());
            if((txt1.getText().equals(usu1))&&(ObtenerHASHMD5(textoIntroducido).equals(md5))){
                if(tipo.equals("Administrador")){
                    this.dispose();
                    Modificar ventana2 = new Modificar();
                    ventana2.setTitle("Modificar");
                    ventana2.setSize(430,390);
                    ventana2.setLocationRelativeTo(null);
                    ventana2.setVisible(true);
                    ventana2.setDefaultCloseOperation(EXIT_ON_CLOSE);     
                }
                else if(tipo.equals("Vendedor")){
                    this.dispose();
                    Altas ventana4 = new Altas();
                    ventana4.setTitle("Altas");
                    ventana4.setSize(430,390);
                    ventana4.setLocationRelativeTo(null);
                    ventana4.setVisible(true);
                    ventana4.setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"El usuario o contraseña es incorrecto.");
            }
        }
        else if(e.getSource()==borrar){
            txt1.setText("");
            txt2.setText("");      
        }
        else if(e.getSource()==newcuenta){
            this.dispose();
            Cuentas ventana6 = new Cuentas();
            ventana6.setTitle("Registrate");
            ventana6.setSize(430,390);
            ventana6.setLocationRelativeTo(null);
            ventana6.setVisible(true);
            ventana6.setDefaultCloseOperation(EXIT_ON_CLOSE);  
        }
        else if(e.getSource()==olvcontra){
            this.dispose();
            OlvidaContra ventana8 = new OlvidaContra();
            ventana8.setTitle("¿Olvidaste tu contraseña?");
            ventana8.setSize(430,310);
            ventana8.setLocationRelativeTo(null);
            ventana8.setVisible(true);
            ventana8.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
    
    public void cone(){
        
    }
    
    public static void main(String[] args) {
        ProyectoAutoPartes ventana1 = new ProyectoAutoPartes(0);
        ventana1.setTitle("Proyecto");
        ventana1.setSize(430,290);
        ventana1.setLocationRelativeTo(null);
        ventana1.setVisible(true);
        ventana1.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
}

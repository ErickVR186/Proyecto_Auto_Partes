package proyectoautopartes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.sql.Statement;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Locos
 */
public class Modificar extends javax.swing.JFrame implements ActionListener{
    Conexion con = new Conexion();
    Connection conexion = con.conectar(1);
    
    JLabel id, nombre,precio;
    JTextField txt1, txt2,txt3;
    JButton modificar, cancelar, buscar, modificar1, borrar;
    public Modificar(){
        setLayout(null);
        
        id = new JLabel(); id.setText("ID: ");txt1 = new JTextField(); 
        txt1.setEditable(false);
        nombre = new JLabel(); nombre.setText("Nombre: ");
        txt2 = new JTextField();    
        precio = new JLabel(); precio.setText("Precio: ");
        txt3 = new JTextField();
        
        modificar = new JButton();  modificar.setText("Modificar");
        cancelar = new JButton();  cancelar.setText("Cancelar");
        buscar = new JButton();  buscar.setText("Buscar");
        modificar1 = new JButton();  modificar1.setText("MODIFICA");
        borrar = new JButton();  borrar.setText("BORRA");
        
        id.setBounds(10,20,150,30);
        txt1.setBounds(80, 20, 320, 30);
        nombre.setBounds(10,70,150,30);
        txt2.setBounds(80, 70, 200, 30);
        precio.setBounds(10,120,150,30);
        txt3.setBounds(80, 120, 320, 30);
        buscar.setBounds(300, 70, 100, 30);
        modificar.setBounds(80, 190, 100, 30);
        cancelar.setBounds(230, 190, 100, 30);
        
        borrar.setBounds(290, 290, 90, 30);
        
        add(id); add(txt1);  add(nombre); add(txt2); add(precio); add(txt3); 
        add(modificar); add(cancelar); add(buscar);  add(borrar);
        modificar.addActionListener(this);
        cancelar.addActionListener(this);
        buscar.addActionListener(this);
        
        borrar.addActionListener(this);
    }
    public void limpiarProducto(){
        txt1.setText("");
        txt2.setText("");
        txt3.setText("");
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==borrar){
            this.dispose();
            Borrar ventana3 = new Borrar();
            ventana3.setTitle("Borrar");
            ventana3.setSize(430,390);
            ventana3.setLocationRelativeTo(null);
            ventana3.setVisible(true);
            ventana3.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        if(e.getSource()==buscar){
            
            try{

            Statement leer= conexion.createStatement();

            ResultSet resultado=leer.executeQuery("SELECT * FROM productos where nombre='"+txt2.getText()+"'");

            while(resultado.next()){

            txt1.setText(resultado.getString(1));

            txt3.setText(resultado.getString(4));

            }

        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error: "+ ex);
        }

        }
        if(e.getSource()==modificar){
           
        try{

            String query="UPDATE productos SET Nombre=? , Precio_producto=? WHERE Id_productos=?";

            PreparedStatement guardar=conexion.prepareStatement(query);

            guardar.setString(1,txt2.getText());

            guardar.setDouble(2,Double.parseDouble(txt3.getText()));

            guardar.setInt(3,Integer.parseInt(txt1.getText()));

            guardar.executeUpdate();

            JOptionPane.showMessageDialog(null,"Producto "+txt2.getText()+ " Modificado !!");

            limpiarProducto();

            txt2.requestFocus();

            }catch(Exception ex){

            System.out.println("Error"+ ex);

            }
        
        }
        if(e.getSource()==cancelar){
            
        limpiarProducto();
        }
    }
}

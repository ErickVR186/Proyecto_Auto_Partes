package proyectoautopartes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import java.sql.Statement;
import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.Connection;

/**
 *
 * @author Locos
 */
public class Borrar extends javax.swing.JFrame implements ActionListener{
    Conexion con = new Conexion();
    Connection conexion = con.conectar(1);
    
    JLabel id, nombre,precio;
    JTextField txt1, txt2,txt3;
    JButton borrar, cancelar, buscar, modificar1;
    
    public Borrar(){
        setLayout(null);
        id = new JLabel(); id.setText("ID: ");txt1 = new JTextField(); 
        nombre = new JLabel(); nombre.setText("Nombre: ");
        txt2 = new JTextField();    
        precio = new JLabel(); precio.setText("Precio: ");
        txt3 = new JTextField();
        
        borrar = new JButton();  borrar.setText("Borrar");
        cancelar = new JButton();  cancelar.setText("Cancelar");
        buscar = new JButton();  buscar.setText("Buscar");
        modificar1 = new JButton();  modificar1.setText("MODIFICA");
        
        
        id.setBounds(10,20,150,30);
        txt1.setBounds(80, 20, 320, 30);
        nombre.setBounds(10,70,150,30);
        txt2.setBounds(80, 70, 200, 30);
        precio.setBounds(10,120,150,30);
        txt3.setBounds(80, 120, 320, 30);
        buscar.setBounds(300, 70, 100, 30);
        borrar.setBounds(80, 190, 100, 30);
        cancelar.setBounds(230, 190, 100, 30);
        modificar1.setBounds(35, 290, 90, 30);
        
        
        add(id); add(txt1);  add(nombre); add(txt2); add(precio); add(txt3); 
        add(borrar); add(cancelar); add(buscar); add(modificar1); 
        borrar.addActionListener(this);
        cancelar.addActionListener(this);
        buscar.addActionListener(this);
        modificar1.addActionListener(this);
        
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==modificar1){
            this.dispose();
            Modificar ventana2 = new Modificar();
            ventana2.setTitle("Modificar");
            ventana2.setSize(430,390);
            ventana2.setLocationRelativeTo(null);
            ventana2.setVisible(true);
            ventana2.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        if(e.getSource()==buscar){
            try{
                Statement leer= conexion.createStatement();
                ResultSet resultado=leer.executeQuery("SELECT * FROM productos where Nombre='"+txt2.getText()+"'");

                if (!resultado.next()){
                txt2.setText("No se encontró el registro");
                }
                else {
                    do {
                        txt1.setText(resultado.getString(1));
                        txt3.setText(resultado.getString(3));
                    }while (resultado.next());
                }                        
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Error: "+ ex);
            }
        }
        if(e.getSource()==borrar){
            int opcion = -1;
            opcion = JOptionPane.showConfirmDialog(null, "¿Realmente desea borrar el producto?", "Confirmar borrado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (opcion == 0) {
                try {
                    String query = "DELETE from productos WHERE Id_productos=?";
                    PreparedStatement guardar = conexion.prepareStatement(query);
                    guardar.setInt(1, Integer.parseInt(txt1.getText()));
                    guardar.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Producto " + txt2.getText() + " borrado !!");
                    txt1.setText("");
                    txt2.setText("");
                    txt3.setText("");

                } catch (Exception ex) {
                    System.out.println("Error" + ex);
                }
}

        }  
        if(e.getSource()==cancelar){
            txt1.setText("");
            txt2.setText("");
            txt3.setText("");
        }
    }
}
    


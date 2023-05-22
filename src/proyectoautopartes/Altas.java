package proyectoautopartes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Locos
 */
public class Altas extends javax.swing.JFrame implements ActionListener{
    
    Conexion con = new Conexion();
    Connection conexion = con.conectar(1);
    JLabel nombrep, precio, id;
    JTextField txt1, txt2, txt3;
    JButton guardar, cancelar, consulta;
    public Altas(){
        setLayout(null);
        nombrep = new JLabel(); nombrep.setText("Nombre del producto: ");
        txt1 = new JTextField(); 
        precio = new JLabel(); precio.setText("Precio: ");
        txt2 = new JTextField();  
        id = new JLabel(); id.setText("ID: ");
        txt3 = new JTextField();    
                
        guardar = new JButton();  guardar.setText("Guardar");
        cancelar = new JButton();  cancelar.setText("Cancelar");
        consulta = new JButton();  consulta.setText("CONSULTA");
        
        id.setBounds(10,20,150,30);
        txt3.setBounds(80, 20, 300, 30);
        nombrep.setBounds(10,70,180,30);
        txt1.setBounds(160, 70, 220, 30);
        precio.setBounds(10,120,150,30);
        txt2.setBounds(80, 120, 300, 30);
        guardar.setBounds(80, 190, 100, 30);
        cancelar.setBounds(230, 190, 100, 30);
        consulta.setBounds(270, 290, 120, 30);
        
        add(nombrep); add(txt1);  add(precio); add(txt2);add(id); add(txt3);  
        add(guardar); add(cancelar);  add(consulta);
        guardar.addActionListener(this);
        cancelar.addActionListener(this);
        
        consulta.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==consulta){
            this.dispose();
            Consulta ventana5 = new Consulta();
            ventana5.setTitle("Consulta");
            ventana5.setSize(430,390);
            ventana5.setLocationRelativeTo(null);
            ventana5.setVisible(true);
            ventana5.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        else if(e.getSource()==guardar){
            try{

            String query="Insert INTO productos (Id_productos,Nombre,Precio_producto) values(?,?,?)";

            PreparedStatement guardar=conexion.prepareStatement(query);

            guardar.setInt(1,Integer.parseInt(txt3.getText()));
            
            guardar.setString(2,txt1.getText());

            guardar.setDouble(3,Double.parseDouble(txt2.getText()));
            
            guardar.executeUpdate();

            JOptionPane.showMessageDialog(null,"Producto "+txt1.getText()+ " Agregado !!");

            limpiarProducto();

            txt1.requestFocus();

            }catch(Exception ex){

            System.out.println("Error"+ ex);

            }
        }
        else if(e.getSource()==cancelar){
            limpiarProducto();
        }
    
}
    public void limpiarProducto(){

        txt1.setText("");

        txt2.setText("");

    }
}

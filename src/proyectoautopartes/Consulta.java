package proyectoautopartes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Locos
 */
public class Consulta extends javax.swing.JFrame implements ActionListener{
    
    Conexion con = new Conexion();
    Connection conexion = con.conectar(1);
    JButton buscar, altas;
    JTable jTable1;
    
    public Consulta(){
        setLayout(null);
        jTable1 = new JTable();               
        buscar = new JButton();  buscar.setText("Buscar");
        altas = new JButton();  altas.setText("ALTAS");
        
        buscar.setBounds(10,150,90,30);
        jTable1.setBounds(120,10,280,270);
        altas.setBounds(290, 290, 90, 30);
                
        add(buscar); add(altas);  add(jTable1);
        buscar.addActionListener(this);
        altas.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==altas){
            this.dispose();
            Altas ventana4 = new Altas();
            ventana4.setTitle("Altas");
            ventana4.setSize(430,390);
            ventana4.setLocationRelativeTo(null);
            ventana4.setVisible(true);
            ventana4.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        else if(e.getSource()==buscar){
            
        String[] nombreColumnas={"Id", "Producto", "Precio"};
        DefaultTableModel tabla=new DefaultTableModel(null,nombreColumnas );        
        jTable1.setModel(tabla);
        
        String[] datos=new String[3];
        
        try{
            Statement leer= conexion.createStatement();
            ResultSet resultado=leer.executeQuery("SELECT * FROM productos");
            
            while(resultado.next()){
                datos[0]=resultado.getString(1);
                datos[1]=resultado.getString(2);
                datos[2]=resultado.getString(4);
                tabla.addRow(datos);
            }
            jTable1.setModel(tabla);                  
            
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error: "+ ex);
}

        }
}
}

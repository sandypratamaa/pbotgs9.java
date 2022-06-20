package pboo;

import java.awt.EventQueue;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;




public class jsper {

	private JFrame frmFormDataBarang;
	private JTextField txtKd;
	private JTextField txtNm;	
	private JTextField txtStok;
	private JTextField txtStokN;
	private JComboBox boxSat;
	JButton tblSimpan = new JButton("Simpan");
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1/transaksi";
	static final String USER = "root";
	static final String PASS = "";
	
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	private DefaultTableModel model;
	private JScrollPane scrollpane;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormBarang window = new FormBarang();
					window.frmFormDataBarang.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FormBarang() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFormDataBarang = new JFrame();
		frmFormDataBarang.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				showData();
			}
		});
		frmFormDataBarang.setTitle("Form Data Barang");
		frmFormDataBarang.setBounds(100, 100, 518, 593);
		frmFormDataBarang.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFormDataBarang.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Data Barang");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(35, 21, 118, 41);
		frmFormDataBarang.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Kode Barang");
		lblNewLabel_1.setBounds(35, 73, 84, 14);
		frmFormDataBarang.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nama Barang");
		lblNewLabel_2.setBounds(35, 98, 84, 14);
		frmFormDataBarang.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Satuan");
		lblNewLabel_3.setBounds(35, 123, 46, 14);
		frmFormDataBarang.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Stok");
		lblNewLabel_4.setBounds(35, 148, 46, 14);
		frmFormDataBarang.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Stok Normal");
		lblNewLabel_5.setBounds(35, 173, 84, 14);
		frmFormDataBarang.getContentPane().add(lblNewLabel_5);
		
		txtKd = new JTextField();
		txtKd.setBounds(129, 73, 86, 20);
		frmFormDataBarang.getContentPane().add(txtKd);
		txtKd.setColumns(10);
		
		txtNm = new JTextField();
		txtNm.setBounds(129, 95, 86, 20);
		frmFormDataBarang.getContentPane().add(txtNm);
		txtNm.setColumns(10);
		
		boxSat = new JComboBox();
		boxSat.setModel(new DefaultComboBoxModel(new String[] {"Buah", "Batang", "Lembar", "Ekor"}));
		boxSat.setBounds(129, 119, 86, 22);
		frmFormDataBarang.getContentPane().add(boxSat);
		
		txtStok = new JTextField();
		txtStok.setBounds(129, 145, 86, 20);
		frmFormDataBarang.getContentPane().add(txtStok);
		txtStok.setColumns(10);
		
		txtStokN = new JTextField();
		txtStokN.setBounds(129, 170, 86, 20);
		frmFormDataBarang.getContentPane().add(txtStokN);
		txtStokN.setColumns(10);
		

		tblSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kode = txtKd.getText();
				String nama = txtNm.getText();
				Object satuan = boxSat.getSelectedItem();
				int stok = Integer.parseInt(txtStok.getText());
				int stokmin = Integer.parseInt(txtStokN.getText());
				
				insert(kode, nama, satuan, stok, stokmin);
			}
		});
		tblSimpan.setBounds(35, 198, 89, 23);
		frmFormDataBarang.getContentPane().add(tblSimpan);
		
		JButton tblReset = new JButton("Reset");
		tblReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetForm();
			}
		});
		tblReset.setBounds(327, 198, 89, 23);
		frmFormDataBarang.getContentPane().add(tblReset);
		
		JButton tblEdit = new JButton("Edit");
		tblEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editData();
			}
		});
		tblEdit.setBounds(129, 198, 89, 23);
		frmFormDataBarang.getContentPane().add(tblEdit);
		
		JButton tblHapus = new JButton("Hapus");
		tblHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Yakin dek mau hapus data?");
				if(response == 0)
				{
					if(table_1.getSelectedRow() >= 0 )
					{
						hapusData(txtKd.getText());
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Hapus data telah dibatalkan");
				}
			}
		});
		tblHapus.setBounds(228, 198, 89, 23);
		frmFormDataBarang.getContentPane().add(tblHapus);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 232, 449, 299);
		frmFormDataBarang.getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String kode = table_1.getValueAt(table_1.getSelectedRow(),0).toString();
				getData(kode);
				tblSimpan.setEnabled(false);
			}
		});
		scrollPane.setViewportView(table_1);
	}
	
	public void insert(String kode, String nama, Object satuan, int stok, int stokmin)
	{
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String sql = "INSERT INTO barang (kd_brg,nm_brg,satuan,stok_brg,stok_min) VALUES (?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
		
			ps.setString(1, kode);
			ps.setString(2, nama);
			ps.setObject(3, satuan);
			ps.setInt(4, stok);
			ps.setInt(5, stokmin);
			
			ps.execute();
			
			stmt.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		showData();
	}
	
	public void showData()
	{
		model = new DefaultTableModel();
		
		model.addColumn("Kode Barang");
		model.addColumn("Nama Barang");
		model.addColumn("Satuan");
		model.addColumn("Stok Barang");
		model.addColumn("Stok Minmal Barang");
		
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			rs	= stmt.executeQuery("SELECT * FROM barang");
			while(rs.next())
			{
				model.addRow(new Object[] {
						rs.getString("kd_brg"),
						rs.getString("nm_brg"),
						rs.getString("satuan"),
						rs.getString("stok_brg"),
						rs.getString("stok_min"),
				});
			}
			
			stmt.close();
			conn.close();
			
			table_1.setModel(model);
			table_1.setAutoResizeMode(0);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void editData()
	{
		String kode = txtKd.getText();
		String nama = txtNm.getText();
		Object satuan = boxSat.getSelectedItem();
		int stok = Integer.parseInt(txtStok.getText());
		int stokmin = Integer.parseInt(txtStokN.getText());
		
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			PreparedStatement ps = conn.prepareStatement("UPDATE barang SET nm_brg=?,satuan=?,stok_brg=?,stok_min=? WHERE kd_brg=?");
			ps.setString(1, nama);
			ps.setObject(2, satuan);
			ps.setInt(3, stok);
			ps.setInt(4, stokmin);
			ps.setString(5, kode);
			
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Data telah diupdate");
			stmt.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		showData();
		resetForm();
	}
	
	public void hapusData(String kode)
	{
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			PreparedStatement ps = conn.prepareStatement("DELETE FROM barang WHERE kd_brg=?");
			ps.setString(1, kode);
					
			ps.execute();
			
			stmt.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		showData();
		resetForm();
	}
	
	
	public void getData(String kode)
	{
		JComboBox boxSat = new JComboBox();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM barang WHERE kd_brg=?");
			ps.setString(1, kode);
					
			rs = ps.executeQuery();
			
			rs.next();
			
			txtKd.setText(rs.getString("kd_brg"));
			txtNm.setText(rs.getString("nm_brg"));
			boxSat.setSelectedItem(rs.getString("satuan"));
			txtStok.setText(rs.getString("stok_brg"));
			txtStokN.setText(rs.getString("stok_min"));
			
			stmt.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void resetForm()
	{
		txtKd.setText("");
		txtNm.setText("");
		txtStok.setText("");
		txtStokN.setText("");
		tblSimpan.setEnabled(true);
	}
	private JasperPrint jprint;
	
	public void actionPerformed(ActionEvent arg0) {
		try {
			class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("JDBC:MYSQL://localhost:3306/stdinfo", "root", "");
			
			String sql = "select * from details";
			
			JasperDesign jdesain = JRXmlLoader.Load("G:\\Javaprograms\\Search\\src\\Search\\Blank_A4.jrxml");
			
			JRDesignQuery updateQuery = new JRDesignQuery();
			
			updateQuery.setText(sql);
			
			jdesign.setQuery(updateQuery);
			
			JasperReport Jreport = JasperCompileManager.compileReport(jdesign);
			JasperPrint JasperPrint = JasperFillManager.fillReport(Jreport, null, conn);
			
			JasperViewer.viewReport(JasperPrint, false);
			
			
	}catch(Exception e2) {
		JOptionPane.showMessageDialog(null, e2);
	}
	}
}
	




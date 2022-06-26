package pos;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class POS_pos extends JPanel implements ActionListener {

	private JButton buttonAdd, btnDB, btnPay, btnCancel;
	private JLabel lblItem, lblStock, lblTotal;
	private JTextField txtStock, txtTotal;
	private JTable tableModel;
	private JComboBox comboModel;
	DefaultComboBoxModel<String> dcm;
	DefaultTableModel model;
	int total;

	public POS_pos() {

	
		setLayout(null);
	
		btnDB = new JButton("제품 불러오기");
		btnDB.setBounds(20, 20, 140, 40);
	
		add(btnDB);
	
		btnDB.addActionListener(this);

		lblItem = new JLabel("상품");
		lblItem.setBounds(20, 90, 100, 30);
		add(lblItem);

		DefaultTableModel model = new DefaultTableModel() {
			public boolean inCellEditable(int i, int c) {
				return false;
			}
		};
		model.addColumn("이름");
		model.addColumn("수량");
		model.addColumn("가격");
		model.addColumn("총가격");

		tableModel = new JTable(model);
		JScrollPane jscroll = new JScrollPane(tableModel);
		jscroll.setBounds(300, 20, 210, 200);
		add(jscroll);

		comboModel = new JComboBox();
		comboModel.setBounds(70, 90, 200, 30);
		add(comboModel);

		lblItem = new JLabel("수량");
		lblItem.setBounds(20, 140, 100, 30);
		add(lblItem);

		txtStock = new JTextField();
		txtStock.setBounds(70, 140, 200, 30);
		add(txtStock);

		lblTotal = new JLabel("총가격");
		lblTotal.setBounds(20, 250, 100, 40);
		add(lblTotal);

		txtTotal = new JTextField();
		txtTotal.setBounds(70, 250, 200, 40);
		add(txtTotal);
		txtTotal.setEditable(false);

		buttonAdd = new JButton("추가");
		buttonAdd.setBounds(170, 190, 100, 40);
		add(buttonAdd);
		buttonAdd.addActionListener(this);

		btnPay = new JButton("결제");
		btnPay.setBounds(300, 250, 100, 40);
		add(btnPay);
		btnPay.addActionListener(this);

		btnCancel = new JButton("취소");
		btnCancel.setBounds(410, 250, 100, 40);
		add(btnCancel);
		btnCancel.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String gun = e.getActionCommand();

		String name, stock, price = "";

		if (gun == "제품 불러오기") {
		
			comboModel.removeAllItems();
		
			dcm = (DefaultComboBoxModel<String>) comboModel.getModel();
			try {
				Vector<Item> itemlist = ItemDAO.getInstance().getAllItem();
				for (Item item : itemlist) {
					String item_name = item.getItem_name();
					Vector<String> in = new Vector<String>();
					in.add(item_name);
					dcm.addElement(in.get(0));
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Vector<String> vdb = new Vector<String>();
		
		} 
		else if (gun == "추가") {
			model = (DefaultTableModel) tableModel.getModel();
		
			String totalprice;
			name = comboModel.getSelectedItem().toString();
			stock = txtStock.getText();
		
			price = ItemDAO.getInstance().getprice(name);
		
			totalprice = String.valueOf(Integer.parseInt(stock) * Integer.parseInt(price));
			total += Integer.parseInt(totalprice);
			txtTotal.setText(total + "");
			
			Vector<String> in1 = new Vector<String>();
			in1.add(name);
			in1.add(stock);
			in1.add(price);
			in1.add(totalprice);
			model.addRow(in1);
			

		} 
		else if (gun == "결제") {
			
			int gun2 = JOptionPane.showConfirmDialog(null, "결제하시겠습니까?");
			if (gun2 == 0) {
				int gun3 = Integer.parseInt(JOptionPane.showInputDialog(null, "총금액은" + total + "입니다"));
				if (gun3 >= total) {
					JOptionPane.showMessageDialog(null, "지불하신 금액은" + gun3 + "이고\n" + "상품의 합계는" + total + "이며,\n"
							+ "거스름돈은" + (gun3 - total) + "입니다");
					stockUpdate(model);
				} else {
					JOptionPane.showMessageDialog(null, "금액이 적습니다");
				}
				clean();
			}
		

		} 
		else {
			int gun2 = JOptionPane.showConfirmDialog(null, "주문을 취소하시겠습니까");
			if (gun2 == 0) {
				clean();
			}
		
		}
	}

	
	public void clean() {
		int rows = model.getRowCount();
		for (int i = rows - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		total = 0;
		txtStock.setText("");
		txtTotal.setText("0원");
	}

	
	public void stockUpdate(DefaultTableModel model) {
		int inputMoney;

		String product_stock[] = new String[model.getRowCount()];

		

		int count = model.getRowCount();

		for (int i = 0; i < count; i++) {
			try {
				product_stock[i] = ItemDAO.getInstance().getStock(model.getValueAt(i, 0).toString());
				ItemDAO.getInstance().updateStock(product_stock[i], model.getValueAt(i, 1).toString(),model.getValueAt(i, 0).toString());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
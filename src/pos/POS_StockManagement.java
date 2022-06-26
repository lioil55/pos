package pos;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class POS_StockManagement extends JPanel implements ActionListener{
	private JLabel labelName;
	private JTable jtableStock;
	private JButton buttonDB;
	private JButton buttonRegister;
	private JButton buttonUpdate;
	private JButton buttonDelete;
	
	public POS_StockManagement() {
		setLayout(null);

		labelName = new JLabel("�����Ȳ");
		labelName.setSize(100, 40);
		labelName.setLocation(60, 20);

		DefaultTableModel model = new DefaultTableModel() {
			public boolean inCellEditable(int i,int c) {
				return false;
			}
		};
		model.addColumn("id");
		model.addColumn("��ǰ��");
		model.addColumn("���");
		model.addColumn("��ǰ����");

		jtableStock = new JTable(model);
		JScrollPane jscroll = new JScrollPane(jtableStock);
		jscroll.setBounds(200, 20, 300, 280);

		// ��ǰ ���� ��ħ
		buttonDB = new JButton("��ǰ ���� ��ħ");
		buttonDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					id_init();
					loadDB(model);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonDB.setBounds(10, 70, 150, 40);

		// ��ǰ ���
		buttonRegister = new JButton("���");
		buttonRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = buttonRegister.getText();
				StockWindow window = new StockWindow(text);
			}
		});
		buttonRegister.setBounds(10, 130, 150, 40);

		// ��ǰ ����
		buttonUpdate = new JButton("����");
		buttonUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = jtableStock.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "DB�� �ҷ����� �ʾҰų� ���� �������� �ʾҽ��ϴ�." , ""
							+ "���!", JOptionPane.WARNING_MESSAGE);
				}else {
					String text = buttonUpdate.getText();
					
					String id = (String) jtableStock.getValueAt(row, 0);
					String name = (String) jtableStock.getValueAt(row, 1);
					String stock = (String) jtableStock.getValueAt(row, 2);
					String price = (String) jtableStock.getValueAt(row, 3);
					
					Item item = new Item();
					item.setId(Integer.parseInt(id));
					item.setItem_name(name);
					item.setItem_stock(Integer.parseInt(stock));
					item.setItem_price(Integer.parseInt(price));
					
					StockWindow window = new StockWindow(text, item);
					
				}
			}
		});
		buttonUpdate.setBounds(10, 190, 150, 40);

		// ��ǰ ����
		buttonDelete = new JButton("����");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = jtableStock.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "DB�� �ҷ����� �ʾҰų� ���� �������� �ʾҽ��ϴ�." , ""
							+ "���!", JOptionPane.WARNING_MESSAGE);
				}else {
					String text = buttonDelete.getText();
					
					String id = (String) jtableStock.getValueAt(row, 0);
					String name = (String) jtableStock.getValueAt(row, 1);
					String stock = (String) jtableStock.getValueAt(row, 2);
					String price = (String) jtableStock.getValueAt(row, 3);
					
					Item item = new Item();
					item.setId(Integer.parseInt(id));
					item.setItem_name(name);
					item.setItem_stock(Integer.parseInt(stock));
					item.setItem_price(Integer.parseInt(price));
					
					StockWindow window = new StockWindow(text, item);
					
				}
			}
		});
		buttonDelete.setBounds(10, 250, 150, 40);

		add(labelName);
		add(jscroll);
		add(buttonDB);
		add(buttonRegister);
		add(buttonUpdate);
		add(buttonDelete);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		//		Object obj = e.getSource();
		//		DefaultTableModel model = (DefaultTableModel) jtableStock.getModel();
	}

	// auto_increment ����
	public void id_init() throws SQLException {
		ItemDAO dao = ItemDAO.getInstance();
		int n = dao.getAllItem().size();
		dao.alteritem(n + 1);
	}

	// ���ΰ�ħ
	private void loadDB(DefaultTableModel model) throws SQLException{
		Vector<Item> itemlist = ItemDAO.getInstance().getAllItem();
		int rows = model.getRowCount();

		for (int i = rows-1; i >= 0; i--) {
			model.removeRow(i);
		}

		for (Item item : itemlist) {
			//			System.out.println(model.getRowCount());
			String item_id = String.valueOf(item.getId());
			String item_name = item.getItem_name();
			String item_stock = String.valueOf(item.getItem_stock());
			String item_price = String.valueOf(item.getItem_price());

			Vector<String> in = new Vector<String>();
			in.add(item_id);
			in.add(item_name);
			in.add(item_stock);
			in.add(item_price);
			model.addRow(in);
		}
	}
}
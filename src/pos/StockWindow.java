package pos;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StockWindow extends JFrame implements ActionListener{

	JLabel labelId = new JLabel("ID");
	JLabel labelName = new JLabel("��ǰ��");
	JLabel labelStock = new JLabel("���");
	JLabel labelPrice = new JLabel("����");
	
	JTextField textFieldId = new JTextField(10);
	JTextField textFieldName = new JTextField(10);
	JTextField textFieldStock = new JTextField(10);
	JTextField textFieldPrice = new JTextField(10);
	
	JButton buttonAccept = new JButton("");
	Item item;
	String text;
	
	public StockWindow(String text) {
		this.text = text;
		display();
		setSize(300, 300);
		setVisible(true);
	}
	
	public StockWindow(String text, Item item) {
		this.text = text;
		this.item = item;
		display();
		setSize(300, 300);
		setVisible(true);
	}
	
	public void display() {
		Container c = getContentPane();
		JPanel p = new JPanel(new GridLayout(4, 2));
		buttonAccept.setText(text);
		textFieldId.setEditable(false);
		
		if (item != null) {
			textFieldId.setText(String.valueOf(item.getId()));
			textFieldName.setText(item.getItem_name());
			textFieldStock.setText(String.valueOf(item.getItem_stock()));
			textFieldPrice.setText(String.valueOf(item.getItem_price()));
		}
		
		p.add(labelId);
		p.add(textFieldId);
		
		p.add(labelName);
		p.add(textFieldName);
		
		p.add(labelStock);
		p.add(textFieldStock);
		
		p.add(labelPrice);
		p.add(textFieldPrice);
		
		c.add(p, BorderLayout.CENTER);
		c.add(buttonAccept, BorderLayout.SOUTH);
		
		buttonAccept.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String state = buttonAccept.getText();
		String id;
		String name;
		String stock;
		String price;
		boolean result = false;
		
		switch(state) {
		case "���" :
			name = textFieldName.getText();
			stock = textFieldStock.getText();
			price = textFieldPrice.getText();
			
			Item register = new Item();
			register.setItem_name(name);
			register.setItem_stock(Integer.parseInt(stock));
			register.setItem_price(Integer.parseInt(price));
			
			result = ItemDAO.getInstance().insertItem(register);
			dispose();
			break;
		case "����" :
			id = textFieldId.getText();
			name = textFieldName.getText();
			stock = textFieldStock.getText();
			price = textFieldPrice.getText();
			
			item.setId(Integer.parseInt(id));
			item.setItem_name(name);
			item.setItem_stock(Integer.parseInt(stock));
			item.setItem_price(Integer.parseInt(price));
			
			result = ItemDAO.getInstance().updateitem(item);
			
			if (result) {
				JOptionPane.showMessageDialog(null, "������ �����Ͽ����ϴ�.", "����!", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}else {
				JOptionPane.showMessageDialog(null, "������ �����Ͽ����ϴ�.", "���!", JOptionPane.WARNING_MESSAGE);
			}
			break;
			
		case "����" :
			id = textFieldId.getText();
			name = textFieldName.getText();
			stock = textFieldStock.getText();
			price = textFieldPrice.getText();

			item.setId(Integer.parseInt(id));
			item.setItem_name(name);
			item.setItem_stock(Integer.parseInt(stock));
			item.setItem_price(Integer.parseInt(price));
			
			int res;
			
			res = JOptionPane.showConfirmDialog(null, "������ ��ǰ��" + name + "�� �����ͺ��̽����� �����Ͻðڽ��ϱ�?");
					
			if (res == 0) {
				ItemDAO.getInstance().deleteItem(Integer.parseInt(id));
				dispose();
				JOptionPane.showMessageDialog(null, "�����Ϸ��� " + name + "�� �����߽��ϴ�.");
			} else {
				JOptionPane.showMessageDialog(null, "������ ����߽��ϴ�.");
			}
			break;
			
			default :
				throw new IllegalStateException("Unexpected Value : " + state);
		}
	}
	
}


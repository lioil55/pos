package pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ItemDAO {

	private ItemDAO() {

	}

	private static ItemDAO instance = new ItemDAO();

	public static ItemDAO getInstance() {
		return instance;
	}

	// 1. ��ü ���ڵ� �˻� ��� (getAllItem())

	public Vector<Item> getAllItem() throws SQLException{
		Vector<Item> list = new Vector<Item>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from item";

		try {
			conn = DB_connect.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setItem_name(rs.getString("item_name"));
				item.setItem_stock(rs.getInt("item_stock"));
				item.setItem_price(rs.getInt("item_price"));
				list.add(item);
			}

		} catch(Exception e) {
			System.out.println("DB ���� �Ǵ� SQL ����!");
		} finally {
			rs.close();
			pstmt.close();
			DB_connect.close();
		}
		return list;
	}

	// 2. ItemDAO(item_name ����Ʈ ����)

	public Vector<String> getItem() throws SQLException{
		Vector<Item> dbitemlist = getAllItem();
		Vector<String> itemlist = new Vector<String>();
		for (Item item : dbitemlist) {
			itemlist.add(item.getItem_name());
		}
		return itemlist;
	}


	// 3. ItemDAO(Ư�� ���ڵ� item_price �˻�)

	public String getprice (String item_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String price = null;
		String sql = "select item_price from item where item_name=?";

		try {
			conn = DB_connect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				price = Integer.toString(rs.getInt("item_price"));
			}
		} catch (SQLException e) {
			System.out.println("Error");
		} finally {
			DB_connect.close();
		}

		return price;
	}

	// 4. ItemDAO(Ư�� ���ڵ� item_stock �˻�)

	public String getStock(String item_name) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String stock = null;
		String sql = "select item_stock from item where item_name = ?";

		try {
			conn = DB_connect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				stock = Integer.toString(rs.getInt("item_stock"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			conn.close();
			DB_connect.close();
		}
		return stock;
	}

	// 5. ItemDAO(Ư�� ���ڵ� item_stock ����)

	public void updateStock (String total, String stock, String name) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update item set item_stock = ?-? where item_name = ?";

		try {
			conn = DB_connect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, total);
			pstmt.setString(2, stock);
			pstmt.setString(3, name);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error");
		} finally {
			conn.close();
		}
	}

	// 6. ItemDAO(���ڵ� �߰�)

	public boolean insertItem(Item item) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		String sql = "insert into item(item_name, item_stock, item_price) values(?, ?, ?)";

		try {
			conn = DB_connect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getItem_name());
			pstmt.setInt(2, item.getItem_stock());
			pstmt.setInt(3, item.getItem_price());
			int r = pstmt.executeUpdate();
			System.out.println("return result = " + r);

			if (r > 0) result = true;
		} catch (Exception e) {
			System.out.println("Error");
		} finally {
			DB_connect.close();
		}

		return result;
	}

	// 7. ItemDAO(���ڵ� ����)

	public boolean updateitem(Item item) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update item set item_name=?, item_stock=?, item_price=? where (id=?)";
		boolean result = false;

		try {
			conn = DB_connect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getItem_name());
			pstmt.setInt(2, item.getItem_stock());
			pstmt.setInt(3, item.getItem_price());
			pstmt.setInt(4, item.getId());
			int r = pstmt.executeUpdate();

			if (r > 0) result = true;
		} catch (Exception e) {
			System.out.println("Error");
		} finally {
			DB_connect.close();
		}

		return result;
	}

	// 8. ItemDAO(Ư�� ���ڵ� ����)

	public boolean deleteItem(int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from item where (id=?)";
		boolean result = false;

		try {
			conn = DB_connect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int r = pstmt.executeUpdate();

			if (r > 0) result = true;
		} catch (Exception e) {
			System.out.println("Error");
		} finally {
			DB_connect.close();
		}

		return result;
	}
	
	// auto_increment ����
	public boolean alteritem(int n) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "alter table item auto_increment = ?";
		boolean result = false;
		
		try {
			conn = DB_connect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, n);
			int r = pstmt.executeUpdate();
			
			if (r > 0) result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB_connect.close();
			pstmt.close();
			conn.close();
		}
		
		
		return result;
	}
}

   
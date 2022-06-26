package pos;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class test {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ItemDAO dao = ItemDAO.getInstance(); 

		Vector<Item> list = new Vector<Item>();
		
		/*
		
		
		
		try {
			list = dao.getAllItem();
			Iterator it = list.iterator();
			
			System.out.println("*** 전체 상품 목록 출력 ***");
			while(it.hasNext()) {
				Item item  = (Item)it.next();
				System.out.println(item.getId()+", "+item.getItem_name()+", "+item.getItem_price()+", "+item.getItem_stock());

			}
			System.out.println("*** 상품명 출력 ***");
			Vector<String> itemlist = new Vector<String>();
			
			itemlist = dao.getItem();
			
			// 순회하면서 vector에 각 요소를 출력
			it = itemlist.iterator();
			
			while(it.hasNext()) {
				String item  = (String)it.next();
				System.out.println(item);

			}

			
			System.out.println("*** 상품 가격 출력 ***");
			Scanner sc = new Scanner(System.in);
			System.out.println("상품명 입력: ");
			String input = sc.next();
			String price = dao.getPrice(input);
			System.out.println("상품 가격: " + price);
			

					
			System.out.println("*** 재고 검색 *** ");
			Scanner sc1 = new Scanner(System.in);
			System.out.println("stock :");
			String input1 = sc.next();
			String stock =dao.getStock(input1);
			System.out.println("상품가격 :" + stock + "개");
			
			
			System.out.println("*** 상품재고량 update *** ");
			String total = dao.getStock("aaa");
			System.out.println("판매 수량 :");
			String count = sc.next();
			dao.updateStock(total, count, "aaa");
			list = dao.getAllItem();
			it = list.iterator(); //it를통해 데이터를 반복,순회가능
			
			System.out.println("*** 전체 상품 목록 출력 *** ");
			while(it.hasNext()){//값이 존재하는동안
				
				Item item = (Item)it.next(); //Item으로 형변환  값출력
				System.out.println(item.getId()+","+item.getItem_name()+","+item.getItem_stock()+","+item.getItem_price());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		
		Scanner sc = new Scanner(System.in);
		
		
		Item itemNew = new Item();
		System.out.println("제품명: ");
		String name = sc.next();
		System.out.println("입고량: ");
		int  stock = sc.nextInt();
		System.out.println("단가: ");
		int price = sc.nextInt();
		itemNew.setItem_name(name);
		itemNew.setItem_stock(stock);
		itemNew.setItem_price(price);
		boolean result = dao.insertItem(itemNew);
		
		
		list = dao.getAllItem();
		Iterator it = list.iterator();
		
		System.out.println("*** 전체 상품 목록 출력 ***");
		while(it.hasNext()) {
			Item item  = (Item)it.next();
			System.out.println(item.getId()+", "+item.getItem_name()+", "+item.getItem_price()+", "+item.getItem_stock());
	}

}
}

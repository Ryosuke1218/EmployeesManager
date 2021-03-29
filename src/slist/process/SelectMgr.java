package slist.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.ResultSet;

import intro07.BaseDB;
import slist.BaseMenu;
import slist.ui.Menu;

public class SelectMgr extends BaseMenu{
	public void showMenu() {
		//1の場合の処理を書く
		System.out.println("検索条件を指定してください :");
		int inputNo = this.processMenu("selectmenu.txt", 1, 4);
		
		switch(inputNo) {
		case 1:
			//2つのIDを指定して範囲検索をする
			this.processEmployeeID();
			break;
		case 2:
			//名前で検索(first_name)で検索する
			this.processFirstName();
			
			break;
		case 3:
			//雇用日付で検索する
			this.processHireDate();
			break;
		case 4:
			//戻る
			Menu menu = new Menu();
			menu.showMenu();
			break;
		default :
			break;
		}
		
	}
	
	/**
	 * 雇用日付を2つ入力させて、範囲検索をするメソッド
	 */
	public void processHireDate() {
		InputStreamReader isr = new InputStreamReader(System.in);
		System.out.println("日付の遅い方を入力してください(-や/は使用可です)");
		BufferedReader br = new BufferedReader(isr);
		
		try {
			//1つ目の入力値を受け取る
			String hire1 = br.readLine();
			boolean isOK = true;
			//1つ目の入力値のチェック
			String date1 = this.Modified(hire1);
			isOK = this.checkDigit(date1);
			while(!isOK) {
				System.out.println("遅い方の日付は数字で入力してください");
				hire1 = br.readLine();
				date1 = this.Modified(hire1);
				isOK = this.checkDigit(date1);
			}
			System.out.println("日付の早い方を入力してください(-や/は使用可です)");
			//2つ目の入力値を受け取る
			String hire2 = br.readLine();
			//2つ目の入力値のチェック
			String date2 = Modified(hire2);
			isOK = this.checkDigit(date2);
			while(!isOK) {
				System.out.println("早い方の日付は数字で入力してください");
				hire2 = br.readLine();
				date2 = this.Modified(hire2);
				isOK = this.checkDigit(date2);
			}
			
			//これで日付は20100101のような並びになる
			//次にどちらが日付の早い方か、遅い方かを判断する
			
			//文字列を数値に変換する
			int val1 = Integer.valueOf(date1).intValue();
			int val2 = Integer.valueOf(date2).intValue();
			
			//大きい方を取得
			int maxVal = Math.max(val1,  val2);
			//小さい方を取得
			int minVal = Math.min(val1,  val2);
			//SQL文を用意する
			
			String sql = null;
			
			if(maxVal == minVal) {
				sql = "SELECT * FROM employees WHERE hire_date = '"+maxVal+"'";
			}else {
				sql = "SELECT * FROM employees WHERE hire_date >= "+ minVal  + "  AND hire_date <= "+ maxVal;
			}
			
			BaseDB db = new BaseDB();
			//実行する
			ResultSet set = db.selectSQL(sql);
			//結果を表示させる
			this.showEmployees(set);
			
			//前の画面に戻る
			this.showMenu();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * -又は/を入れたままの状態では、文字確認が出来ないので削除するためのメソッド
	 */
	public String Modified(String hire) {
		String date = hire;
		if(hire.contains("-")) {
			date = hire.replace("-", "");
		}else if(hire.contains("/")) {
			date = hire.replace("/", "");
		}
		return date;
	}
	

	/**
	 * first_nameで検索する。LIKE演算子を使ってSQLで問い合わせを行う
	 */
	public void processFirstName() {
		InputStreamReader isr = new InputStreamReader(System.in);
		System.out.println("検索したい名前を入力してください");
		BufferedReader br = new BufferedReader(isr);
		try {
			//入力値を受け取る
			String name = br.readLine();
			String sql = "SELECT * FROM employees WHERE first_name LIKE '%"+name+"%'";
			BaseDB db = new BaseDB();
			ResultSet set = db.selectSQL(sql);
			this.showEmployees(set);
			this.showMenu();
		}catch(Exception e) {
		e.printStackTrace();
		}
	}

	/**
	 * 社員番号を2つ入力させて、範囲検索をするメソッド
	 */
	
	public void processEmployeeID() {
		//2回入力させる
		InputStreamReader isr = new InputStreamReader(System.in);
		System.out.println("小さい方のIDを入力してください :");
		BufferedReader br1 = new BufferedReader(isr);
		
		try {
			String str_id1 = br1.readLine();
			boolean isOK = true;
			//1つ目の入力値チェック
			isOK = this.checkDigit(str_id1);
			while(!isOK) {
				System.out.println("小さい方のIDは数字で入力してください :");
				str_id1 = br1.readLine();
				isOK = this.checkDigit(str_id1);
			}
			
			System.out.println("大きい方のIDを入力してください :");
			String str_id2 = br1.readLine();
			isOK = this.checkDigit(str_id2);
			while(!isOK) {
				System.out.println("大きい方のIDは数字で入力してください :");
				str_id2 = br1.readLine();
				isOK = this.checkDigit(str_id2);

			}
			//この時点で、正しく数値が入力された状態になる
			//どちらが大きいかを判断する
			
			//文字列を数値に変換する
			int val1 = Integer.valueOf(str_id1).intValue();
			int val2 = Integer.valueOf(str_id2).intValue();
			
			//大きい方を取得
			int maxVal = Math.max(val1, val2);
			//小さい方を取得
			int minVal = Math.min(val1,  val2);
			//SQL分を用意
			String sql = null;
			
			if(maxVal == minVal) {
				sql = "SELECT * FROM employees WHERE employee_id = "+maxVal;
			}else {
				sql = "SELECT * FROM employees WHERE employee_id >="+ minVal + " AND employee_id <="+ maxVal;
			}
			
			BaseDB db = new BaseDB();
			ResultSet set = db.selectSQL(sql);
			//結果を表示させる
			this.showEmployees(set);
			
			//前の画面に戻る
			this.showMenu();
			
			
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	/**
	 * ResultSet(SQLの実行結果)の情報を出力するメソッド
	 * @param set
	 */
	
	public void showEmployees(ResultSet set ) {
		try {
			int cnt = 0;
			//1行戻る
			
			//1件以上あった場合の処理
			System.out.println("  社員ID  |  名前  |  苗字  |  e-mail  |  雇用日  |  給料  |");
			
			while(set.next()) {
				int employee_id = set.getInt("employee_id");
				String first_name = set.getString("first_name");
				String last_name = set.getString("last_name");
				String email = set.getString("email");
				Date hire_date = set.getDate("hire_date");
				int salary = set.getInt("salary");
				System.out.println("|   "+employee_id+"   |   "+first_name+"   |  "+last_name
						+"  |  "+email+"  |  "+hire_date+"  |  "+salary+"  |");
				cnt++;

			}
			if(cnt == 0) {
				System.out.println("誰もいませんでした。");
			}
			
		}catch(Exception e) {
				e.printStackTrace();
			}
		
		
			}
		
	}


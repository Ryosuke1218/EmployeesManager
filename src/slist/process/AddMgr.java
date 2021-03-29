package slist.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import intro07.BaseDB;
import slist.BaseMenu;
import slist.ui.Menu;

public class AddMgr extends BaseMenu{
	/**
	 * 社員追加のためのメニュ表示
	 */
	public void showMenu(){
		System.out.println("社員を新規追加します。");
		/**
		 * 入力項目
		 */
		//first_name
		//last_name
		//email
		//phone_number
		//hire_date
		//job_id
		//salary
		//commision_pct
		//manager_id
		//department_id
		//入力項目のリストを作る
		
		String[] strArray = {"first_name", "last_name", "email", "phone_number",
				"hire_date", "job_id", "salary", "commision_pct", "manager_id", "department_id"};

		//入力情報を格納するためのMapを作る
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String sql = "INSERT INTO employees (first_name,"
				+ "last_name,"
				+ "email,"
				+ "phone_number,"
				+ "hire_date,job_id,"
				+ "salary,"
				+ "commision_pct,"
				+ "manager_id,"
				+ "department_id) VALUES(";
				
		//mapに対してデフォルト値を設定する
		for(int i = 0; i<strArray.length;i++) {
			if(i <= 5) {
				String value = (String)this.handleInput(strArray[i], true);
				System.out.println("入力された値(str)：" + value);
				//文字列入力モード
				map.put(strArray[i], value);
				sql += "'"+value+"',";
			}else {
				int value = (int)this.handleInput(strArray[i], false);
				if(i < 9) {
					sql += value + ",";
				}else {
					//最後は, )で閉じる
					sql += value + ")";
				}
				
				//数値入力モード
				map.put(strArray[i], value);
			}
			
			
		}
		BaseDB db = new BaseDB();
		int cnt = db.updateSQL(sql);
		
		if(cnt > 0) {
			//成功パターン
			System.out.println("正しく追加されました");
		}else {
			//失敗
			System.out.println("追加されませんでした。");
		}
		
		
		//前の画面に戻る
		Menu menu = new Menu();
		menu.showMenu();

	}
	/**
	 *  入力を促して、受け取ったデータを返す
	 * @return NULLであれば、だめ、NULLでないならば、OK
	 */
	public Object handleInput(String column, boolean isString) {
		
		InputStreamReader str = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(str);
		
		//入力チェック用のフラグを用意する
		boolean isOK = false;
		String val_str = null;
		int val_int = -1;
		
		
		try {
			while(!isOK) {
				System.out.println(column + "を入力してください。");
				//入力値を取得
				String val = br.readLine();
				
				if(isString) {
					//文字列入力モードであれば、nullチェックでOK?
					if(val != null) {
						if(val.length() > 0) {
							
							//文字列があれば、ループを抜ける
							if(column.equals("hire_date")) {
								
								int len = val.length();
								
								if((len == 8)&&(this.checkDigit(val))) {
									val_str = val;
									isOK = true;
								}else {
									System.out.println("入力誤りがあります。");
								}
							}else {
								val_str = val;
								isOK = true;
							}
						}
					}else {
						System.out.println("入力誤りがあります。");
					}
				}else {
					//数値入力モードであれば、nullチェック + 数値チェック
					if(val != null) {
						try {
							
							val_int = Integer.valueOf(val).intValue();
							isOK = true;
						}catch(Exception e) {
							continue;
						
						}
					}else {
						System.out.println("入力誤りがあります");
					}
					
				}
		}
			if(isString) {
				return val_str;
			}else {
				return val_int;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}



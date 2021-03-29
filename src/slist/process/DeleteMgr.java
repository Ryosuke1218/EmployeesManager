package slist.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import intro07.BaseDB;
import slist.ui.Menu;

public class DeleteMgr extends EditMgr{

	
	public void showMenu() {
		System.out.println("社員情報を削除します。削除対象の社員の検索条件を指定してください。");
		int inputNo = this.processMenu("selectmenu.txt", 1, 4);
		//入力値で場合分け
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
	//更新処理をします
	//this.conditionによって指定された条件を、update文にくっつける
	//そして、SQLを発行する
	public void processEdit() {
		System.out.println("削除しても良いですか?(Y/N)");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		try {
			//入力値を受け取る
			String str = br.readLine();
			//入力値によって場合分け
			switch(str) {
			case "Y":
				//消す場合はSQL発行
				StringBuffer buf = new StringBuffer("DELETE FROM employees ");
				buf.append(this.condition);
				String sql = buf.toString();
				BaseDB db = new BaseDB();
				int cnt = db.updateSQL(sql);
				System.out.println(cnt +"件削除されました");
				break;
			case "N":
				//消さない場合は戻る
				this.showMenu();
				break;
			default:
				// Y/N以外が入力されたら、再度開きなおす
				this.processEdit();
				break;
			}
			
				//前の画面に戻る
				this.showMenu();
				
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

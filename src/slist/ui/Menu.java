package slist.ui;

import slist.BaseMenu;
import slist.process.AddMgr;
import slist.process.DeleteMgr;
import slist.process.EditMgr;
import slist.process.SelectMgr;

public class Menu extends BaseMenu{
	
	public void showMenu(){
		System.out.println("********************");
		System.out.println("  社員管理システム");
		System.out.println("********************");
		
		
		try {	
				//switch-case構文を使って、場合分けする
				int menuNo = this.processMenu("menu.txt", 1, 4);
				
				switch(menuNo) {
				case 1:
					SelectMgr sMgr = new SelectMgr();
					sMgr.showMenu();
					break;
					
				case 2:
					AddMgr aMgr = new AddMgr();
					aMgr.showMenu();
					
					break;
				case 3:
					//3の場合の処理を書く
					EditMgr eMgr = new EditMgr();
					eMgr.showMenu();
					break;
				case 4:
					//4の場合の処理を書く
					DeleteMgr dMgr = new DeleteMgr();
					dMgr.showMenu();
					break;
				default:
					break;
				}
				
			
			
			}catch(Exception e) {
				e.printStackTrace();
		
		}
	}
	
}

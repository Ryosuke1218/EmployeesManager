package slist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class BaseMenu {
	
	
	public int processMenu(String file, int minNo, int maxNo) {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			int i = 0;
			while(true) {
				String a = br.readLine();
				if(a == null) {
					break;
				}else {
					System.out.println(a);
				}
				i++;
			}
				br.close();
				System.out.println("番号を入力してください :");
				
				//1～3を入力してもらう
				InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader br2 = new BufferedReader(isr);
				
				String input_no = br2.readLine();
				boolean isOK = this.checkInputNo(input_no, minNo, maxNo);
				//isOKがtrueになるまで繰り返す
				while(!isOK) {
					System.out.println(minNo + "～" + maxNo + "の値を指定して、メニュー番号を入力してください");
					//入力値を受けとる
					input_no = br2.readLine();
					isOK = this.checkInputNo(input_no, minNo, maxNo);
				}
				return Integer.valueOf(input_no).intValue();
				
				
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
	
	public boolean  checkInputNo(String input, int minNo, int maxNo) {
		
		boolean isALLDigit = true;
		isALLDigit = this.checkDigit(input);
		//1～4までの数字であるかどうかのをチェックする
		if(isALLDigit) {
			int val = Integer.valueOf(input).intValue();
			if(minNo<= val && val <= maxNo) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}

		
	}

	public boolean checkDigit(String input) {
		//文字列のサイズを測る
		int len = input.length();
		//全ての文字を順にみて、すべてtrueになるかどうか確かめる
		boolean isALLDigit = true;
				
		for(int i = 0; i<len;i++){
			char c = input.charAt(i);
			//文字が数値かどうかのチェックをする
			if(!Character.isDigit(c)) {
				isALLDigit = false;
				break;
					}
		
	}

		return isALLDigit;
	}
	
	
}

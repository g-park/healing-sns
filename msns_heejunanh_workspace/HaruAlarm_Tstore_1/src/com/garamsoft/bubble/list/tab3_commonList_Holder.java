package com.garamsoft.bubble.list;

//tab4 메인에서 리스트목록들을 눌렀을때 호출될 리스트를 담을 홀더.

public class tab3_commonList_Holder {

	// 위쪽에 나올 것.
	String tab3_list_item_left;
	
	// 아래 쪽에 나올 것.
	String tab3_list_item_right;
	
	
	public tab3_commonList_Holder(String _left, String _right){
		tab3_list_item_left = _left;
		tab3_list_item_right = _right;
	}
}

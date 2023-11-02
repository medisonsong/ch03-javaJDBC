package kr.s07.note;

/*
 * DAO: Data Access Object 
 * 		데이터베이스의 데이터를 전문적으로 호출하고 제어하는 객체 (메소드 단위로 작업)
 * 
 * 전처럼 class를 따로따로 만드는 게 아니라 dao 자체를 데이터베이스랑 연동한 전문적인 클래스로 만들고 NoteMain에서 화면 보이게 한 후 구동
 */

public class NoteDAO {
	//글 쓰기
	public void insertInfo(String name, String passwd, String subject, String content, String email) {
		//데이터베이스 연동
		System.out.println("글 쓰기를 완료했습니다.");
	}

	//목록 보기
	public void selectInfo() {
		//데이터베이스 연동
		System.out.println("작성한 글 목록을 표시합니다.");
	}

	//상세글 보기
	public void selectDetailInfo(int num) { //pk(num)로 상세 정보를 출력하기 때문에
		//데이터베이스 연동
		System.out.println("한 건의 상세 글 정보를 출력합니다.");
	}
	
	//글 수정
	public void updateInfo(int num, String name, String passwd, String subject, String content, String email) {
		//데이터베이스 연동
		System.out.println("한 건의 글 정보를 수정했습니다.");
	}
	
	//글 삭제
	public void deleteInfo(int num) {
		//데이터베이스 연동
		System.out.println("한 건의 글 정보를 삭제했습니다.");
	}
	
}

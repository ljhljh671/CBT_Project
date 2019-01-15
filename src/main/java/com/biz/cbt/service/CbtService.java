package com.biz.cbt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.biz.cbt.dao.CbtDao;
import com.biz.cbt.db.CbtSqlFactory;
import com.biz.cbt.vo.CbtVO;

public class CbtService {
	SqlSessionFactory sessionFactory;
	Scanner scan;

	public CbtService() {
		scan = new Scanner(System.in);
		CbtSqlFactory sqlFactory = new CbtSqlFactory();
		this.sessionFactory = sqlFactory.getSessionFactory();

	}

	public void menu() {
		while (true) {
			System.out.println("==============================");
			System.out.print("1.문제 입력   ");
			System.out.print("2.문제 풀이   ");
			System.out.println("3.종료   ");
			System.out.println("------------------------------");
			System.out.print("선택 >> ");
			String gostop = scan.nextLine();
			if (gostop.equals("1")) {
				while (true) {

					System.out.println("==============================");
					System.out.print("1.문제 등록   ");
					System.out.print("2.문제 수정   ");
					System.out.print("3.문제삭제    ");
					System.out.println("0.종료");
					System.out.println("------------------------------");
					System.out.print("선택 >> ");
					String gostop2 = scan.nextLine();
					if (gostop2.equals("1")) {
						insert();
					} else if (gostop2.equals("2")) {
						update();
					} else if (gostop2.equals("3")) {
						delete();
					} else if (gostop2.equals("0")) {
						break;
					}
				}

			} else if (gostop.equals("2")) {
				question();
			} else if (gostop.equals("3")) {
				System.out.println("감사합니다!");
				break;
			}
		}

	}
	
	// 클래스 내에서 사용될 것이기 때문에 private으로 선언 해주고
	// return값을 가질 것이기 때문에 CbtVO 형으로 메서드를 만든다.

	private CbtVO cbtInput() {

		CbtVO vo = new CbtVO();

		String[] str_q = new String[4];	
		// 보기를 저장하기 위해String 형 번수를 배열에 넣는다.
		String strQuestion;
		System.out.print("문제를 입력하세요 >> ");
		strQuestion = scan.nextLine();
		vo.setCb_question(strQuestion);
		// 먼저 문제 먼저 저장하고

		System.out.print("답안1번 >> ");
		str_q[0] = scan.nextLine();
		vo.setCb_q1(str_q[0]);
		// 1번 보기 저장

		System.out.print("답안2번 >> ");
		str_q[1] = scan.nextLine();
		vo.setCb_q2(str_q[1]);
		// 2번 보기 저장

		System.out.print("답안3번 >> ");
		str_q[2] = scan.nextLine();
		vo.setCb_q3(str_q[2]);
		// 3번 보기 저장

		System.out.print("답안4번 >> ");
		str_q[3] = scan.nextLine();
		vo.setCb_q4(str_q[3]);
		// 4번 보기 저장

		System.out.print("정답 문항  >> ");
		int intAns = Integer.valueOf(scan.nextLine());
		vo.setCb_answernum(str_q[intAns - 1]);
		// 정답 보기 저장

		System.out.println("입력 완료!!");
		

		return vo;
		// 입력이 다 됐으면 저장된 vo를 다른 곳에 써야 하니 return 해준다.
	}

	private void insert() {
		
		
		SqlSession session = sessionFactory.openSession();
		CbtDao dao = session.getMapper(CbtDao.class);

		CbtVO vo = cbtInput();
		
		// CbtInput에서 받아온 vo를 dao에 있는 dao에 넣는다.
		// 여기서 dao는 SQL Developer와 연결되어 있고
		// dao 클래스 안에는 SQL문이 저장되어 있다.

		dao.insert(vo);
		
		// Insert나 Delete할 때에는 꼭 commit을 해 준다.

		session.commit();
		session.close();

	}
	
	// insert와 비슷하다 수정할 번호를 받고 그 번호에 대한 문제, 보기, 정답 문항을 받은 후
	// 그 vo를 return 하여 준다.

	private CbtVO cbtupdate() {

		CbtVO vo = new CbtVO();

		String[] str_q = new String[4];
		String strQuestion;
		String strNumber;

		System.out.println("수정할 문제 번호 입력 >> ");
		strNumber = scan.nextLine();
		vo.setCb_num(Integer.valueOf(strNumber));

		System.out.print("문제 입력 >> ");
		strQuestion = scan.nextLine();
		vo.setCb_question(strQuestion);

		System.out.print("답안1번 >> ");
		str_q[0] = scan.nextLine();
		vo.setCb_q1(str_q[0]);

		System.out.print("답안2번 >> ");
		str_q[1] = scan.nextLine();
		vo.setCb_q2(str_q[1]);

		System.out.print("답안3번 >> ");
		str_q[2] = scan.nextLine();
		vo.setCb_q3(str_q[2]);

		System.out.print("답안4번 >> ");
		str_q[3] = scan.nextLine();
		vo.setCb_q4(str_q[3]);

		System.out.print("정답 문항  >> ");
		int intAns = Integer.valueOf(scan.nextLine());
		vo.setCb_answernum(str_q[intAns - 1]);

		System.out.println("수정 완료!!");

		return vo;
	}
	

	private void update() {
		SqlSession session = sessionFactory.openSession();
		CbtDao dao = session.getMapper(CbtDao.class);
		
		// cbtupdate()에서 나온 vo를 가지고 dao에 있는 update문을 실행 하여 준다.
		// 그러면 SQL Developer와 연결되어 있는 dao는 SQL Developer에게 vo 값과 함께
		// SQL문을 전달할 것이다.
		// 그것을 받은 SQL Developer는 받은 정보로 업데이트 될 것이다.

		CbtVO vo = cbtupdate();
		
		// 업데이트 할 문제 번호를 잘못 잘못 입력했을 경우 int ret 값은 음수가 나오게 dao에 지정되어있다.
		int ret = dao.update(vo);

		session.commit();
		session.close();
		
		// 위에서 정의한 ret가 만약 음수이면 수정이 문제 번호가 잘못되어 저장되지 않았다고 알려준다.
		if(ret > 0 ) {
			System.out.println("수정 완료!!");
		} else {
			System.out.println("문제 번호가 잘못되었습니다!!");
		}

	}

	private void delete() {
		SqlSession session = sessionFactory.openSession();
		CbtDao dao = session.getMapper(CbtDao.class);
		// Delete는 삭제할 문제 번호를 키보드로 받아 받아
		// dao에 있는 delete문으로 보내준다.
		// 이때 cb_num은 long형이기 때문에 Integer형으로 바꿔 준다.
		String cb_num;
		System.out.println("삭제할 문제 번호>> ");
		cb_num = scan.nextLine();
		int ret = dao.delete(Integer.valueOf(cb_num));

		
		session.commit();
		session.close();
		
		// 문제번호가 잘못되어 저장되지 않았을 때 알려준다.
		
		if(ret > 0 ) {
			System.out.println("삭제 완료!!");
		} else {
			System.out.println("문제 번호가 잘못되었습니다!!");
		}


	}

	private List<CbtVO> shuffle() {
		SqlSession session = sessionFactory.openSession();
		CbtDao dao = session.getMapper(CbtDao.class);
		// Oracle에 저장되어 있는 모든 문제를 섞어서 문제를 내야 하므로
		// shuffle을 이용해 리스트를 섞는다.
		List<CbtVO> listvo = dao.selectAll();
		Collections.shuffle(listvo);
		
		// 섞인 list를 return 해준다.
		return listvo;
	}

	private void question() {

		List<CbtVO> listvo = shuffle();	// 섞은 문제들
		int i = 0;	// 몇번째 문제인지 알려주는 숫자
		List<String> listox = new ArrayList();	
		// 맞았는지 틀렸는지 알려주는 문자열을
		// 저장하는 list
		String ox;	// 맞았는지 틀렸는지 알려주는 문자열
		int intscore = 0;	// 획득한 점수를 저장하는 변수

		for (CbtVO vo : listvo) {
			// continue를 사용하기 때문에  5문제를 풀고
			// 성적과 점수를 알려주는 명령문을
			// 맨 위로 설정 해 놓았다.
			// 이 때 i는 맨 처음 시작하는 0일때는 나오지 말아야 하며
			// 5문제 마다 알려줘야 하므로
			// 지금까지 푼 문제를 5로 나누었을 때 나머지가 0이어야 한다.
			if (i != 0 && i % 5 == 0) {
				System.out.println("======================");
				System.out.println("지금까지의 성적 >>");
				// 문제 번호까지 저장된 맞았는지 틀렸는지 알려주는 listox를 콘솔에 불러오고
				for (int j = 1; j <= i; j++) {
					System.out.print(j + "번 >> ");
					System.out.println(listox.get((j - 1)));
				}
				// 지금까지의 점수가 들어있는 intscore를 콘솔에 불러온다.
				System.out.print("지금까지의 점수 >> ");
				System.out.println(intscore);
				System.out.println("======================");
			}
			
			// 지금까지의 성적을 알려주고 난 후에 문제번호를 1 더한다.
			i++;
			
			// 문제풀이를 시작한다.
			// 문제 보기도 섞어야 하기 때문에 
			// String형 변수를 배열에 넣고
			// Arrays.asList를 이용해 List에 넣고
			

			String[] str_q = new String[4];
			str_q[0] = vo.getCb_q1();
			str_q[1] = vo.getCb_q2();
			str_q[2] = vo.getCb_q3();
			str_q[3] = vo.getCb_q4();
			
			// 셔플을 실행한다.
			Collections.shuffle(Arrays.asList(str_q));
			
			
			
			// 몇번인지 알려주고
			System.out.println(i + "번");
			// 각문제의 고유의 번호를 알려준 후
			System.out.print(vo.getCb_num() + "번 문제 >> ");
			// 문제, 보기를 콘솔에 표시한다.
			System.out.println(vo.getCb_question());
			System.out.print("1. ");
			System.out.println(str_q[0]);
			System.out.print("2. ");
			System.out.println(str_q[1]);
			System.out.print("3. ");
			System.out.println(str_q[2]);
			System.out.print("4. ");
			System.out.println(str_q[3]);
			
			// 답을 키보드로 받아온다.

			System.out.print("정답>> ");
			String cb_answer = scan.nextLine();
			// 배열 순서에 넣어야 하니
			// 받은 답을 Integer형으로 바꾸어 주고
			int inta = Integer.valueOf(cb_answer);
			
			// 받은 답의 보기를 저장힌다.
			String stra = str_q[inta - 1];
			
			// 만약 키보드로 입력받은 답의 보기와 저장해 놓았던 정답의 보기를
			// 문자열으로 비교하여 같으면

			if (vo.getCb_answernum().equals(stra)) {
				// 정답이라고 말해주고 O를 listox에 저장해 준다.
				System.out.println("정답입니다!! (종료하기 >> 0  계속하기 >> Enter)");
				ox = "O";
				listox.add(ox);
				// intscore에도 5점을 더해주고
				intscore += 5;
				// 다음 행동을 결정하기 위해 키보드로 결정을 입력받는다.
				System.out.print("입력>> ");
				String strgo = scan.nextLine();
				// 0을 입력받으면 문제 풀이를 종료한다.
				if (strgo.equals("0")) {
					break;
				// 0이 아닌 값을 받았을 때
				// 문제를 계속 풀기 위해 아래는 for문에 있는 아래는 생략하고
				// for문 맨 처음으로 간다.
				
				} else {
					continue;
				}
				
				// 한번 더  풀어볼 보기, 다음 문제로 넘어가는 보기, 종료하는 보기를 준다.

			} else {
				System.out.println("다시 생각해 보세요 (한번 더 풀어보기 >> 1" + " 다음 문제로 넘어가기 >> Enter "
						+ "종료 >> 0)");
				System.out.print("입력 >> ");
				String gostop = scan.nextLine();
				// 1번을 입력하면  방금 전 풀었던 그 문제를 또 준다.
				if (gostop.equals("1")) {
					System.out.println(i + "번");
					System.out.print(vo.getCb_num() + "번 문제 >> ");
					System.out.println(vo.getCb_question());
					System.out.print("1. ");
					System.out.println(str_q[0]);
					System.out.print("2. ");
					System.out.println(str_q[1]);
					System.out.print("3. ");
					System.out.println(str_q[2]);
					System.out.print("4. ");
					System.out.println(str_q[3]);

					System.out.print("정답>> ");
					cb_answer = scan.nextLine();
					inta = Integer.valueOf(cb_answer);
					stra = str_q[inta - 1];
					
					// 다시 풀 기회를 준 문제에서 맞으면

					if (vo.getCb_answernum().equals(stra)) {
						System.out.println("정답입니다!! (종료하기 >> 0  계속하기 >> Enter)");
						
						// 정답이라고 콘솔에 보이고
						// 맞았다고 O를 저장해 놓고, intscore에 5점을 더해준다.

						ox = "O";
						listox.add(ox);
						intscore += 5;
						
						// 여기에서 또 종료할지 계속할지의 결정을 키보드로 받는다.
						System.out.print("입력>> ");
						String strgo = scan.nextLine();
						if (strgo.equals("0")) {
							break;
						} else {
							continue;
						}
						// 다시 또 푼 문제에서 틀렸을 경우
					} else {
						// 오답이라고 콘솔에 표시하고 X를 listox에 넣는다.
						System.out.println("오답입니다!! (종료 >> 0  계속 문제 풀기 >> Enter)");
						ox = "X";
						listox.add(ox);
						
						// 또 결정을 받는다.
						System.out.print("입력>> ");
						String strgo = scan.nextLine();
						if (strgo.equals("0")) {
							break;
						} else {
							continue;

						}

					}
					
					// 다시생각해 보라는 결정에서
					// 다시 안풀고 종료하겠다는 결정을 내리면
					// 틀린것이므로 lisox에 X를 넣는다.

				} else if (gostop.equals("0")) {
					ox = "X";
					listox.add(ox);
					break;
					
				// 다시생각해 보라는 결정에서
				// 다시 안풀고 다른 문제를 풀겠다는 결정을 내리면
				// 틀린것이므로 lisox에 X를 넣는다.
					
				} else {
					ox = "X";
					listox.add(ox);
					continue;
				}

			}
			
		}
		
		// break문이 걸리면 for문이 끝난 이곳으로 온다.
		// 5문제마다 성적을 보여주지만
		// 문제를 중간에 종료했을 때에도 성적을 보여줘야 하니
		// for문이 끝난 이곳에서  성적을 보여준다.
		
		System.out.println("======================");
		System.out.println("지금까지의 성적 >>");
		for (int j = 1; j < (i + 1); j++) {
			System.out.print(j + "번 >> ");
			System.out.println(listox.get((j - 1)));
		}
		System.out.print("지금까지의 점수 >> ");
		System.out.println(intscore);
		System.out.println("======================");
	}

	private void select() {
		SqlSession session = sessionFactory.openSession();
		CbtDao dao = session.getMapper(CbtDao.class);

		List<CbtVO> vo = dao.selectAll();
		System.out.println(vo);
	}

}

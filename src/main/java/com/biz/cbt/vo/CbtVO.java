package com.biz.cbt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CbtVO {
	
    long cb_num;			// 문제의 고유 번호
    String cb_question ;	// 문제
    String cb_q1 ;			// 보기 1번
    String cb_q2 ; 			// 보기 2번
    String cb_q3 ;			// 보기 3번
    String cb_q4 ;			// 보기 4번
    String cb_answernum ;	// 정답 문항

}

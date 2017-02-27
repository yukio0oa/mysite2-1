package com.bit2017.mysite.repository;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bit2017.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert( GuestbookVo guestbookVo ) {
		int count = sqlSession.insert( "guestbook.insert", guestbookVo );
		// insert 후에 PK 받아내기
		// System.out.println( guestbookVo );
		return count == 1;
	}
	
	public boolean delete( GuestbookVo guestbookVo ) {
		int count = sqlSession.delete( "guestbook.delete", guestbookVo );
		return (count == 1);
	}

	public GuestbookVo get( Long no ) {
		return sqlSession.selectOne( "guestbook.getByNo", no );
	}
	
	public List<GuestbookVo> getList() {
		return sqlSession.selectList( "guestbook.getList" );
	}
	
	public List<GuestbookVo> getList( int page ) {
		return sqlSession.selectList( "guestbook.getListByPage", page );
	}
	
}

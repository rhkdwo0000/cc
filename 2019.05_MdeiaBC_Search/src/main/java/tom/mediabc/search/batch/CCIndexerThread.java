package tom.mediabc.search.batch;

import java.util.Date;
import java.util.Random;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.common.configuration.LoggerName;
import tom.common.util.ObjectTicket;
import tom.mediabc.search.dao.RegisterQueueDAOImpl;
import tom.mediabc.search.vo.RegisterQueueVO;

public class CCIndexerThread extends Thread{

	private Logger log = LoggerFactory.getLogger(LoggerName.INDEX_BATCH);
	private RegisterQueueVO queueVO;
	private RegisterQueueDAOImpl registerQueueDAO;
	
	public CCIndexerThread(RegisterQueueDAOImpl registerQueueDAO, RegisterQueueVO queueVO) {
		this.registerQueueDAO = registerQueueDAO;
		this.queueVO = queueVO;
	}
	
	public void run() {
		
		GenericObjectPool<ObjectTicket> indexerPool = CCIndexerPoolWrapper.getInstance().getObjectPool();
		ObjectTicket ticket = null;
		int tid = (new Random()).nextInt(1000000);
		try {
			ticket = indexerPool.borrowObject();//인스턴스를 빌린다.
			log.debug("["+tid+"] ======== Start Indexer ========");
			
			queueVO.setJobProc(RegisterQueueVO.JOB_INDEXING);//다운로드 완료 세팅
			queueVO.setJobIndexStart(new Date());//인덱싱 시작시간 세팅
			registerQueueDAO.updateRegisterQueue(queueVO);// registerQueueDAO 다운로드 완료 & 시작시간 update
			
			
			
			log.debug("["+tid+"] processing ..... ");
			Thread.sleep(60000);
			//엘라스틱서치 인덱싱
			
			
			
			
			
			
			
			
			
			
			queueVO.setJobProc(RegisterQueueVO.JOB_INDEXING_DONE);// 인덱싱하는중 세팅
			queueVO.setJobIndexEnd(new Date());// 인덱싱 끝난시간 세팅
			registerQueueDAO.updateRegisterQueue(queueVO);// registerQueueDAO 인데싱중 & 끝난시간 update
			
		} catch (Exception e) {
			log.error("["+tid+"]indexing error " + e.getMessage(), e);
			
			//ERROR 로그 관련내용 기록...
			
		} finally {
			if(ticket != null) {
				try {
					indexerPool.returnObject(ticket);//빌린 인스턴스를 반납한다.
				} catch (Exception e) {
					log.error("["+tid+"]Object return error " + e.getMessage(), e);
				}
			}
			
			log.debug("["+tid+"] ===============================");
		}
		
		
	}
}

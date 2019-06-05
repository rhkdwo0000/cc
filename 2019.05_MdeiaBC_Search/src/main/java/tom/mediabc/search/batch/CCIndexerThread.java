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
			ticket = indexerPool.borrowObject();
			log.debug("["+tid+"] ======== Start Indexer ========");
			
			queueVO.setJobProc(RegisterQueueVO.JOB_INDEXING);
			queueVO.setJobIndexStart(new Date());
			registerQueueDAO.updateRegisterQueue(queueVO);
			
			
			
			
			
			log.debug("["+tid+"] processing ..... ");
			Thread.sleep(60000);
			
			
			
			
			
			
			
			queueVO.setJobProc(RegisterQueueVO.JOB_INDEXING_DONE);
			queueVO.setJobIndexEnd(new Date());
			registerQueueDAO.updateRegisterQueue(queueVO);
			
		} catch (Exception e) {
			log.error("["+tid+"]indexing error " + e.getMessage(), e);
			
			
			//ERROR 로그 관련내용 기록...
			
			
		} finally {
			if(ticket != null) {
				try {
					indexerPool.returnObject(ticket);
				} catch (Exception e) {
					log.error("["+tid+"]Object return error " + e.getMessage(), e);
				}
			}
			
			log.debug("["+tid+"] ===============================");
		}
		
		
	}
}

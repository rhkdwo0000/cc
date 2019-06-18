package tom.mediabc.search.batch;

import java.util.List;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tom.common.configuration.Configuration;
import tom.common.configuration.LoggerName;
import tom.common.util.ObjectTicket;
import tom.mediabc.search.dao.RegisterQueueDAOImpl;
import tom.mediabc.search.vo.RegisterQueueVO;

@Repository("CCIndexBatch")
public class CCIndexBatch {
	
	
	private Logger log = LoggerFactory.getLogger(LoggerName.INDEX_BATCH);
	
	@Autowired
	private RegisterQueueDAOImpl registerQueueDAO;
	
	public void indexNewCC() {
		
		if(Configuration.getInstance().isInit() == false) {//false 면 indexNewCC메서드 종료 (true 인 경우가 언제인지?) 
			return;
		}
		
		/* pool에 오브젝트를 담아두고 빌리고 반납하고 할수 있는 클래스다. 사용하기위해서는 commons-pool이라는 라이브러리를 사용해야한다.*/
		GenericObjectPool<ObjectTicket> indexerPool = CCIndexerPoolWrapper.getInstance().getObjectPool(); 
		int activeThread = indexerPool.getNumActive();//현재 GenericObjectPool에서 빌린 인스턴스수 
		int maxThread = indexerPool.getMaxActive();// 현재 GenericObjectPool에서 할당되어 관리되는 최대 인스턴스 수 default = 8
		int availableThread = maxThread - activeThread;//쉬고있는(사용가능한) 인스턴스 수 
		
		log.debug("------------------- INDEX NEW ComplexContent -------------------");
		log.debug("availableThread["+availableThread+"] = maxThread["+maxThread+"] - activeThread["+activeThread+"]");
		
		try {
			if(availableThread > 0) {// 이용가능한 인스턴수 수가 존재하면 
				
				/* JOB_DOWNLOAD_DONE = DD 이므로 job_proc가 DD이면서 날짜(reg_date)로 정렬하고 limit가  availableThread인 만큼 list에 담는다.  */
				List<RegisterQueueVO> jobList = registerQueueDAO.selectJobByCode(RegisterQueueVO.JOB_DOWNLOAD_DONE, availableThread);
		
				/* list 의 길이만큼 쓰레드 생성 및 실행 */
				for(int i=0; i<jobList.size(); i++) {
					CCIndexerThread ccIndexerTh = new CCIndexerThread(registerQueueDAO, jobList.get(i));
					ccIndexerTh.start();
				}
				
				
				
				
			}
		} catch (Exception e) {
			log.error("ERROR " + e.getMessage(), e);
		}
		
		log.debug("----------------------------------------------------------------");
	}
}

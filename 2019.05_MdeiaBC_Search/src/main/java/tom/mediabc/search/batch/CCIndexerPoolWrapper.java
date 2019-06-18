package tom.mediabc.search.batch;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

import tom.common.configuration.Configuration;
import tom.common.util.ObjectTicketPool;
import tom.common.util.ObjectTicket;

public class CCIndexerPoolWrapper {

	
	private PoolableObjectFactory<ObjectTicket> factory = null;
	private GenericObjectPool<ObjectTicket> objectPool = null;
	
	private static CCIndexerPoolWrapper instance = null;
	private CCIndexerPoolWrapper() {// 해당 객체를 만들면 밑과 같이 factory 와 maxactive 를 세팅한후 GenericObjectPool객체 생성을 해준다.
		Integer threadNum = Configuration.getInstance().getIntegerExtra("batch.index.thread");
		factory = new ObjectTicketPool();
        objectPool = new GenericObjectPool<ObjectTicket>(factory, threadNum.intValue());
    }
	
    public static synchronized CCIndexerPoolWrapper getInstance() {
        if (instance == null) {
            instance = new CCIndexerPoolWrapper();// getInstance 로만 해당클래스에 접근 
        }
        return instance;
    }
    
    public GenericObjectPool<ObjectTicket> getObjectPool() {
    	return objectPool;
    }
    
    /*
    public Ticket borrowObject() throws Exception {
    	return objectPool.borrowObject();
    }
    public void returnObject(Ticket obj) throws Exception {
    	objectPool.returnObject(obj);
    }
    
    public int getNumActive() {
    	objectPool.getNumActive();
    }
    */
}

/**
 * 
 */
package org.tch.ste.infra.test.repository;

import static org.junit.Assert.assertTrue;

import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.entity.BatchFileType;
import org.tch.ste.infra.repository.PageSort;
import org.tch.ste.infra.repository.PageSort.SortType;
import org.tch.ste.infra.repository.PagingAndSortingJpaDao;
import org.tch.ste.test.base.BaseTest;

/**
 * Test the basic DAO functionalities.
 * 
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@org.tch.ste.test.base.TransactionalUnitTest
public class DaoTest extends BaseTest {

    @Autowired
    @Qualifier("batchFileTypeDao")
    private PagingAndSortingJpaDao<BatchFileType, Integer> batchFileTypeDao;

    /**
     * @throws PropertyVetoException
     *             - Thrown by JNDI.
     * @throws IllegalStateException
     *             - Thrown by JNDI.
     * @throws NamingException
     *             - Thrown by JNDI.
     */
    public DaoTest() throws PropertyVetoException, NamingException {
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    @Test
    @Transactional
    public void testSave() {
        BatchFileType ti1 = createBatchFileType("BOA");
        batchFileTypeDao.save(ti1);
        BatchFileType ti2 = createBatchFileType("CITI");
        batchFileTypeDao.save(ti2);

        // Fetch
        BatchFileType ex1 = batchFileTypeDao.get(1);
        makeSureEquals(ex1, ti1);
        BatchFileType ex2 = batchFileTypeDao.get(2);
        makeSureEquals(ex2, ti2);

        assertTrue(batchFileTypeDao.count() == 2);

        // Validate the Queries.
        List<BatchFileType> nonPagedValues = batchFileTypeDao.findByQuery("select t from BatchFileType t",
                new HashMap<String, Object>());
        assertTrue(nonPagedValues.size() == 2);

        PageSort pager = new PageSort();
        pager.setStart(0);
        pager.setMax(1);
        List<String> sortCriteria = new ArrayList<String>();
        sortCriteria.add("fileName");
        pager.setSortCriteria(sortCriteria);
        pager.setSortType(PageSort.SortType.ASCENDING);

        List<BatchFileType> pagedValues1 = batchFileTypeDao.findByQueryPaged("select t from BatchFileType t",
                new HashMap<String, Object>(), pager);
        assertTrue(pagedValues1.size() == 1);
        assertTrue("BOA".equals(pagedValues1.get(0).getFileName()));

        pager.setStart(1);

        List<BatchFileType> pagedValues2 = batchFileTypeDao.findByQueryPaged("select t from BatchFileType t",
                new HashMap<String, Object>(), pager);
        assertTrue(pagedValues2.size() == 1);
        assertTrue("CITI".equals(pagedValues2.get(0).getFileName()));

        pager.setSortType(SortType.DESCENDING);

        List<BatchFileType> pagedValues3 = batchFileTypeDao.findByQueryPaged("select t from BatchFileType t",
                new HashMap<String, Object>(), pager);
        assertTrue(pagedValues3.size() == 1);
        assertTrue("BOA".equals(pagedValues3.get(0).getFileName()));
    }

    /**
     * @param obj1
     * @param obj2
     */
    private static void makeSureEquals(BatchFileType obj1, BatchFileType obj2) {
        assertTrue(obj1.getId().equals(obj2.getId()));
        assertTrue(obj1.getFileName().equals(obj2.getFileName()));
    }

    /**
     * @param name
     * @return
     */
    private static BatchFileType createBatchFileType(String name) {
        BatchFileType retVal = new BatchFileType();
        retVal.setFileName(name);
        return retVal;
    }

}

package www.wanandroid.com.wanandroid.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.dao.DaoMaster;
import www.wanandroid.com.wanandroid.dao.DaoSession;
import www.wanandroid.com.wanandroid.dao.SearchHistoryDao;
import www.wanandroid.com.wanandroid.entry.dao.SearchHistory;

public class DataBaseManager {
    private static DataBaseManager dataBaseManager;
    private DaoSession daoSession;



    public static DataBaseManager getInstance(Context context){
        if (dataBaseManager==null){
            synchronized (DataBaseManager.class){
                if (dataBaseManager==null){
                    dataBaseManager=new DataBaseManager(context);
                }
            }
        }
        return dataBaseManager;
    }

    private DataBaseManager(){

    }

    private DataBaseManager(Context context) {
            initDataBase(context);
    }


    private void initDataBase(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, Constant.DATABASE_NAME, null);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        daoSession = daoMaster.newSession();
    }

   //保存数据
    public void save(SearchHistory searchHistory){
        SearchHistoryDao dao=daoSession.getSearchHistoryDao();
        SearchHistory history=dao.queryBuilder().where(SearchHistoryDao.Properties.KeyWord.eq(searchHistory.getKeyWord())).unique();
        if (history==null){
            dao.insert(searchHistory);
        }
    }
   //查询全部数据
    public List<SearchHistory>queryAllSearchHistory(){
        SearchHistoryDao dao=daoSession.getSearchHistoryDao();
        QueryBuilder<SearchHistory>historyQueryBuilder=dao.queryBuilder();
        return historyQueryBuilder.orderDesc(SearchHistoryDao.Properties.Id).list();
    }
    //清空全部数据
     public void deleteAllSearchHistory(){
         SearchHistoryDao dao=daoSession.getSearchHistoryDao();
         dao.deleteAll();
     }
    //根据id删除数据
    public void deleteSearchHistoryById(Long id){
        SearchHistoryDao dao=daoSession.getSearchHistoryDao();
        dao.deleteByKey(id);
    }


}

package www.wanandroid.com.wanandroid.manager;

import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

/*活动管理类*/
public class ActivityManager {
    private static Stack<AppCompatActivity>activityStack;
    private static ActivityManager instance;

    private ActivityManager(){

    }

    /**
     * 单一实例
     */
    public static ActivityManager getInstance(){
        if(instance == null){
            synchronized (ActivityManager.class){
                if(instance == null){
                    instance = new ActivityManager();
                }
            }
        }
        return instance;
    }

    //获取活动栈的大小
    public int getActivityStackSize(){
        if (activityStack==null){
            return 0;
        }
        return activityStack.size();
    }

    /**
     * 添加Activity到堆栈
     */

    public void addActivity(AppCompatActivity activity){
        if (activityStack==null){
            activityStack=new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public AppCompatActivity currentActivity(){
        AppCompatActivity activity=activityStack.lastElement();
        return activity;
    }

//获取前一个Activity （当前Activity前一个Activity）

    public AppCompatActivity beforeActivity(){
        AppCompatActivity activity=activityStack.get(activityStack.size()-2);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity(){
        AppCompatActivity activity=activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(AppCompatActivity activity){
        if(activity!=null){
            activityStack.remove(activity);
            activity.finish();
            activity=null;
        }
    }
    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls){
        for (AppCompatActivity activity:activityStack) {
             if (activity.getClass().equals(cls)){
                 finishActivity(activity);
             }
        }
    }

    /**
     * 结束指定数量的Activity
     */
    public void finishManyActivity(int count){
        for (int i = 0; i < count; i++){
            if(activityStack.isEmpty()){
                return;
            }
            try {
                AppCompatActivity activity=activityStack.pop();
                activity.finish();
                activity=null;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * 结束所有Activity
     */
    public void finishAllActivity(){
        if(activityStack == null){
            return;
        }
        for (int i = 0; i < activityStack.size(); i++) {
             if (activityStack.get(i)!=null){
                 activityStack.get(i).finish();
             }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

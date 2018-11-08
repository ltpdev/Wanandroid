package www.wanandroid.com.wanandroid.entry.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class SearchHistory {
    @Id(autoincrement = true)
    private Long id;
    private String keyWord;
    public String getKeyWord() {
        return this.keyWord;
    }
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1150442660)
    public SearchHistory(Long id, String keyWord) {
        this.id = id;
        this.keyWord = keyWord;
    }
    @Generated(hash = 1905904755)
    public SearchHistory() {
    }


}

package workshops.database.handler;

public class FossCategory {
	
	Integer _id;
    String _fosscategory;
    int _imageid ;
    String _level;
    String _language;
    String _tutorialname;
    String _tutoriallink;
    String _fossdesc;
   
	public FossCategory()
    {
		
    }
	public FossCategory(int _imageid,String _fosscategory,String _fossdesc) {
		this._imageid = _imageid;
		this._fosscategory = _fosscategory;
		this._fossdesc = _fossdesc;
	}
	public String get_fossdesc() {
		return _fossdesc;
	}
	public void set_fossdesc(String _fossdesc) {
		this._fossdesc = _fossdesc;
	}
	public FossCategory(String _fosscategory,String _language) {
		
		this._fosscategory = _fosscategory;
		this._language = _language;
	}
	public FossCategory(String _fosscategory,String _language,String _level,String _tutorialname,String _tutoriallink) {
		
		this._fosscategory = _fosscategory;
		this._language = _language;
		this._level = _level;
		this._tutorialname = _tutorialname;
		this._tutoriallink = _tutoriallink;
	}
    public String get_tutoriallink() {
		return _tutoriallink;
	}
	public void set_tutoriallink(String _tutoriallink) {
		this._tutoriallink = _tutoriallink;
	}
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public String get_fosscategory() {
		return _fosscategory;
	}
	public void set_fosscategory(String _fosscategory) {
		this._fosscategory = _fosscategory;
	}
	public int get_imageid() {
		return _imageid;
	}
	public void set_imageid(int _imageid) {
		this._imageid = _imageid;
	}
	public String get_level() {
		return _level;
	}
	public void set_level(String _level) {
		this._level = _level;
	}
	public String get_language() {
		return _language;
	}
	public void set_language(String _language) {
		this._language = _language;
	}
	public String get_tutorialname() {
		return _tutorialname;
	}
	public void set_tutorialname(String _tutorialname) {
		this._tutorialname = _tutorialname;
	}
}
   
  

    

package workshops.database.handler;

public class Contacts {
	//private variables
		int _id;
		String _state;
		String _person;
		String _email;
		String _phone;
		
		// Empty constructor
		public Contacts() {
			// TODO Auto-generated constructor stub
		}
		
		public Contacts(int _id, String _state, String _person, String _email,
				String _phone) {
			super();
			this._id = _id;
			this._state = _state;
			this._person = _person;
			this._email = _email;
			this._phone = _phone;
		}
		public Contacts(String _state, String _person, String _email,
				String _phone) {
			super();
			this._state = _state;
			this._person = _person;
			this._email = _email;
			this._phone = _phone;
		}
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public String get_state() {
			return _state;
		}
		public void set_state(String _state) {
			this._state = _state;
		}
		public String get_person() {
			return _person;
		}
		public void set_person(String _person) {
			this._person = _person;
		}
		public String get_email() {
			return _email;
		}
		public void set_email(String _email) {
			this._email = _email;
		}
		public String get_phone() {
			return _phone;
		}
		public void set_phone(String _phone) {
			this._phone = _phone;
		}
		
		
}

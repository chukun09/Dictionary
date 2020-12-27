package Model;

public class FavouriteWord {
	private String account;
	private String word;
	private String detail;

	public FavouriteWord(String account, String word, String detail) {
		super();
		this.account = account;
		this.word = word;
		this.detail = detail;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}

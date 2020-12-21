package Model;

public class Word {

	private int idx;
	private String word;
	private String detail;

	public Word(int idx, String word, String detail) {
		super();
		this.idx = idx;
		this.word = word;
		this.detail = detail;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
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

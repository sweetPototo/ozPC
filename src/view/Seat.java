package view;
import javax.swing.JLabel;


	public class Seat extends JLabel {
    private int num;
    private int time;

    public Seat() {
        super();    // JLabel 생성자 호출
    }

    public Seat(int num, int time) {
        this.num = num;
        this.time = time;
        setText(num+1 + "번");
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public int getTime() {
        return time;
    }

    public void setSeat(int num, int time) {
        this.num = num;
        this.time = time;
        setText("사용 중" + num + "번\n" + time + "시간 남았습니다");
    }

} 




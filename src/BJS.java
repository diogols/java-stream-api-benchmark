import model.BJSUtils;
import model.Transaction;
import java.util.List;

public class BJS {
    public static void main(String[] args) {
        List<Transaction> list = BJSUtils.load("transCaixa6M.txt", BJSUtils.toTransaction);

        System.out.println(BJSUtils.testBox(BJSUtils.j8SDT1(list)).getKey());
        System.out.println(BJSUtils.testBox(BJSUtils.j8DST1(list)).getKey());

        System.out.println(BJSUtils.testBox(BJSUtils.j8SDPT1(list)).getKey());
        System.out.println(BJSUtils.testBox(BJSUtils.j8DSPT1(list)).getKey());
    }
}

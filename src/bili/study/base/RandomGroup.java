package bili.study.base;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @program: hand
 * @description:
 * @author: tianwei
 * @create: 2020-04-27 17:10
 */
public class RandomGroup {

    public static void main(String[] args) {
        String[] strings = {"曾从威","蒋恺均","刘江涛","崔雅倩","祝秋培","吴赟鹏","李鸿飞","王  虎","缪  峰","张  龙","刘加封",
                "姚  笛","郑  丹","孔海宇","陈逸伦","徐煜清","陈  喆","盛  开","方文倩","边金鹏","陈振宇","陶小康","孙凌霄","王玉恒",
                "王娇娇","许新华","胡光兴","周  波","王  鹏","陈  程","徐小蕤","李  峰","王  琦","陈梦玉","陈志辉","李  森","陈  芳"};

        System.out.println(Arrays.deepToString(group(strings)));
    }

    private static String[][] group(String[] s) {
        String[][] result = new String[10][4];
        List<String> list = Arrays.asList(s);

        Random random = new Random();

        boolean[] flag = new boolean[37];

        int count = 0;

        for (int i = 0; i < result.length; i++) {
            if (count > 36) {
                break;
            }
            for (int j = 0; j < result[i].length; j++) {
                if (count > 36) {
                    break;
                }
                int rand = random.nextInt(37);
                while (flag[rand]) {
                    rand = random.nextInt(37);
                }
                flag[rand] = true;
                result[i][j] = s[rand];
                count++;
            }
        }
        return result;
    }
}

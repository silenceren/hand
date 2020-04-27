package bili.study.base;

/**
 * @program: hand
 * @description:
 * @author: tianwei
 * @create: 2020-04-04 11:15
 */
public class Clothe {

    private static int num=1; //共用的静态变量
    private int id;

    Clothe(){  //对象创建时调用构造方法
        this.id=Clothe.num; //创建时赋值
        Clothe.num++;   //序号递增
    }

    //获取编号
    public int getId() {
        return id;
    }

}

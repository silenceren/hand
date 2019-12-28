# 面试

## JVM_OOM:
   - java.lang.StackOverflowError
   - java.lang.OutOfMemoryError: Java heap space
   - java.lang.OutOfMemoryError: GC overhead limit exceeded
   - java.lang.OutOfMemoryError: Direct buffer memory
   - java.lang.OutOfMemoryError: Metaspace
   - java.lang.OutOfMemoryError: unable to create new native thread
   - java.lang.OutOfMemoryError: Requested arror size exceeds VM limit

## Java堆（Heap）
  Java堆从GC的角度还可以细分为：新生代（Eden区、From Survivor区和 To Survivor区）和老年代
  ![Aaron Swartz](https://raw.githubusercontent.com/silenceren/hand/master/pic/minorGC.png)
   - 1 eden、SurvivorFrom复制到SurvivorTo，年龄+1
      首先，当Eden区满的时候会触发第一次GC，把还活着的对象拷贝到SurvivorFrom区，当Eden区再次触发GC的时候会扫描Eden区和From区域，对这两个区域进行垃圾回收，经过这次回收后还存活的对象，则直接复制到To区域（如果有对象的年龄已经达到了老年的标准，则赋值到老年代区），同时将这些对象的年龄+1
   - 2 清空eden、SurvivorFrom
      然后，清空Eden和SurvivorFrom中的对象，也即复制之后有交换，谁空谁是to
   - 3 SurvivorTo 和 SurvivorFrom 互换
      最后，SurvivorTo 和 SurvivorFrom 互换，原 SurvivorTo 成为下一次GC时的SurvivorFrom区。部分对象会在From和To区域中复制来复制去，如此交换15次（由JVN参数MaxTenuringThreshold决定，这个参数默认是15），最终如果还是存活，就存入老年代
   - 4 大对象特殊情况
      如果分配的新对象比较大 Eden 区放不下但 Old 区可以放下时， 对象会被直接分配到Old 区 （即没有晋升这一过程，直接到老年代了）

## 谈谈对 volatile 的理解
   - volatile 是 Java虚拟机提供的轻量级的同步机制：
     - 保证可见性
     - 不保证原子性
     - 禁止指令重排
    
## JMM（Java内存模型Java Memory Model）
   (可见性、原子性、有序性)
   - JMM（Java内存模型Java Memory Model）本身是一种抽象的概念并不真实存在，它描述的是一组规则或规范，通过这组规范定义了程序中各个变量（包括实例字段，静态字段和构成数组对象的元素）的访问方式。
      - JMM关于同步规定:
        - 1.线程解锁前,必须把共享变量的值刷新回主内存
        - 2.线程加锁前,必须读取主内存的最新值到自己的工作内存
        - 3.加锁解锁是同一把锁
      
      由于JVM运行程序的实体是线程,而每个线程创建时JVM都会为其创建一个工作内存(有些地方成为栈空间),工作内存是每个线程的私有数据区域,而Java内存模型中规定所有变量都存储在主内存,主内存是共享内存区域,所有线程都可访问,但线程对变量的操作(读取赋值等)必须在工作内存中进行,首先要将变量从主内存拷贝到自己的工作空间,然后对变量进行操作,操作完成再将变量写回主内存,不能直接操作主内存中的变量,各个线程中的工作内存储存着主内存中的变量副本拷贝,因此不同的线程无法访问对方的工作内存,此案成间的通讯(传值) 必须通过主内存来完成,其简要访问过程如下图：
      ![Aaron Swartz](https://raw.githubusercontent.com/silenceren/hand/master/pic/jmm.jpg)
      
计算机在执行程序时,为了提高性能,编译器和处理器常常会做指令重排,一把分为以下3种：
  - 单线程环境里面确保程序最终执行结果和代码顺序执行的结果一致.
  
  - 处理器在进行重新排序是必须要考虑指令之间的数据依赖性
  
  - 多线程环境中线程交替执行,由于编译器优化重排的存在,两个线程使用的变量能否保持一致性是无法确定的,结果无法预测
  ![Aaron_Swartz](https://raw.githubusercontent.com/silenceren/hand/master/pic/Instruction.png)

## 禁止指令重排小总结
  volatile 实现禁止指令重排优化，从而避免多线程环境下程序出现乱序执行的现象
  先了解一个概念，内存屏障（Memory Barrier），又称内存栅栏，是一个CPU指令，它的作用有两个 ：
    - 一是保证特定操作的执行顺序
    - 二是保证某些变量的内存可见性（利用该特性实现volatile的内存可见性）
由于编译器处理器都能执行指令重排优化，如果在指令间插入一条Memory Barrier则会告诉编译器和cpu，不管什么指令都不能和这条Memory Barrier指令重排序，也就是通过内存屏障禁止在内存屏障前后的指令执行重排序优化。内存屏障另外一个作用是强制刷出各种CPU的缓存数据，因此任何CPU上的线程都能读取到这些数据的最新版本。
![Aaron_Swartz](https://raw.githubusercontent.com/silenceren/hand/master/pic/rearrangement.png) 

  - 单例模式volatile分析
    DCL(双端检锁) 机制不一定线程安全,原因是有指令重排的存在,加入volatile可以禁止指令重排
      原因在于某一个线程在执行到第一次检测,读取到的instance不为null时,instance的引用对象可能没有完成初始化.
    instance=new SingletonDem(); 可以分为以下步骤(伪代码)
     
    memory=allocate();//1.分配对象内存空间
    instance(memory);//2.初始化对象
    instance=memory;//3.设置instance的指向刚分配的内存地址,此时instance!=null 
     
    步骤2和步骤3不存在数据依赖关系.而且无论重排前还是重排后程序执行的结果在单线程中并没有改变,因此这种重排优化是允许的.
    memory=allocate();//1.分配对象内存空间
    instance=memory;//3.设置instance的指向刚分配的内存地址,此时instance!=null 但对象还没有初始化完.
    instance(memory);//2.初始化对象
    但是指令重排只会保证串行语义的执行一致性(单线程) 并不会关心多线程间的语义一致性
    所以当一条线程访问instance不为null时,由于instance实例未必完成初始化,也就造成了线程安全问题.
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
    - DCL(双端检锁) 机制不一定线程安全,原因是有指令重排的存在,加入volatile可以禁止指令重排
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

## CAS是什么
  - CAS的全称为Compare-And-Swap，它是一条CPU并发原语。它的功能是判断内存某个位置的值是否为预期值，如果是则更改为新的值，这个过程是原子的。
  - CAS并发原语体现在Java语言中就是sun.misc.Unsafe类中的各个方法。调用Unsafe类中的CAS方法，JVM会帮助我们实现出CAS汇编指令。这是一种完全依赖于硬件的功能，通过它实现了原子操作。再次强调，由于CAS是一种系统原语，原语属于操作系统用语范畴，是由若干指令组成的，用于完成某个功能的一个过程，并且原语的执行必须是连续的，在执行过程中不允许被中断，也就是说CAS是一条CPU的原子指令，不会造成所谓的数据不一致问题。
    - AtomicInteger.java
  ![Aaron_Swartz](https://raw.githubusercontent.com/silenceren/hand/master/pic/casGetAndInc.png) 
    - Unsafe.java
  ![Aaron_Swartz](https://raw.githubusercontent.com/silenceren/hand/master/pic/casGetAndAdd.png) 
  - var1 AtomicInteger 对象本身
  - var2 该对象值的引用地址
  - var4 需要变动的数量
  - var5 是用过var1 var2找出的主内存中真实的值
  - 用该对象当前的值与var5比较：如果相同，更新var5 + var4并且返回true，如果不同，继续取值然后再比较，直到更新完成
  - 假设线程A与线程B两个线程同时执行getAndAddInt操作（分别跑在不同的cpu上）：
     - AtomicInteger 里面的value原始值为3，即主内存中的AtomicInteger的value为3，根据JMM模型，线程A和线程B各自持有一份值为3的value的副本分别到各自的工作内存
     - 线程A通过getIntVolatile（var1，var2）拿到value值3，这时线程A被挂起
     - 线程B也通过getIntVolatile(var1, var2)方法获取到value值3，此时刚好线程B没有被挂起并执行compareAndSwapInt方法比较内存值也为3，成功修改内存值为4，线程B打完收工，一切ok
     - 这时线程A恢复，执行compareAndSwapInt方法比较，发现自己手里的值数字3和主内存的值数字4不一致，说明该值已经被其他线程抢先一步修改过了，那A线程本次修改失败，只能重新读取重新来一遍了
     - 线程A重新获取value值，因为变量value被volatile修饰，所以其他线程对它的修改，线程A总是能够看到，线程A继续执行compareAndSwapInt进行比较替换，直到成功
   ![Aaron_Swartz](https://raw.githubusercontent.com/silenceren/hand/master/pic/casbase.jpg)
   - 简单总结
     - CAS(CompareAndSwap) 比较当前工作内存中的值和主内存中的值，如果相同则执行规定操作，否则继续比较直到主内存和工作内存中的值一致为止
     - CAS应用 CAS有3个操作数，内存值V，旧的预期值A，要修改的更新值B。当且仅当预期值A和内存值V相同时，将内存值V改为B，否则什么都不做
  
  - CAS 缺点
    - 循环时间长开销很大
      - getAndAddInt方法执行时，由一个do while循环，如果CAS失败，会一直进行尝试，如果CAS长时间一直不成功，可能会给CPU带来很大的开销
    - 只能保证一个共享变量的原子操作
      - 当对一个共享变量执行操作时，我们可以使用循环CAS的方式来保证原子操作，但是，对多个共享变量操作时，循环CAS就无法保证操作的原子性，这时就可以用锁来保证原子性
    - CAS 会导致“ABA问题”
      - CAS算法实现一个重要前提需要取出内存中某时刻的数据并在当下时刻比较并替换，那么在这个时间差内会导致数据的变化。比如说一个线程one从内存位置V中取出A，这时候另一个线程two也从内存中取出A，并且线程two进行了一些操作将值变成了B，然后线程又将V位置的数据变成A，这时候线程one进行CAS操作发现内存中仍然是A，然后线程one操作成功
      - 尽管线程one的CAS操作成功，但是不代表这个过程是没有问题的
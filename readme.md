# 面试

## JVM_OOM:
   - java.lang.StackOverflowError
   - java.lang.OutOfMemoryError: Java heap space
   - java.lang.OutOfMemoryError: GC overhead limit exceeded
   - java.lang.OutOfMemoryError: Direct buffer memory
   - java.lang.OutOfMemoryError: Metaspace
   - java.lang.OutOfMemoryError: unable to create new native thread
   - java.lang.OutOfMemoryError: Requested arror size exceeds VM limit

## 谈谈对 volatile 的理解
   - volatile 是 Java虚拟机提供的轻量级的同步机制：
     - 保证可见性
     - 不保证原子性
     - 禁止指令重排
    
## JMM（Java内存模型Java Memory Model）
   - JMM（Java内存模型Java Memory Model）本身是一种抽象的概念并不真实存在，它描述的是一组规则或规范，通过这组规范定义了程序中各个变量（包括实例字段，静态字段和构成数组对象的元素）的访问方式。
      - JMM关于同步规定:
        - 1.线程解锁前,必须把共享变量的值刷新回主内存
        - 2.线程加锁前,必须读取主内存的最新值到自己的工作内存
        - 3.加锁解锁是同一把锁
      
      由于JVM运行程序的实体是线程,而每个线程创建时JVM都会为其创建一个工作内存(有些地方成为栈空间),工作内存是每个线程的私有数据区域,而Java内存模型中规定所有变量都存储在主内存,主内存是共享内存区域,所有线程都可访问,但线程对变量的操作(读取赋值等)必须在工作内存中进行,首先要将变量从主内存拷贝到自己的工作空间,然后对变量进行操作,操作完成再将变量写回主内存,不能直接操作主内存中的变量,各个线程中的工作内存储存着主内存中的变量副本拷贝,因此不同的线程无法访问对方的工作内存,此案成间的通讯(传值) 必须通过主内存来完成,其简要访问过程如下图：
      
计算机在执行程序时,为了提高性能,编译器和处理器常常会做指令重排,一把分为以下3种：
  - 单线程环境里面确保程序最终执行结果和代码顺序执行的结果一致.
  
  - 处理器在进行重新排序是必须要考虑指令之间的数据依赖性
  
  - 多线程环境中线程交替执行,由于编译器优化重排的存在,两个线程使用的变量能否保持一致性是无法确定的,结果无法预测

## 禁止指令重排小总结
  volatile 实现禁止指令重排优化，从而避免多线程环境下程序出现乱序执行的现象
  先了解一个概念，内存屏障（Memory Barrier），又称内存栅栏，是一个CPU指令，它的作用有两个 ：
    - 一是保证特定操作的执行顺序
    - 二是保证某些变量的内存可见性（利用该特性实现volatile的内存可见性）
由于编译器处理器都能执行指令重排优化，如果在指令间插入一条Memory Barrier则会告诉编译器和cpu，不管什么指令都不能和这条Memory Barrier指令重排序，也就是通过内存屏障禁止在内存屏障前后的指令执行重排序优化。内存屏障另外一个作用是强制刷出各种CPU的缓存数据，因此任何CPU上的线程都能读取到这些数据的最新版本。  
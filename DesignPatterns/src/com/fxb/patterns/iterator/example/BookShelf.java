package com.fxb.patterns.iterator.example;

/**
 * 存储数据单元的集合
 * 迭代器在此类生成
 * */
public class BookShelf<T> implements Shelf<T> {

    private Object[] books;
    private int count;

    /**
     * 固定书架最大容量就只有100
     * */
    public BookShelf() {
        this(100);
    }

    public BookShelf(int initialCapacity) {
        books = new Book[initialCapacity];
    }


    @Override
    public void append(T t) {
        if (count() + 1 > 100)
            System.out.println("书架容量已经装满了");// 应定义一个异常 抛出异常
        else {
            books[count] = t;
            count++;
        }
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        int movNum = count - index - 1;// count 比 下标值大1
        if (movNum > 0)
            System.arraycopy(books ,index + 1 ,books ,index ,movNum);
        books[--count] = null;//让 JVM gc 回收
        return (T)books[index];
    }

    @Override
    public T getBookAt(int index) {
        rangeCheck(index);
        return (T)books[index];
    }

    @Override
    public int count() {
        return count;
    }

    private void rangeCheck(int index){
        if(index >= count || index < 0)
            throw new IndexOutOfBoundsException("Index" + index + "Size" + count);
    }

    /**
     * 此处采用了工厂模式
     * 在父接口 iterable 中定义 生成实例的方式
     * 在此类中 王城具体的产生实例的实现
     * */
    @Override
    public Iterator<T> iterator() {
        return new BookShelfIterator<T>(this);
    }
}
